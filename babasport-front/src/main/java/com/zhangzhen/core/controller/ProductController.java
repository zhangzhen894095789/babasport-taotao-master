package com.zhangzhen.core.controller;

import cn.itcast.common.page.Pagination;
import com.zhangzhen.core.bean.product.*;
import com.zhangzhen.core.service.product.ProductService;
import com.zhangzhen.core.service.product.SkuService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

/**
 * 商品的详情页面
 * 
 * @author Administrator
 * @company
 */
@Controller
public class ProductController {
	@Autowired
	private SolrServer solrServer;
	@Autowired
	private JedisPool jedisPool;
	// 去前台商品首页
	@RequestMapping("/product/display/list.shtml")
	public String list(Integer pageNo, String keyword,Long brandId,String price,Model model) throws Exception {
		//从redis服务器中取出品牌信息
		Jedis jedis = jedisPool.getResource();
		
		Set<String> keys = jedis.keys("brandId:*");
		//创建一个list集合用来封装品牌对象信息
		List<Brand> brands = new ArrayList<>();
		
		//遍历所有的key获取key值对应的数据
		for(String key : keys){
			//创建一个brand对象用来封装数据id和name
			Brand brand = new Brand();
			//获取id的值
			brand.setId(Long.parseLong(jedis.hget(key, "id")));
			//获取name值
			brand.setName(jedis.hget(key, "name"));
			//将获取的数据都放到一个创建号的list集合中
			brands.add(brand);
		}
		//显示品牌信息到前台页面上
		jedis.close();//连接池数量有限 ，用完尽量返回不要忘记关闭
		model.addAttribute("brands", brands);
		// 创建条件查询对象封装分页查询数据
		ProductQuery productQuery = new ProductQuery();
		productQuery.setPageNo(Pagination.cpn(pageNo));
		productQuery.setPageSize(4);

		// 创建分页查询对象拼接的参数字符串对象
		StringBuilder sb = new StringBuilder();
		sb.append("keyword=").append(keyword);
		// 查询
		SolrQuery params = new SolrQuery();

		// 关键词
		params.set("q", "name_ik:" + keyword);

		// 设置开始行
		params.setStart(productQuery.getStartRow());
		//设置每页数
		params.setRows(productQuery.getPageSize());

		// 设置查询条件价格从低到高
		params.addSort("price", ORDER.asc);
		
		
		//已选条件标识
		Boolean flag = false;
		//已选条件存储
		Map<String,String> query = new HashMap<String,String>();
		
		//判断 品牌ID
		if(null != brandId){
			//添加过滤条件
			params.addFilterQuery("brandId:" + brandId);
			//回显品牌ID
			model.addAttribute("brandId", brandId);
			//已选条件标识
			flag = true;
			//添加过滤条件到query
			query.put("品牌", jedis.hget("brandId:" + brandId, "name"));
		}
		//判断价格
		if(null != price){
			//添加价格过滤条件
			String[] p = price.split("-");
			if(p.length == 2){
				//第一种： 0-79 
				//开始价
				Float pStart = new Float(p[0]);
				//结束价格
				Float pEnd = new Float(p[1]);
				//价格过滤
				params.addFilterQuery("price:[" + pStart +" TO " + pEnd + "]");
				//添加过滤条件到query
				query.put("价格",price);
			}else if(p.length == 1){
				//第二种：600~无限大
				//开始价
				Float pStart = new Float(p[0]);
				//结束价格
				Float pEnd = new Float("99999999");
				//价格过滤
				params.addFilterQuery("price:[" + pStart +" TO " + pEnd + "]");
				//添加过滤条件到query
				query.put("价格",price + "以上");
			}
			//回显价格
			model.addAttribute("price", price);
			//已选条件标识
			flag = true;
		}
		//回显已选条件标识
		model.addAttribute("flag", flag);
		//已选条件回显到页面
		model.addAttribute("query", query);
		
		
		//开启高亮
		params.setHighlight(true);
		//设置高亮字段
		params.addHighlightField("name_ik");
		//设置高亮字段的前缀 <span style='color:red'>瑜伽服</span>
		params.setHighlightSimplePre("<span style='color:red'>");
		//设置高亮字段的后缀
		params.setHighlightSimplePost("</span>");
		// 执行查询 返回查询结果集
		QueryResponse response = solrServer.query(params);
		// 获取结果集
		SolrDocumentList docs = response.getResults();
		// 取出结果的总条数
		 long numFound = docs.getNumFound();

		// System.out.println("总条数：" + numFound);

		List<Product> list = new ArrayList<Product>();

		for (SolrDocument doc : docs) {
			// 创建商品对象
			System.out.println(doc);
			Product product = new Product();
			// 获取商品的id
			product.setId(Long.parseLong((String) doc.get("id")));
			// 获取solr中保存商品名
			//取出高亮数据
			Map<String, Map<String, List<String>>> map = response.getHighlighting();
			Map<String, List<String>> map2 = map.get((String) doc.get("id"));
			List<String> list2 = map2.get("name_ik");
			//将已经高亮过的数据取出来后重新赋值给product这样显示的就是高亮的信息了
			String name = (String) doc.get("name_ik");
			product.setName(list2.get(0));
			// 获取商品的图片
			String url = (String) doc.get("url");
			Img img = new Img();
			img.setUrl(url);
			product.setImg(img);
			// 获取商品的价格
			product.setPrice((Float) doc.get("price"));
			// 获取商品id信息
			product.setBrandId(Long.parseLong(String.valueOf((Integer) doc.get("brandId"))));

			list.add(product);
		}

		// 创建分页对象
		Pagination pagination = new Pagination(productQuery.getPageNo(),productQuery.getPageSize(),(int) numFound);

		pagination.setList(list);
		
		String url = "/product/display/list.shtml";
		pagination.pageView(url, sb.toString());
		model.addAttribute("pagination", pagination);
		return "product/product";

	}
	//去页面详情页
	@Autowired
	private ProductService productService;
	@Autowired
	private SkuService skuService;
	@RequestMapping("/product/detail.shtml")
	public String toDeatil(Long id,Model model){
		//加载商品及图片
		Product product = productService.selectProductAndImgById(id);
		model.addAttribute("product",product);
		//Sku结果集 及 颜色
		List<Sku> skus = skuService.selectSkuListWithStockByProductId(id);
		//Set集合 去掉重复的
		Set colors = new HashSet();
		//遍历Sku
		for(Sku sku : skus){
			//Sku中的Color对象放到Set<Color> 中
			colors.add(sku.getColor());
		}
		model.addAttribute("skus",skus);
		model.addAttribute("colors",colors);
		return "/product/productDetail";
	}
}

package cn.itcast.core.service.product;

import cn.itcast.common.utils.page.Pagination;
import cn.itcast.core.bean.product.*;
import cn.itcast.core.bean.product.ImgQuery.Criteria;
import cn.itcast.core.dao.product.ImgDao;
import cn.itcast.core.dao.product.ProductDao;
import cn.itcast.core.dao.product.SkuDao;
import cn.itcast.core.service.staticpage.StaticPageService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ImgDao imgDao;
	@Autowired
	private SkuDao skuDao;

	// 获取分页查询对象
	public Pagination selectPaginationByQuery(ProductQuery productQuery) {
		int totalCount = productDao.countByExample(productQuery);
		Pagination pagination = new Pagination(productQuery.getPageNo(), productQuery.getPageSize(), totalCount);
		// 超过最大当前页时使用最后 一页
		productQuery.setPageNo(pagination.getPageNo());
		List<Product> products = productDao.selectByExample(productQuery);
		// 返回商品的同时设置商品的图片的地址
		for (Product product : products) {
			ImgQuery imgQuery = new ImgQuery();
			Criteria criteria = imgQuery.createCriteria();
			// 设置查询条件是商品的id并且商品是没删除状态的
			criteria.andProductIdEqualTo(product.getId()).andIsDefEqualTo(true);
			List<Img> imgs = imgDao.selectByExample(imgQuery);
			product.setImg(imgs.get(0));
		}
		pagination.setList(products);
		return pagination;
	}

	@Autowired
	private JedisPool jedisPool;

	// 商品添加
	public void insertProduct(Product product) {
		// 设置redis统一生成的商品编号
		Jedis jedis = jedisPool.getResource();
		// jedis.set("pno", "1000");
		Long id = jedis.incr("pno");

		product.setId(id);
		// 设置上下架 默认要求是下架
		product.setIsShow(false);
		// 设置是否删除 不删除 1
		product.setIsDel(true);
		// 保存商品
		productDao.insertSelective(product);
		// 图片保存
		Img img = product.getImg();
		// 设置商品ID
		img.setProductId(product.getId());
		// 设置是否是默认
		img.setIsDef(true);
		// 保存图片表
		imgDao.insertSelective(img);
		// 保存Sku
		// 商品ID 、颜色、尺码 （最小销售）
		for (String color : product.getColors().split(",")) {
			// 9 转Long
			// 创建Sku对象
			Sku sku = new Sku();
			// 设置颜色
			sku.setColorId(Long.parseLong(color));
			// 设置商品ID
			sku.setProductId(product.getId());
			for (String size : product.getSizes().split(",")) {
				// 设置尺码
				sku.setSize(size);
				// 设置运费
				sku.setDeliveFee(10f);
				// 市场价格
				sku.setMarketPrice(0f);
				// 售价
				sku.setPrice(0f);
				// 库存
				sku.setStock(0);
				// 默认200件 可更改
				sku.setUpperLimit(200);
				// 遍历尺码 处 保存Sku
				skuDao.insertSelective(sku);
			}

		}
		// jedis连接池资源有限，记得释放资源
		jedis.close();
	}

	@Autowired
	private SolrServer solrServer;

	// 修改上架状态
	public void isShow(Long[] ids) {
		for (Long id : ids) {
			Product product = new Product();
			product.setId(id);
			// 设置上下架，上架为true下架为false
			product.setIsShow(true);
			// 更新商品的状态信息
			productDao.updateByPrimaryKeySelective(product);
			// 保存商品信息到solr服务器 id,name,url,price, //品牌ID
			SolrInputDocument doc = new SolrInputDocument();
			// id
			doc.setField("id", id);
			// 商品的名称
			Product product2 = productDao.selectByPrimaryKey(id);
			doc.setField("name_ik", product2.getName());
			// url图片地址
			ImgQuery imgQuery = new ImgQuery();
			// 默认图片1，根据商品id查询图片表
			imgQuery.createCriteria().andProductIdEqualTo(id).andIsDefEqualTo(true);
			List<Img> imgs = imgDao.selectByExample(imgQuery);
			doc.setField("url", imgs.get(0).getUrl());
			// 保存商品的价格price
			// 查询库存表 select price from bbs_sku order by price asc limit 0,1
			SkuQuery skuQuery = new SkuQuery();
			// 指定查询字段
			skuQuery.setFields("price");
			skuQuery.setOrderByClause("price asc");
			skuQuery.setPageNo(1);
			skuQuery.setPageSize(1);
			List<Sku> skus = skuDao.selectByExample(skuQuery);
			doc.setField("price", skus.get(0).getPrice());
			// 品牌id
			doc.setField("brandId", product2.getBrandId());
			// 保存到solr服务器
			try {
				solrServer.add(doc);
				solrServer.commit();
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//静态化（商品详情页面）
			Map<String,Object> root = new HashMap<String,Object>();
			
			//加载商品及图片
			Product pp = selectProductAndImgById(id);
			root.put("product", pp);
			//Sku结果集 及 颜色
			List<Sku> ss = skuService.selectSkuListWithStockByProductId(id);
			
			//Set集合 去掉重复的
			Set<Color> colors = new HashSet<Color>();
			//遍历Sku
			for (Sku sku : ss) {
				//Sku中的Color对象放到Set<Color> 中
				colors.add(sku.getColor());
			}
			
			root.put("skus", ss);
			root.put("colors", colors);
			
			staticPageService.index(root, id);



		}
	}

	@Autowired
	private SkuService skuService;
	@Autowired
	private StaticPageService staticPageService;

	// 修改下架状态
	public void isNotShow(Long[] ids) {
		for (Long id : ids) {
			Product product = new Product();
			product.setId(id);
			// 设置上下架，上架为true下架为false
			product.setIsShow(false);
			// 更新商品的状态信息
			productDao.updateByPrimaryKeySelective(product);
		}
	}

	// 查询单个商品信息
	public Product selectProductById(Long productId) {
		Product product = productDao.selectByPrimaryKey(productId);
		return product;
	}

	// 修改更新单个商品信息
	public void updateProductById(Product product) {
		ImgQuery imgQuery = new ImgQuery();
		//根据商品id查询图片的信息
		imgQuery.createCriteria().andProductIdEqualTo(product.getId()).andIsDefEqualTo(true);
		List<Img> imgs = imgDao.selectByExample(imgQuery);
		//重新设置图片的URL
		Img img = imgs.get(0);
		img.setUrl(product.getImg().getUrl());
		//更新图片的信息
		imgDao.updateByPrimaryKeySelective(img);
		productDao.updateByPrimaryKeySelective(product);
	}

	// 根据商品信息查询商品和商品图片
	public Product selectProductAndImgById(Long id) {
		Product product = productDao.selectByPrimaryKey(id);
		// 通过商品ID查询图片
		ImgQuery imgQuery = new ImgQuery();
		// 商品ID 默认 true 1是默认显示图片
		imgQuery.createCriteria().andProductIdEqualTo(id).andIsDefEqualTo(true);
		// 返回唯一的一张图片
		List<Img> imgs = imgDao.selectByExample(imgQuery);
		// 设置一张图片到商品对象中
		product.setImg(imgs.get(0));
		return product;
	}

}

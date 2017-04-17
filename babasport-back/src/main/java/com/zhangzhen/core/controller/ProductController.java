package com.zhangzhen.core.controller;

import cn.itcast.common.page.Pagination;
import com.zhangzhen.core.bean.product.*;
import com.zhangzhen.core.service.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 商品信息管理
 */

@Controller
public class ProductController {
	@Autowired
	private BrandService brandService;
	@Autowired
	private ProductService productService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private FeatureService featureService;
	@Autowired
	private ColorService colorService;

	@RequestMapping("/product/list.do")
	public String list(HttpServletRequest request, Long brandId, String name, Integer pageNo, Boolean isShow,
			Model model) {
		// 查询品牌信息
		BrandQuery brandQuery = new BrandQuery();

		// 设置查询条件
		brandQuery.setIsDisplay(1);
		List<Brand> brands = brandService.selectBrandListByQuery(brandQuery);
		// 品牌信息传回页面
		model.addAttribute("brands", brands);
		// 创建商品查询条件
		ProductQuery productQuery = new ProductQuery();
		ProductQuery.Criteria criteria = productQuery.createCriteria();
		// 设置每页显示数
		productQuery.setPageSize(3);
		// 设置当前页
		productQuery.setPageNo(Pagination.cpn(pageNo));
		// 查询的数据 按照商品id倒序展示，展示 最新添加的商品在前
		productQuery.setOrderByClause("id desc");
		// 设置查询条件
		StringBuilder params = new StringBuilder();
		// 判断传入参数
		if (null != name) {
			String method = request.getMethod();

			if (method.equals("GET")) {
				try {
					name = new String(name.getBytes("iso8859-1"), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			criteria.andNameLike("%" + name + "%");
			// 回显数据
			model.addAttribute("name", name);
			params.append("name=").append(name);
		}
		// 品牌id
		if (null != brandId) {
			criteria.andBrandIdEqualTo(brandId);
			model.addAttribute("brandId", brandId);
			params.append("&brandId=").append(brandId);
		}
		// 上下架
		if (null != isShow) {
			criteria.andIsShowEqualTo(isShow);
			// 回显
			model.addAttribute("isShow", isShow);
			params.append("&isShow=").append(isShow);
		} else {
			criteria.andIsShowEqualTo(false);
			model.addAttribute("isShow", false);
			params.append("&isShow=").append(false);
		}
		// 创建分页查询对象
		Pagination pagination = productService.selectPaginationByQuery(productQuery);
		// 拼接分页条

		String url = "/product/list.do";
		pagination.pageView(url, params.toString());
		// 回显
		model.addAttribute("pagination", pagination);
		return "/product/list";
	}

	// 去商品添加页面
	@RequestMapping("/product/toAdd.do")
	public String toAdd(Model model) {
		// 加载商品类型数据到页面下拉框
		TypeQuery typeQuery = new TypeQuery();
		TypeQuery.Criteria criteria = typeQuery.createCriteria();
		criteria.andParentIdNotEqualTo(0l);
		List<Type> types = typeService.selectTypeListByQuery(typeQuery);
		model.addAttribute("types", types);
		// 加载品牌信息数据到页面回显
		BrandQuery brandQuery = new BrandQuery();
		// 是否可见
		brandQuery.setIsDisplay(1);
		List<Brand> brands = brandService.selectBrandListByQuery(brandQuery);
		// 回显数据
		model.addAttribute("brands", brands);

		// 、材质、
		FeatureQuery featureQuery = new FeatureQuery();
		featureQuery.createCriteria().andIsDelEqualTo(true);
		List<Feature> features = featureService.selectFeatureListByQuery(featureQuery);
		// 回显条件
		model.addAttribute("features", features);
		// 颜色
		ColorQuery colorQuery = new ColorQuery();
		colorQuery.createCriteria().andParentIdNotEqualTo(0L);
		List<Color> colors = colorService.selectColorListByQuery(colorQuery);
		// 回显条件
		model.addAttribute("colors", colors);
		return "/product/add";
	}

	// 添加商品，保存图片，库存（最小销售单元）
	@RequestMapping(value = "/product/add.do")
	public String add(Product product) {
		// 保存 商品表、图片表 事务 最好写到Service
		productService.insertProduct(product);
		return "redirect:/product/list.do";
	}
	
	//商品上架
	@RequestMapping("/product/isShow.do")
	public String isShow(Long[] ids){
		//根据id设置商品的isShow
		productService.isShow(ids);
		return "redirect:/product/list.do";
	}
	
	//商品下架
	@RequestMapping("/product/isNotShow.do")
	public String isNotShow(Long[] ids){
		productService.isShow(ids);
		return "redirect:/product/list.do";
	}
	
	//修改商品页面
	@RequestMapping("/product/toEdit.do")
	public String toEdit(Long productId,Model model){
		Product product = productService.selectProductAndImgById(productId);
		//查询的商品信息回显到编辑页面
		model.addAttribute("product", product);
		return "/product/edit";
	}
	
	//修改商品
	@RequestMapping("/product/edit.do")
	public String edit(Product product){
//		productService.updateProductById(product);
		return "redirect:/product/list.do";
	}
}

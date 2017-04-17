package com.zhangzhen.core.controller;

import cn.itcast.common.page.Pagination;
import com.zhangzhen.core.bean.product.Brand;
import com.zhangzhen.core.bean.product.BrandQuery;
import com.zhangzhen.core.service.product.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * 品牌管理
 * 
 * @author lx
 *
 */
@Controller
public class BrandController {
	@Autowired
	private BrandService brandService;
	//品牌管理
	@RequestMapping(value = "/brand/list.do")
	public String list(HttpServletRequest request, Integer pageNo, String name, Integer isDisplay, Model model){
		StringBuilder params = new StringBuilder();
		//创建品牌查询对象
		BrandQuery brandQuery = new BrandQuery();
		//如果当前页为null或小于1，将当前页置为 1
		brandQuery.setPageNo(Pagination.cpn(pageNo));
		brandQuery.setPageSize(3);
		//判断传入条件是否为null
		if (name != null) {
			String method = request.getMethod();
			if (method.equals("get")||method.equals("GET")) {
				try {
					name = new String(name.getBytes("iso-8859-1"), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//这里传入的name可能为空串" "
			brandQuery.setName(name);
			//数据回显到页面的input的输入框中
			model.addAttribute("name", name);
			params.append("name=").append(name);
		}
		
		if (isDisplay != null) {
			brandQuery.setIsDisplay(isDisplay);
			model.addAttribute("isDisplay", isDisplay);
			params.append("&isDisplay=").append(isDisplay);
		}else {
			model.addAttribute("isDisplay", 1);
			params.append("&isDisplay=").append(1);
		}
		
		//调用service层查询
//		List<Brand> brands = brandService.selectBrandListByQuery(brandQuery);
		//将数据传递到页面
//		model.addAttribute("brands", brands);
		Pagination pagination = brandService.selectPaginationByQuery(brandQuery);
	
		String url = "/brand/list.do";
		pagination.pageView(url , params.toString());
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageNO", pagination.getPageNo());
		return "brand/list";
	}
	//跳转添加品牌页面
	@RequestMapping("/brand/toAdd.do")
	public String toAdd(){
		return "/brand/add";
	}
	
	//添加品牌
	@RequestMapping("/brand/add.do")
	public String add(Brand brand){
		brandService.insertBrandById(brand);
		return "redirect:/brand/list.do";
	}
	//批量删除
	@RequestMapping("/brand/deletes.do")
	public String deletes(Long[]ids,Integer pageNo,String name,Integer isDisplay,Model model) throws Exception{
		//删除
		brandService.deleteBrandByIds(ids);
		if (null != pageNo) {
			model.addAttribute("pageNo", pageNo);
		}
		//判断
		if(null != name){
			
			model.addAttribute("name", name);
		}
		//判断
		if(null != isDisplay){
			model.addAttribute("isDisplay", isDisplay);
		}
		return "redirect:/brand/list.do";
	}
	//跳转到品牌修改的页面
	@RequestMapping("/brand/toEdit.do")
	public String toEdit(Long id,Model model){
		//通过id查询brand回显到edit页面
		Brand brand = brandService.selectBrandById(id);
		//将brand传回页面
		model.addAttribute("brand",brand);
		return "/brand/edit";
	}
	//修改品牌信息
	@RequestMapping("/brand/edit.do")
	public  String edit(Brand brand){
		brandService.updateBrandById(brand);
		return "redirect:/brand/list.do";
	}
}
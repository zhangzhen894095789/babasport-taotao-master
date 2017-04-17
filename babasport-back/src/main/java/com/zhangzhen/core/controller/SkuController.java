package com.zhangzhen.core.controller;

import com.zhangzhen.core.bean.product.Sku;
import com.zhangzhen.core.service.product.SkuService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 库存管理 加载库存页面数据 修改库存数据
 * 
 * @author lx
 *
 */
@Controller
public class SkuController {
	@Autowired
	private SkuService skuService;

	// 去库存列表页面
	@RequestMapping("/sku/list.do")
	public String toSku(Long productId, Model model) {
		// 根据商品id查询sku库存结果集
		List<Sku> skus = skuService.selectSkuListByProductId(productId);
		// 结果集返回页面
		model.addAttribute("skus", skus);

		return "/sku/list";
	}

	// 修改库存信息
	@RequestMapping("/sku/update.do")
	public void update(Sku sku, HttpServletResponse response) throws Exception {
		// 更新库存信息
		skuService.updateSkuById(sku);
		// 页面提示信息
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jo = new JSONObject();
		jo.put("message", "保存成功！");
		response.getWriter().write(jo.toString());
		
	}
}

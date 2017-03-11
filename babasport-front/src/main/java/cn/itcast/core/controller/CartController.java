package cn.itcast.core.controller;

import java.io.StringWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.web.session.SessionProvider;
import cn.itcast.core.bean.BuyerCart;
import cn.itcast.core.bean.BuyerItem;
import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.service.product.SkuService;
import cn.itcast.core.web.Constants;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 购物车
 * 
 * @author Administrator
 *
 */
@Controller
public class CartController {
	@Autowired
	private SessionProvider sessionProvider;

	@Autowired
	private SkuService skuService;

	@Autowired
	private JedisPool jedisPool;

	// 去购车页面
	@RequestMapping("/shopping/buyerCart.shtml")
	// 页面传送库存id,购买商品数量
	public String toCart(Long skuId, Integer amount, HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		//转JSON的类
				ObjectMapper om = new ObjectMapper();
				//不转换NUll值
				om.setSerializationInclusion(Inclusion.NON_NULL);
				//需求：分析逻辑
				//去Cookie中取之前的商品
				//JSESSIONID
				//购物车
				BuyerCart buyerCart = null;
				//CJESSIONID
				Cookie[] cookies = request.getCookies();
				//判断 
				if(null != cookies && cookies.length >0){
					//遍历
					for (Cookie cookie : cookies) {
						//找到Cookie中的购物车
						if(Constants.BUYER_CART.equals(cookie.getName())){
							//获取购物车
							String value = cookie.getValue();
							//把JSON字符串转成购物车
							buyerCart = om.readValue(value,BuyerCart.class);
							//跳出for
							break;
						}
						
					}
				}
				//判断如果没有 创建一个新购物车
				if(null == buyerCart){
					buyerCart = new BuyerCart();
				}
				
				//判断用户是否登陆
				String username = sessionProvider.getAttribute(request, response, Constants.USER_SESSION);
				if(null != username){
					//登陆时 购物车存储
					Jedis jedis = jedisPool.getResource();
					//判断购物车中购物项长度大于0时
					List<BuyerItem> items = buyerCart.getItems();
					if(items.size() > 0){
						for (BuyerItem buyerItem : items) {
							if(jedis.hexists("buyerItem:" + username, String.valueOf(skuId))){
								//对hash容器中相同款的商品进行指定增加购买数量
								jedis.hincrBy("buyerItem:" + username, String.valueOf(buyerItem.getSku().getId()), 
										Long.parseLong(String.valueOf(buyerItem.getAmount())));
							}else{
								//将购物车中的数据放到Redis中
								jedis.lpush("buyerCart:" + username, String.valueOf(buyerItem.getSku().getId()));
								//将购物项中的SKuID 及 购买数量放到redis的hash类型的容器中
								jedis.hset("buyerItem:" + username, String.valueOf(buyerItem.getSku().getId()),
										String.valueOf(buyerItem.getAmount()));
							}
						}
						//清空购物车
						buyerCart.clearCart();
						//清空Cookie
						Cookie cookie = new Cookie(Constants.BUYER_CART,null);
						//设置路径
						cookie.setPath("/");
						//立即销毁
						cookie.setMaxAge(0);
						//写回用户浏览器 
						response.addCookie(cookie);
					}
					//把当前的要追加的SkuID及数据数量添加到Redis中
					if(null != skuId){
						//用Redis服务器中的hash判断此款商品是否已经存在，如果存在只追加购买数量 即可
						if(jedis.hexists("buyerItem:" + username, String.valueOf(skuId))){
							//对hash容器中相同款的商品进行指定增加购买数量
							jedis.hincrBy("buyerItem:" + username, String.valueOf(skuId), 
									Long.parseLong(String.valueOf(amount)));
						}else{
							//追加到Redis中
							jedis.lpush("buyerCart:" + username, String.valueOf(skuId));
							//放SkuID及购买数量到Redis的Hash类型的容器中
							jedis.hset("buyerItem:" + username, String.valueOf(skuId), String.valueOf(amount));
						}
					}
					//Jedis中的购物车所有商品遍历添加到购物车对象中
					List<String> lrange = jedis.lrange("buyerCart:" + username, 0, -1);
					//此用户的所的购物项 遍历
					if(null != lrange){
						for (String l : lrange) {
							//创建SKu对象
							Sku sku = new Sku();
							sku.setId(Long.parseLong(l));
							//创建购物项
							BuyerItem item = new BuyerItem();
							item.setSku(sku);
							//设置购买的数量
							item.setAmount(Integer.parseInt(jedis.hget("buyerItem:" + username, l)));
							//把购物项装到购物车中
							buyerCart.addItems(item);
						}
					}
				}else{
					//把当前商品追加进购物车
					if(null != skuId){
						//创建Sku
						Sku sku = new Sku();
						//设置当前商品的SKUID
						sku.setId(skuId);
						//把SKu设置给购物项
						BuyerItem buyerItem = new BuyerItem();
						buyerItem.setSku(sku);
						//设置购物数量
						buyerItem.setAmount(amount);
						//购物项追加到购物车
						buyerCart.addItems(buyerItem);
						
						//转购物车对象到JSON字符串
						StringWriter w = new StringWriter();
						om.writeValue(w, buyerCart);
						
						//将购物车回写到用户的浏览器的Cookie中
						Cookie cookie = new Cookie(Constants.BUYER_CART,w.toString());
						//保存7天
						cookie.setMaxAge(60*60*24*7);
						//设置Cookie的路径 
						cookie.setPath("/");
						//把Cookie写回用户的浏览器
						response.addCookie(cookie);
						//对购物车进行倒排
						Collections.sort(buyerCart.getItems(), new Comparator<BuyerItem>() {
							//排序的方法 
							@Override
							public int compare(BuyerItem o1, BuyerItem o2) {
								// TODO Auto-generated method stub
								//判断   return 1 0 -1
								return -1;
							}
							
						});
					}
					
				}
				//把购物车数据装满
				//购物车中有购物项(有商品 （之前Cookie里的。也可以当前新追加的）
				//购物车中无购物项（购物项是空的）
				List<BuyerItem> items = buyerCart.getItems();
				if(items.size() > 0){
					for (BuyerItem buyerItem : items) {
						//通过SkuID查询（SKu表、颜色表、商品表、图片表）
						Sku sku = skuService.selectSkuById(buyerItem.getSku().getId());
						//设置全数据的SKu到购物项中
						buyerItem.setSku(sku);
					}
				}
				//回显页面
				model.addAttribute("buyerCart", buyerCart);
				
				//正常的进入 购物车页面
				return "product/cart";
			}
	// 删除购物项
	@RequestMapping("/shopping/delProduct.shtml")
	public String delProduct(Long skuId, Integer amount, HttpServletRequest request, HttpServletResponse response,
			Model model) throws Exception {
		//判断用户是否登陆
				String username = sessionProvider.getAttribute(request, response, Constants.USER_SESSION);
				if(null != username){
					//删除购物车中的商品
					//第一步：删除  redis中 List集合中的 对应此用户的购物车中 skuId
					Jedis jedis = jedisPool.getResource();
					jedis.lrem("buyerCart:" + username, 1, String.valueOf(skuId));
					//删除 redis中购物项中的数量
					jedis.hdel("buyerItem:" + username, String.valueOf(skuId));
					jedis.close();
				}else{
					//删除购物车一款商品
					//转JSON的类
					ObjectMapper om = new ObjectMapper();
					//不转换NUll值
					om.setSerializationInclusion(Inclusion.NON_NULL);
					//需求：分析逻辑
					//去Cookie中取之前的商品
					//JSESSIONID
					//购物车
					BuyerCart buyerCart = null;
					//CJESSIONID
					Cookie[] cookies = request.getCookies();
					//判断 
					if(null != cookies && cookies.length >0){
						//遍历
						for (Cookie cookie : cookies) {
							//找到Cookie中的购物车
							if(Constants.BUYER_CART.equals(cookie.getName())){
								//获取购物车
								String value = cookie.getValue();
								//把JSON字符串转成购物车
								buyerCart = om.readValue(value,BuyerCart.class);
								//跳出for
								break;
							}
							
						}
					}
					//删除购物项
					buyerCart.delProduct(skuId);
					//转购物车对象到JSON字符串
					StringWriter w = new StringWriter();
					om.writeValue(w, buyerCart);
					
					//将购物车回写到用户的浏览器的Cookie中
					Cookie cookie = new Cookie(Constants.BUYER_CART,w.toString());
					//保存7天
					cookie.setMaxAge(60*60*24*7);
					//设置Cookie的路径 
					cookie.setPath("/");
					//把Cookie写回用户的浏览器
					response.addCookie(cookie);
				}
				
			
				
				return "redirect:/shopping/buyerCart.shtml";
			}

	// + -点击购物相中的+号时
	@RequestMapping(value = "/shopping/productAmount.shtml")
	public void productAmount(Long skuId, Integer amount, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 转换JSON的类
		ObjectMapper om = new ObjectMapper();
		// 只转换非null
		om.setSerializationInclusion(Inclusion.NON_NULL);
		BuyerCart buyerCart = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(Constants.BUYER_CART)) {
				// cookie中已经存在了之前的购物车数据

				// 获取购物车

				String value = cookie.getValue();
				// 将json字符串转换成购物车对象
				buyerCart = om.readValue(value, BuyerCart.class);
				// 跳出for循环
				break;
			}
		}

		// 把当前的购物项的数据添加到购物车中去
		if (skuId != null) {
			Sku sku = new Sku();
			sku.setId(skuId);
			// 把sku设置给购物项
			BuyerItem buyerItem = new BuyerItem();
			buyerItem.setSku(sku);
			buyerItem.setAmount(amount);
			// 购物项添加到购物车
			buyerCart.addItems(buyerItem);
			// 转购物车对象到JSON字符串
			StringWriter w = new StringWriter();
			om.writeValue(w, buyerCart);
			// 将购物车数据写回到浏览器 的cookie中
			Cookie cookie = new Cookie(Constants.BUYER_CART, w.toString());
			// 设置cookie在浏览器的保存时间
			cookie.setMaxAge(60 * 60 * 24 * 7);// 保存七天
			// 设置cookie的访问路径
			cookie.setPath("/");
			// 写回到浏览器
			response.addCookie(cookie);
		}
		// 把购物车中的数据装满
		List<BuyerItem> items = buyerCart.getItems();
		if (items.size() > 0) {
			// 购物车中有购物项，就将该购物项的所有信息查询出来封装到sku中，保存在购物车的购物项集合中
			for (BuyerItem buyerItem : items) {

				Sku sku = skuService.selectSkuById(buyerItem.getSku().getId());
				buyerItem.setSku(sku);
			}

		}
		// 返回ajax数据
		JSONObject jo = new JSONObject();
		jo.put("productAmount", buyerCart.getProductAmount());
		jo.put("productPrice", buyerCart.getProductPrice());
		jo.put("fee", buyerCart.getFee());
		jo.put("totalPrice", buyerCart.getTotalPrice());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jo.toString());
	}

	// 清空购物车
	@RequestMapping(value = "/shopping/clearCart.shtml")
	public String clearCart(HttpServletResponse response,HttpServletRequest request) {
		//判断用户是否登陆
		String username = sessionProvider.getAttribute(request, response, Constants.USER_SESSION);
		
		if(null != username){
			//清空Redis服务器中购物车及购物项
			Jedis jedis = jedisPool.getResource();
			//清空
			jedis.del("buyerCart:" + username);
			jedis.del("buyerItem:" + username);
			jedis.close();
			
		}else{
			//清空购物车  清空Cookie  销毁Cookie
			Cookie cookie = new Cookie(Constants.BUYER_CART,null);
			//立即销毁
			cookie.setMaxAge(0);
			//设置路径
			cookie.setPath("/");
			//写回用户浏览器
			response.addCookie(cookie);
		}
		
		
		//刷新购物车
		return "redirect:/shopping/buyerCart.shtml";
	}

	//结算按钮
	@RequestMapping("/buyer/trueBuy.shtml")
	public String trueBye(HttpServletRequest request,HttpServletResponse response,Model model){
		//判断 购物车中是否有商品
		//判断用户是否登陆
		String username = sessionProvider.getAttribute(request, response, Constants.USER_SESSION);
		//从Reds中取此用户的购物车
		Jedis jedis = jedisPool.getResource();
		List<String> lrange = jedis.lrange("buyerCart:" + username, 0, -1);
		if(null != lrange && lrange.size() > 0){
			
		}else{
			//此用户购物车中无商品
			//刷新购物车
			return "redirect:/shopping/buyerCart.shtml";
		}
		//正常情况下

		return "/product/productOrder";
	}

}

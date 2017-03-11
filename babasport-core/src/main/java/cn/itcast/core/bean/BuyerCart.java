package cn.itcast.core.bean;

import cn.itcast.core.bean.product.Sku;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 * 
 * @author Administrator
 *
 */
public class BuyerCart {
	// 购物车对象包含购物项集合
	private List<BuyerItem> items = new ArrayList<>();

	// 添加购物车中添加购物项的方法, buyerItem已经重写了equals方法，如果 两个购物项的id相同，就是同一个对象
	//在添加购物项的方法中叠加相同的购物项的购买数量保存到购物项中就ok了
	public void addItems(BuyerItem item) {
		// contains底层调用的也是item的的equals方法
		if (items.contains(item)) {
			// 如果购物车中已经包含了该购物项
			for (BuyerItem it : items) {
				if (it.equals(item)) {
					it.setAmount(it.getAmount() + item.getAmount());
				}
			}
		} else {

			items.add(item);
		}
	}

	public List<BuyerItem> getItems() {
		return items;
	}

	public void setItems(List<BuyerItem> items) {
		this.items = items;
	}

	
	

	//商品数量
	@JsonIgnore
	public Integer getProductAmount(){
		Integer result = 0;
		//计算商品数量
		for(BuyerItem it : items){
			result += it.getAmount();
		}
		return result;
	}
	//商品金额
	@JsonIgnore
	public Float getProductPrice(){
		Float result = 0f;
		//计算商品金额
		for(BuyerItem it : items){
			result += it.getAmount()*it.getSku().getPrice();
		}
		return result;
	}
	//运费
	@JsonIgnore
	public Float getFee(){
		Float result = 0f;
		//公司要求
		if(getProductPrice() < 79){
			result = 5f;
		}
		return result;
	}
	//应付金额
	@JsonIgnore
	public Float getTotalPrice(){
		return getFee() + getProductPrice();
	}
	
	//删除购物项
	public void delProduct(Long skuId){
		Sku sku = new Sku();
		sku.setId(skuId);
		BuyerItem buyerItem = new BuyerItem();
		//sku设置给购物项
		buyerItem.setSku(sku);
		//删除购物项，购物项中比较两个购物项是否相等的equals方法是比较的skuId是否相等
		items.remove(buyerItem);
	}
	//清空购物车
	public void clearCart(){
		//调用list集合的clear方法
		items.clear();
	}
}

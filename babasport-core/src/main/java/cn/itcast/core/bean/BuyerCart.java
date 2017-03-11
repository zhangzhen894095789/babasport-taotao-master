package cn.itcast.core.bean;

import cn.itcast.core.bean.product.Sku;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * ���ﳵ
 * 
 * @author Administrator
 *
 */
public class BuyerCart {
	// ���ﳵ��������������
	private List<BuyerItem> items = new ArrayList<>();

	// ��ӹ��ﳵ����ӹ�����ķ���, buyerItem�Ѿ���д��equals��������� �����������id��ͬ������ͬһ������
	//����ӹ�����ķ����е�����ͬ�Ĺ�����Ĺ����������浽�������о�ok��
	public void addItems(BuyerItem item) {
		// contains�ײ���õ�Ҳ��item�ĵ�equals����
		if (items.contains(item)) {
			// ������ﳵ���Ѿ������˸ù�����
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

	
	

	//��Ʒ����
	@JsonIgnore
	public Integer getProductAmount(){
		Integer result = 0;
		//������Ʒ����
		for(BuyerItem it : items){
			result += it.getAmount();
		}
		return result;
	}
	//��Ʒ���
	@JsonIgnore
	public Float getProductPrice(){
		Float result = 0f;
		//������Ʒ���
		for(BuyerItem it : items){
			result += it.getAmount()*it.getSku().getPrice();
		}
		return result;
	}
	//�˷�
	@JsonIgnore
	public Float getFee(){
		Float result = 0f;
		//��˾Ҫ��
		if(getProductPrice() < 79){
			result = 5f;
		}
		return result;
	}
	//Ӧ�����
	@JsonIgnore
	public Float getTotalPrice(){
		return getFee() + getProductPrice();
	}
	
	//ɾ��������
	public void delProduct(Long skuId){
		Sku sku = new Sku();
		sku.setId(skuId);
		BuyerItem buyerItem = new BuyerItem();
		//sku���ø�������
		buyerItem.setSku(sku);
		//ɾ��������������бȽ������������Ƿ���ȵ�equals�����ǱȽϵ�skuId�Ƿ����
		items.remove(buyerItem);
	}
	//��չ��ﳵ
	public void clearCart(){
		//����list���ϵ�clear����
		items.clear();
	}
}

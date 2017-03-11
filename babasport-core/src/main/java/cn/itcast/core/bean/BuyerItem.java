package cn.itcast.core.bean;

import cn.itcast.core.bean.product.Sku;

/**
 * ������
 * 
 * @author Administrator
 *
 */
public class BuyerItem {

	//������
	private Sku sku;
	
	//ѡ����Ʒ����
	private Integer amount;
	
	//�Ƿ��л�
	
	private Boolean isHave = true;


	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Boolean getIsHave() {
		return isHave;
	}

	public void setIsHave(Boolean isHave) {
		this.isHave = isHave;
	}
	
	
	public Sku getSku() {
		return sku;
	}
	
	public void setSku(Sku sku) {
		this.sku = sku;
	}
	
	
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) //��ַ�Ƚ�
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())  //cn.itcast.core.bean.BuyerItem
			return false;
		BuyerItem other = (BuyerItem) obj;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.getId().equals(other.sku.getId()))
			return false;
		return true;
	}

	
}

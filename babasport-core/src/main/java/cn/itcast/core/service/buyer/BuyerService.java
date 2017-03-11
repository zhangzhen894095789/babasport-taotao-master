package cn.itcast.core.service.buyer;

import cn.itcast.core.bean.user.Buyer;

public interface BuyerService {

	//根据用户名查询用户
	public Buyer selectBuyerByUsername(String username);

}

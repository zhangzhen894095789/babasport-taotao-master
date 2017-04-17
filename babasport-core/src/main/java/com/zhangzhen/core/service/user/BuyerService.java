package com.zhangzhen.core.service.user;


import com.zhangzhen.core.bean.user.Buyer;

public interface BuyerService {
	//查询用户
	public Buyer selectBuyerByUserName(String username);

}

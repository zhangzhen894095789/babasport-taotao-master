package com.zhangzhen.core.service.user;


import com.zhangzhen.core.bean.user.Addr;

public interface AddrService {
	
	
	//查询用户的收货地址
	public Addr selectAddrByUserName(String username);

}

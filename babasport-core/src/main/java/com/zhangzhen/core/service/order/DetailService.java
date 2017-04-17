package com.zhangzhen.core.service.order;

import com.zhangzhen.core.bean.order.Detail;

import java.util.List;


public interface DetailService {
	
	//查询订单详情 通过订单ID
	public List<Detail> selectDetailListByOrderId(Long id);

}

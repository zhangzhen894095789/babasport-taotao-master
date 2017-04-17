package com.zhangzhen.core.service.order;

import com.zhangzhen.core.bean.BuyerCart;
import com.zhangzhen.core.bean.order.Order;
import com.zhangzhen.core.bean.order.OrderQuery;

import java.util.List;



public interface OrderService {
	
	//保存订单
	public void insertOrder(Order order, BuyerCart buyerCart);
	
	//查询订单结果集
	public List<Order> selectOrderListByState(OrderQuery orderQuery);
	
	//通过ID查询
	public Order selectOrderById(Long id);

}

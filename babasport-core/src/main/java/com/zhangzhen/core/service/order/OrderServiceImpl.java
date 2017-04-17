package com.zhangzhen.core.service.order;

import com.zhangzhen.core.bean.BuyerCart;
import com.zhangzhen.core.bean.BuyerItem;
import com.zhangzhen.core.bean.order.Detail;
import com.zhangzhen.core.bean.order.Order;
import com.zhangzhen.core.bean.order.OrderQuery;
import com.zhangzhen.core.dao.order.DetailDao;
import com.zhangzhen.core.dao.order.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.List;

/**
 * 保存订单
 * 保存订单详情
 * @author lx
 *
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private DetailDao detailDao;
	
	//保存订单
	public void insertOrder(Order order, BuyerCart buyerCart){
		// 保存订单表
		Jedis jedis = jedisPool.getResource();
		//生成订单号
		Long id = jedis.incr("oid");
		order.setId(id);
		//运费
		order.setDeliverFee(buyerCart.getFee());
		//订单金额
		order.setOrderPrice(buyerCart.getProductPrice());
		//总金额
		order.setTotalFee(buyerCart.getTotalPrice());
		//判断是否为到付
		//到付  0
		if(order.getPaymentWay() == 0) order.setIsPaiy(0);
		else{
			//待付款  1 
			order.setIsPaiy(1);
		}
		//订单状态
		order.setOrderState(0);
		//订单生成时间
		order.setCreateDate(new Date());
		//保存订单
		orderDao.insertSelective(order);
		
		// 保存订单详情表
		List<BuyerItem> items = buyerCart.getItems();
		//遍历购物项
		for (BuyerItem buyerItem : items) {
			//订单详情对象
			Detail detail = new Detail();
			//设置订单ID
			detail.setOrderId(id);
			//商品ID
			detail.setProductId(buyerItem.getSku().getProductId());
			//商品名称
			detail.setProductName(buyerItem.getSku().getProduct().getName());
			//颜色名
			detail.setColor(buyerItem.getSku().getColor().getName());
			//尺码
			detail.setSize(buyerItem.getSku().getSize());
			//价格
			detail.setPrice(buyerItem.getSku().getPrice());
			//购买的数量
			detail.setAmount(buyerItem.getAmount());
			//保存订单详情表
			detailDao.insertSelective(detail);

		}
		
	}
	//查询订单结果集
	public List<Order> selectOrderListByState(OrderQuery orderQuery){
		return orderDao.selectByExample(orderQuery);
	}
	
	//通过ID查询
	public Order selectOrderById(Long id){
		return orderDao.selectByPrimaryKey(id);
	}
}

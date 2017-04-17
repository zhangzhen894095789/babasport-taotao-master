package com.zhangzhen.core.service.order;

import com.zhangzhen.core.bean.order.Detail;
import com.zhangzhen.core.bean.order.DetailQuery;
import com.zhangzhen.core.dao.order.DetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 查询订单详情
 * @author lx
 *
 */
@Service
public class DetailServiceImpl implements DetailService {

	@Autowired
	private DetailDao detailDao;
	//查询订单详情 通过订单ID
	public List<Detail> selectDetailListByOrderId(Long id){
		//创建Detail条件对象
		DetailQuery detailQuery = new DetailQuery();
		//设置订单ID
		detailQuery.createCriteria().andOrderIdEqualTo(id);
		return detailDao.selectByExample(detailQuery);
	}
}

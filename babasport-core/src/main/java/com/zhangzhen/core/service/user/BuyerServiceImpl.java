package com.zhangzhen.core.service.user;

import com.zhangzhen.core.bean.user.Buyer;
import com.zhangzhen.core.dao.user.BuyerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 查询用户
 * @author lx
 *
 */
@Service
public class BuyerServiceImpl implements BuyerService{

	
	@Autowired
	private BuyerDao buyerDao;
	//查询用户
	public Buyer selectBuyerByUserName(String username){
		
		return buyerDao.selectByPrimaryKey(username);
	}
}

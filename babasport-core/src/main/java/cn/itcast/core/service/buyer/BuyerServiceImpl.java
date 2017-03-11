package cn.itcast.core.service.buyer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.bean.user.Buyer;
import cn.itcast.core.bean.user.BuyerQuery;
import cn.itcast.core.dao.user.BuyerDao;

@Service
@Transactional
public class BuyerServiceImpl implements BuyerService{
	@Autowired
	private BuyerDao buyerDao;

	//根据用户名查询用户
	public Buyer selectBuyerByUsername(String username) {

		BuyerQuery buyerQuery = new BuyerQuery();
		buyerQuery.createCriteria().andUsernameEqualTo(username);
		List<Buyer> buyer = buyerDao.selectByExample(buyerQuery);
		return buyer.get(0);
	}
	
}

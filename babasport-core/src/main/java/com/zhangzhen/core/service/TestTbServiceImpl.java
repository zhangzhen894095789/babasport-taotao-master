package com.zhangzhen.core.service;

import com.zhangzhen.core.bean.TestTb;
import com.zhangzhen.core.dao.TestTbDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 测试事务
 * @author lx
 *
 */
@Service
@Transactional
public class TestTbServiceImpl implements TestTbService {

	@Autowired
	private TestTbDao testTbDao;
	//添加
	public void addTestTb(TestTb testTb){
		
		testTbDao.addTestTb(testTb);
		
		throw new RuntimeException();
	}
	
}

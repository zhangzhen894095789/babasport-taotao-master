package cn.itcast;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.common.junit.SpringJunitTest;
import cn.itcast.core.bean.TestTb;
import cn.itcast.core.dao.TestTbDao;
import cn.itcast.core.service.TestTbService;
/**
 * Junit单元测试是基于Spring   注解式
 * @author lx
 *
 */
public class TestTestTb extends SpringJunitTest {


	@Autowired
	private TestTbDao testDao;
	@Autowired
	private TestTbService testTbService ;
	@Test//测试DAO层
	public void testAdd() throws Exception {
		TestTb testTb = new TestTb();
		testTb.setName("小明");
		
		testDao.addTestTb(testTb);
	}
	
	@Test//测试事务
	public void testServiceAdd() throws Exception {
		TestTb testTb = new TestTb();
		testTb.setName("小明2");
		
		testTbService.addTestTb(testTb);
	}
	
}

package cn.itcast;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.common.junit.SpringJunitTest;
import cn.itcast.core.bean.product.Product;
import cn.itcast.core.bean.product.ProductQuery;
import cn.itcast.core.bean.product.ProductQuery.Criteria;
import cn.itcast.core.dao.product.ProductDao;
/**
 * 测试product接口
 * @author Administrator
 * @company
 */
public class TestProduct extends SpringJunitTest {
	@Autowired
	private ProductDao productDao;
	@Test
	public void testProduct() {
//		ProductQuery productQuery = new ProductQuery();
//		Criteria criteria = productQuery.createCriteria();
////		criteria.andIsShowEqualTo(false);
//		criteria.andIsShowEqualTo(true);
////		int count = productDao.countByExample(productQuery);
////		System.out.println(count);
//		productQuery.setFields("id,name");
//		productQuery.setPageNo(1);
//		productQuery.setPageSize(3);
//		productQuery.setOrderByClause("id asc");
//		List<Product> products = productDao.selectByExample(productQuery);
//		for (Product p : products) {
//			System.out.println(p);
//		}
		
		Product product = new Product();
		product.setName("测试product");
		int id = productDao.insertSelective(product);
		//测试结果是添加商品是返回值是1，表示添加成功，并不是返回的添加成功的商品的id
		System.out.println("添加product时返回值是："+id);
	}
}

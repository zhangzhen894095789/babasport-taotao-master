package cn.itcast.core.service.product;

import cn.itcast.common.utils.page.Pagination;
import cn.itcast.core.bean.product.Brand;
import cn.itcast.core.bean.product.BrandQuery;
import cn.itcast.core.dao.product.BrandDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌管理Service
 * 
 * @author lx
 *
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService {
	@Autowired
	// 注入brandDao,调用mapper接口brandDao
	private BrandDao brandDao;

	@Override
	public List<Brand> selectBrandListByQuery(BrandQuery brandQuery) {
		return brandDao.selectBrandListByQuery(brandQuery);
	}

	// 返回分页对象的方法
	public Pagination selectPaginationByQuery(BrandQuery brandQuery) {
		// 创建分页对象
		// 当前页 页号
		// 每页数 3
		// 总条数（符合条件）
		int pageNo = brandQuery.getPageNo();
		int pageSize = brandQuery.getPageSize();
		int totalCount = brandDao.selectCounts(brandQuery);
		Pagination pagination = new Pagination(pageNo, pageSize, totalCount);
		// 分页对象中当前页数 设置给品牌条件对象BrandQuery 当前页
		brandQuery.setPageNo(pagination.getPageNo());
		
		// 设置查询的结果集（要求符合 3）
		pagination.setList(brandDao.selectBrandListByQuery(brandQuery));

		return pagination;
	}
	//添加品牌信息
	public void insertBrandById(Brand brand) {
		brandDao.insertBrandById(brand);
	}
	//批量删除
	public void deleteBrandByIds(Long[] ids) {
	
			brandDao.deleteBrandByIds(ids);
	}
	//修改品牌信息
	
	@Autowired
	//注入jedis连接池获取jedis操作对象
	private JedisPool jedisPool;
	
	public void updateBrandById(Brand brand) {
		//修改品牌信息的时候添加品牌信息到redis缓存服务器
		Jedis jedis = jedisPool.getResource();
		Map<String, String> map = new HashMap<>();
		map.put("id", String.valueOf(brand.getId()));
		map.put("name", brand.getName());
		jedis.hmset("brandId:"+brand.getId(),map );
		brandDao.updateBrandById(brand);
	}

	//根据id查找品牌
	public Brand selectBrandById(Long id) {
		return brandDao.selectBrandById(id);
	}
}

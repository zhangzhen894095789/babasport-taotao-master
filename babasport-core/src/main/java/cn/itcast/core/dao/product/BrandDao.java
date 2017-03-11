package cn.itcast.core.dao.product;

import java.util.List;

import cn.itcast.core.bean.product.Brand;
import cn.itcast.core.bean.product.BrandQuery;

/**
 * 品牌的Dao接口
 * 
 * @author lx
 *
 */
public interface BrandDao {

	// 查询结果集
	public List<Brand> selectBrandListByQuery(BrandQuery brandQuery);

	// 查询总记录数
	public int selectCounts(BrandQuery brandQuery);

	// 添加品牌
	public void insertBrandById(Brand brand);

	// 删除
	public void deleteBrandByIds(Long[] ids);

	//修改
	public void updateBrandById(Brand brand);
	//根据id查询品牌
	public Brand selectBrandById(Long id);

}

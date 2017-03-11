package cn.itcast.core.service.product;

import cn.itcast.common.utils.page.Pagination;
import cn.itcast.core.bean.product.Brand;
import cn.itcast.core.bean.product.BrandQuery;

import java.util.List;

public interface BrandService {

	// 查询品牌结果集
	public List<Brand> selectBrandListByQuery(BrandQuery brandQuery);
	
	//分页查询对象获取
	public Pagination selectPaginationByQuery(BrandQuery brandQuery);

	//添加品牌
	public void insertBrandById(Brand brand);
	//删除
	public void deleteBrandByIds(Long[] ids);
	//修改
	public void updateBrandById(Brand brand);

	//根据id查询品牌
	public Brand selectBrandById(Long id);
}

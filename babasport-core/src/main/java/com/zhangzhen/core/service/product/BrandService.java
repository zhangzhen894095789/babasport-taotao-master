package com.zhangzhen.core.service.product;

import cn.itcast.common.page.Pagination;
import com.zhangzhen.core.bean.product.Brand;
import com.zhangzhen.core.bean.product.BrandQuery;

import java.util.List;


public interface BrandService {

	// 查询品牌结果集
	public List<Brand> selectBrandListByQuery(BrandQuery brandQuery);

	// 查询品牌分页对象
	public Pagination selectPaginationByQuery(BrandQuery brandQuery);

	// 保存
	public void insertBrandById(Brand brand);

	// 删除
	public void deleteBrandByIds(Long[] ids);
	
	//查询品牌ID
	public Brand selectBrandById(Long id);
	
	//修改
	public void updateBrandById(Brand brand);
}

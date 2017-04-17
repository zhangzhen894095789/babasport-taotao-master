package com.zhangzhen.core.service.product;

import com.zhangzhen.core.bean.product.Feature;
import com.zhangzhen.core.bean.product.FeatureQuery;

import java.util.List;


/**
 * 材料管理
 * @author Administrator
 * @company
 */
public interface FeatureService {
	//查询材料信息
	public List<Feature> selectFeatureListByQuery(FeatureQuery featureQuery);
	
}

package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.core.bean.product.Feature;
import cn.itcast.core.bean.product.FeatureQuery;

/**
 * 材料管理
 * @author Administrator
 * @company
 */
public interface FeatureService {
	//查询材料信息
	public List<Feature> selectFeatureListByQuery(FeatureQuery featureQuery);
	
}

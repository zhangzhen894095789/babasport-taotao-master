package com.zhangzhen.core.service.product;

import com.zhangzhen.core.bean.product.Feature;
import com.zhangzhen.core.bean.product.FeatureQuery;
import com.zhangzhen.core.dao.product.FeatureDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 材质
 * @author lx
 *
 */
@Service
public class FeatureServiceImpl implements FeatureService{

	@Autowired
	FeatureDao featureDao;
	
	//查询材质 
	public List<Feature> selectFeatureListByQuery(FeatureQuery featureQuery){
		return featureDao.selectByExample(featureQuery);
	}
}

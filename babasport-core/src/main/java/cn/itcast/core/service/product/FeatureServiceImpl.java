package cn.itcast.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.bean.product.Feature;
import cn.itcast.core.bean.product.FeatureQuery;
import cn.itcast.core.dao.product.FeatureDao;

/**
 * 商品材料管理
 * @author Administrator
 * @company
 */
@Service
@Transactional
public class FeatureServiceImpl implements FeatureService {
	@Autowired
	private FeatureDao featureDao;
	//查询材料信息表数据
	public List<Feature> selectFeatureListByQuery(FeatureQuery featureQuery) {
		return featureDao.selectByExample(featureQuery);
	}
	
}

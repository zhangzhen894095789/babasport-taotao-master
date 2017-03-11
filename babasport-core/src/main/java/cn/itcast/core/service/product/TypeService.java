package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.core.bean.product.Type;
import cn.itcast.core.bean.product.TypeQuery;

/**
 * 商品类型属性接口
 * @author Administrator
 * @company
 */
public interface TypeService {
	//查询类型结果集
	public List<Type> selectTypeListByQuery(TypeQuery typeQuery);
	
}

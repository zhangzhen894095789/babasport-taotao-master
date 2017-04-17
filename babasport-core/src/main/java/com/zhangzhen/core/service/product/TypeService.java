package com.zhangzhen.core.service.product;

import com.zhangzhen.core.bean.product.Type;
import com.zhangzhen.core.bean.product.TypeQuery;

import java.util.List;


public interface TypeService {

	
	//加载类型
	public List<Type> selectTypeListByQuery(TypeQuery typeQuery);
}

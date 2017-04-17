package com.zhangzhen.core.service.product;

import com.zhangzhen.core.bean.product.Type;
import com.zhangzhen.core.bean.product.TypeQuery;
import com.zhangzhen.core.dao.product.TypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 类型
 * @author lx
 *
 */
@Service
public class TypeServiceImpl implements TypeService{

	
	@Autowired
	private TypeDao typeDao;
	//加载类型
	public List<Type> selectTypeListByQuery(TypeQuery typeQuery){
		return typeDao.selectByExample(typeQuery);
	}
}

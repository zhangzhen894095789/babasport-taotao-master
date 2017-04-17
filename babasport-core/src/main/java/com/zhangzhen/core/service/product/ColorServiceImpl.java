package com.zhangzhen.core.service.product;

import com.zhangzhen.core.bean.product.Color;
import com.zhangzhen.core.bean.product.ColorQuery;
import com.zhangzhen.core.dao.product.ColorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 颜色
 * @author lx
 *
 */
@Service
public class ColorServiceImpl implements ColorService{

	@Autowired
	private ColorDao colorDao;
	//查询颜色
	public List<Color> selectColorListByQuery(ColorQuery colorQuery){
		return colorDao.selectByExample(colorQuery);
	}
}


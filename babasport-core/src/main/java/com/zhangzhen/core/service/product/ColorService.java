package com.zhangzhen.core.service.product;

import com.zhangzhen.core.bean.product.Color;
import com.zhangzhen.core.bean.product.ColorQuery;

import java.util.List;


public interface ColorService {

	// 查询颜色
	public List<Color> selectColorListByQuery(ColorQuery colorQuery);
}

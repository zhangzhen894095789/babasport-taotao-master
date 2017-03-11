package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.core.bean.product.Color;
import cn.itcast.core.bean.product.ColorQuery;
/**
 * 颜色管理
 * @author Administrator
 * @company
 */
public interface ColorService {
	//查询颜色表数据结果集
	public List<Color> selectColorListByQuery(ColorQuery colorQuery);

}

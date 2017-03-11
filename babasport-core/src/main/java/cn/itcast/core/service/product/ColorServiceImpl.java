package cn.itcast.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.bean.product.Color;
import cn.itcast.core.bean.product.ColorQuery;
import cn.itcast.core.dao.product.ColorDao;

/**
 * 商品颜色管理
 * @author Administrator
 * @company
 */
@Service
@Transactional
public class ColorServiceImpl implements ColorService {
	@Autowired
	private ColorDao colorDao;
	//查询颜色结果集
	public List<Color> selectColorListByQuery(ColorQuery colorQuery) {
		return colorDao.selectByExample(colorQuery);
	}
	
}

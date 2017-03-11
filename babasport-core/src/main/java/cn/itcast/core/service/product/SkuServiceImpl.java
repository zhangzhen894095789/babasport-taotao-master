package cn.itcast.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.bean.product.Img;
import cn.itcast.core.bean.product.ImgQuery;
import cn.itcast.core.bean.product.Product;
import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.bean.product.SkuQuery;
import cn.itcast.core.bean.product.SkuQuery.Criteria;
import cn.itcast.core.dao.product.ColorDao;
import cn.itcast.core.dao.product.ImgDao;
import cn.itcast.core.dao.product.ProductDao;
import cn.itcast.core.dao.product.SkuDao;

/**
 * 库存管理
 * @author Administrator
 * @company
 */
@Service
@Transactional
public class SkuServiceImpl implements SkuService{
	@Autowired
	private SkuDao skuDao;
	@Autowired
	private ColorDao colorDao;
	//根据商品id查询库存信息
	public List<Sku> selectSkuListByProductId(Long productId) {
		SkuQuery skuQuery = new SkuQuery();
		Criteria criteria = skuQuery.createCriteria();
		
		criteria.andProductIdEqualTo(productId);
		//查询该商品id的所有库存信息
		List<Sku> skus = skuDao.selectByExample(skuQuery);
		//设置商品的颜色
		for (Sku sku : skus) {
			//库存中添加颜色对象  附加字段
			//根据库存中的颜色id查询颜色表中的数据设置到库存对象中
			sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));
		}
		return skus;
		
	}
	//修改更新库存信息
	public void updateSkuById(Sku sku) {
		skuDao.updateByPrimaryKeySelective(sku);
	}
	//根据id查询库存信息
	public List<Sku> selectSkuListWithStockByProductId(Long id) {
		SkuQuery skuQuery= new SkuQuery();
		//设置商品ID  及 库存大于0
		skuQuery.createCriteria().andProductIdEqualTo(id).andStockGreaterThan(0);
		//返回的Sku结果集进行遍历
		List<Sku> skus = skuDao.selectByExample(skuQuery);
		
		for(Sku sku :skus){
			sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));
		}
		
		return skus;
	}
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ImgDao imgDao;
	//通过skuId查询sku数据，颜色信息，商品信息
	public Sku selectSkuById(Long skuId){
		//第一步根据skuId查询库存信息
		Sku sku = skuDao.selectByPrimaryKey(skuId);
		//设置sku中的颜色对象信息
		sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));
		//查询商品的信息
		Product product = productDao.selectByPrimaryKey(sku.getProductId());
		//查询图片信息
		ImgQuery imgQuery= new ImgQuery();
		imgQuery.createCriteria().andProductIdEqualTo(sku.getProductId()).andIsDefEqualTo(true);
		List<Img> imgs = imgDao.selectByExample(imgQuery);
		product.setImg(imgs.get(0));
		sku.setProduct(product);
		return sku;
	}
}

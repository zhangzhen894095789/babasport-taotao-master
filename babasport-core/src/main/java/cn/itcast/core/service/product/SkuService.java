package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.core.bean.product.Sku;

public interface SkuService {
	// 根据商品id查询库存信息
	public List<Sku> selectSkuListByProductId(Long productId);

	// 修改/更新库存信息
	public void updateSkuById(Sku sku);

	// 根据商品id查询库存信息
	public List<Sku> selectSkuListWithStockByProductId(Long id);

	// 通过skuId查询sku数据，颜色信息，商品信息
	public Sku selectSkuById(Long skuId);
}

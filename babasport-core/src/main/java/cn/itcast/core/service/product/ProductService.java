package cn.itcast.core.service.product;

import cn.itcast.common.utils.page.Pagination;
import cn.itcast.core.bean.product.Product;
import cn.itcast.core.bean.product.ProductQuery;

public interface ProductService {

	// 分页查询商品信息
	public Pagination selectPaginationByQuery(ProductQuery productQuery);

	// 添加商品信息

	public void insertProduct(Product product);

	// 修改上下架信息
	public void isShow(Long[] ids);

	// 修改下架状态
	public void isNotShow(Long[] ids);

	//根据商品id查询商品信息
	public Product selectProductById(Long productId);

	//修改更新商品信息
	public void updateProductById(Product product);
	
	//根据商品id查询商品及商品图片信心
	public Product selectProductAndImgById(Long id);
}

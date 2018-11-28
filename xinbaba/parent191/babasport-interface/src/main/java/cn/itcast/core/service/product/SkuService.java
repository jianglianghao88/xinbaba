package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.core.bean.cart.BuyerCart;
import cn.itcast.core.bean.order.Order;
import cn.itcast.core.bean.product.Sku;

public interface SkuService {

	List<Sku> getSkuListByProductId(Long productId);

	void updateSkuById(Sku sku);

	Sku selectSkuById(Long id);

	void setBuyCartToRedis(BuyerCart buyerCart, String username);

	BuyerCart getBuyCartFromRedis(String username);

	void insertOrder(Order order, String username);

}

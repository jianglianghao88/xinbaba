package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.core.bean.product.Sku;

public interface SkuService {

	List<Sku> getSkuListByProductId(Long productId);

}

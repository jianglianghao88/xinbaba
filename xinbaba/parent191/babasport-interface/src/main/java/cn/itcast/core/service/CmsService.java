package cn.itcast.core.service;

import java.util.List;

import cn.itcast.core.bean.product.Product;
import cn.itcast.core.bean.product.Sku;

public interface CmsService {

	Product selectProdictById(Long productId);

	List<Sku> selectSkuListById(Long productId);

}

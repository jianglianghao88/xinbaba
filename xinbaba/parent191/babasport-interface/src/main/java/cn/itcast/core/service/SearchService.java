package cn.itcast.core.service;

import java.util.List;

import cn.itcast.core.bean.product.Product;

public interface SearchService {

	List<Product> searchProductByKeyWord(String keyword) throws Exception;

}

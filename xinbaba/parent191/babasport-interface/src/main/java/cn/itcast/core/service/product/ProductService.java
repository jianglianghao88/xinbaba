package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.Color;
import cn.itcast.core.bean.product.Product;

public interface ProductService {

	Pagination selectPaginationByQuery(String name, Long brandId,
			Boolean isShow, Integer pageNo);

	List<Color> selectColorList();
	
	void insertProdcut(Product product);

	void isShow(Long[] ids);

}

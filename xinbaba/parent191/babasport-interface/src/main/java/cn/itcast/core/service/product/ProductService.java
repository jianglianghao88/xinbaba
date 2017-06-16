package cn.itcast.core.service.product;

import java.util.List;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.Color;

public interface ProductService {

	Pagination selectPaginationByQuery(String name, Long brandId,
			Boolean isShow, Integer pageNo);

	List<Color> selectColorList();

}

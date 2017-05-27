package cn.itcast.core.service.product;

import cn.itcast.common.page.Pagination;

public interface BrandService {

	//查询分页对象
	public Pagination selectPaginationByQuery(String name,Integer isDisplay,Integer pageNo);
}

package cn.itcast.core.service.product;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.Brand;

public interface BrandService {

	//查询分页对象
	public Pagination selectPaginationByQuery(String name,Integer isDisplay,Integer pageNo);
	
	public Brand selectBrandById(Long id);
	
	public void updateBrandById(Brand brand);
	
	public void deletesByIds(Long[] ids);
}

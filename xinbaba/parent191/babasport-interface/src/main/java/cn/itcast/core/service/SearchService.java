package cn.itcast.core.service;

import java.util.List;

import cn.itcast.common.page.Pagination;

public interface SearchService {


	Pagination searchProductByKeyWord(Integer pageNo, String keyword, String price, Long brandId)
			throws Exception;

	void addProductToSolr(Long id);

}

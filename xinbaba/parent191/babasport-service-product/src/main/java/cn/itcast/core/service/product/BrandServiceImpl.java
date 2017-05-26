package cn.itcast.core.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.BrandQuery;
import cn.itcast.core.dao.product.BrandDao;

@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService{

	@Autowired
	private BrandDao brandDao;
	
	//分页查询
	public Pagination selectPaginationByQuery(String name , Integer isDisplay , Integer pageNo){
		BrandQuery brandQuery = new BrandQuery();
		brandQuery.setPageNo(Pagination.cpn(pageNo));
		
		brandQuery.setPageSize(3);
		
		if(null != name){
			brandQuery.setName(name);
		}
		if(null != isDisplay){
			brandQuery.setIsDisplay(isDisplay);
		}else{
			brandQuery.setIsDisplay(1);
		}
		
		Pagination pagination = new Pagination(brandQuery.getPageNo(), brandQuery.getPageSize(), brandDao.selectCount(brandQuery));
		
		pagination.setList(brandDao.selectBrandListByQuery(brandQuery));
		
		return pagination;
	}
}

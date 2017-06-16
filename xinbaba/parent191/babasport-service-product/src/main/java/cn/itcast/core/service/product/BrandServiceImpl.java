package cn.itcast.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.Brand;
import cn.itcast.core.bean.product.BrandQuery;
import cn.itcast.core.dao.product.BrandDao;

@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService{

	@Autowired
	private BrandDao brandDao;
	
	//分页查询
	@Override
	public Pagination selectPaginationByQuery(String name , Integer isDisplay , Integer pageNo){
		BrandQuery brandQuery = new BrandQuery();
		brandQuery.setPageNo(Pagination.cpn(pageNo));
		
		brandQuery.setPageSize(5);
		
		StringBuilder params = new StringBuilder(); 
		
		if(null != name){
			params.append("name=").append(name);
			brandQuery.setName(name);
		}
		if(null != isDisplay){
			params.append("&isDisplay=").append(isDisplay);
			brandQuery.setIsDisplay(isDisplay);
		}else{
			params.append("&isDisplay=").append(1);
			brandQuery.setIsDisplay(1);
			
		}
		
		Pagination pagination = new Pagination(brandQuery.getPageNo(), brandQuery.getPageSize(), brandDao.selectCount(brandQuery));
		
		pagination.setList(brandDao.selectBrandListByQuery(brandQuery));
		
		String url = "/brand/list.do";
		pagination.pageView(url, params.toString());
		
		return pagination;
	}

	@Override
	public Brand selectBrandById(Long id) {

		Brand brand = brandDao.selectBrandById(id);
		return brand;
	}

	@Override
	public void updateBrandById(Brand brand) {

		brandDao.updateBrandById(brand);
	}

	@Override
	public void deletesByIds(Long[] ids) {
		// TODO Auto-generated method stub
		brandDao.deletesByIds(ids);
	}

	@Override
	public void addBrand(Brand brand) {
		// TODO Auto-generated method stub
		brandDao.addBrand(brand);
	}

	@Override
	public List<Brand> selectBrandListByQuery(Integer isDisplay) {

		BrandQuery brandQuery = new BrandQuery();
		brandQuery.setIsDisplay(isDisplay);
		List<Brand> list = brandDao.selectBrandListByQuery(brandQuery);
		return list;
	}
}

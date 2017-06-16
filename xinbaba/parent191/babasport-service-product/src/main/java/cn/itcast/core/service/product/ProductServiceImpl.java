package cn.itcast.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.Color;
import cn.itcast.core.bean.product.ColorQuery;
import cn.itcast.core.bean.product.ProductQuery;
import cn.itcast.core.bean.product.ProductQuery.Criteria;
import cn.itcast.core.dao.product.ColorDao;
import cn.itcast.core.dao.product.ProductDao;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ColorDao colorDao;

	//分页查询
		@Override
		public Pagination selectPaginationByQuery(String name ,Long brandId , Boolean isShow , Integer pageNo){
			ProductQuery productQuery = new ProductQuery();
			productQuery.setPageNo(Pagination.cpn(pageNo));
			Criteria criteria = productQuery.createCriteria();
			
			StringBuilder params = new StringBuilder(); 
			
			if(null != name){
				params.append("name=").append(name);
				criteria.andNameLike("%"+name+"%");
			}
			if(null != brandId){
				criteria.andBrandIdEqualTo(brandId);
				params.append("&brandId=").append(brandId);
			}

			if(null != isShow){
				params.append("&isShow=").append(isShow);
				criteria.andIsShowEqualTo(isShow);
			}else{
				params.append("&isShow=").append(false);
				criteria.andIsShowEqualTo(false);
				
			}
			
			Pagination pagination = new Pagination(productQuery.getPageNo(), productQuery.getPageSize(), productDao.countByExample(productQuery),productDao.selectByExample(productQuery));
			
			String url = "/product/list.do";
			pagination.pageView(url, params.toString());
			
			return pagination;
		}
		@Override
		public List<Color> selectColorList(){
			
			ColorQuery example = new ColorQuery();
			example.createCriteria().andParentIdNotEqualTo(0L);
			return colorDao.selectByExample(example);
		}
}

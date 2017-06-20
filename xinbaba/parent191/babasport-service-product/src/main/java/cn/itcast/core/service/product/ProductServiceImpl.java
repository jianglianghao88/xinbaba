package cn.itcast.core.service.product;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.Color;
import cn.itcast.core.bean.product.ColorQuery;
import cn.itcast.core.bean.product.Product;
import cn.itcast.core.bean.product.ProductQuery;
import cn.itcast.core.bean.product.ProductQuery.Criteria;
import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.dao.product.ColorDao;
import cn.itcast.core.dao.product.ProductDao;
import cn.itcast.core.dao.product.SkuDao;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ColorDao colorDao;
	@Autowired
	private SkuDao skuDao;
	@Autowired
	private Jedis jedis;

	//分页查询
		@Override
		public Pagination selectPaginationByQuery(String name ,Long brandId , Boolean isShow , Integer pageNo){
			ProductQuery productQuery = new ProductQuery();
			productQuery.setPageNo(Pagination.cpn(pageNo));
			productQuery.setOrderByClause("id desc");
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
		//保存商品
		public void insertProdcut(Product product){
			Long id = jedis.incr("pno");
			if(id == null){
				jedis.set("pno", System.currentTimeMillis()+"");
				id = jedis.incr("pno");
			}
			product.setId(id);
			product.setIsShow(false);
			product.setIsDel(true);
			product.setCreateTime(new Date());
			
			productDao.insertSelective(product);
			
			Sku sku = new Sku();
			
			String[] colors = product.getColors().split(",");
			String[] sizes = product.getSizes().split(",");
			for (String color : colors) {
				for (String size : sizes) {
					sku.setProductId(product.getId());
					sku.setColorId(Long.parseLong(color));
					sku.setMarketPrice(999f);
					sku.setPrice(666f);
					sku.setSize(size);
					sku.setStock(10000);
					sku.setUpperLimit(5);
					sku.setDeliveFee(10f);
					sku.setCreateTime(new Date());
					skuDao.insertSelective(sku);
				}
			}
		}
}

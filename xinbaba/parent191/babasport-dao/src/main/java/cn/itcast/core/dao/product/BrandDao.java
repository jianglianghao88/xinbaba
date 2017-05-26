package cn.itcast.core.dao.product;

import java.util.List;

import cn.itcast.core.bean.product.Brand;
import cn.itcast.core.bean.product.BrandQuery;

public interface BrandDao {

	public List<Brand> selectBrandListByQuery(BrandQuery brandQuery);
}

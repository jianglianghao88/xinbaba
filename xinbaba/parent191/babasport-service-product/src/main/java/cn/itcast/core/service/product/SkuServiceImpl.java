package cn.itcast.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.bean.product.SkuQuery;
import cn.itcast.core.bean.product.SkuQuery.Criteria;
import cn.itcast.core.dao.product.ColorDao;
import cn.itcast.core.dao.product.SkuDao;

@Service("skuService")
@Transactional
public class SkuServiceImpl implements SkuService{

	@Autowired
	private SkuDao skuDao;
	@Autowired
	private ColorDao colorDao;
	
	@Override
	public List<Sku> getSkuListByProductId(Long productId){
		
		SkuQuery skuQuery = new SkuQuery();
		Criteria criteria = skuQuery.createCriteria();
		criteria.andProductIdEqualTo(productId);
		
		List<Sku> skus = skuDao.selectByExample(skuQuery);
		
		for (Sku sku : skus) {
			sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));
		}
		
		return skus;
	}
	
	@Override
	public void updateSkuById(Sku sku){
		
		skuDao.updateByPrimaryKeySelective(sku);
	}
	
}

package cn.itcast.core.service.product;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import cn.itcast.core.bean.cart.BuyerCart;
import cn.itcast.core.bean.cart.BuyerItem;
import cn.itcast.core.bean.order.Detail;
import cn.itcast.core.bean.order.Order;
import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.bean.product.SkuQuery;
import cn.itcast.core.bean.product.SkuQuery.Criteria;
import cn.itcast.core.dao.order.DetailDao;
import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.product.ColorDao;
import cn.itcast.core.dao.product.ProductDao;
import cn.itcast.core.dao.product.SkuDao;

@Service("skuService")
@Transactional
public class SkuServiceImpl implements SkuService{

	@Autowired
	private SkuDao skuDao;
	@Autowired
	private ColorDao colorDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private DetailDao detailDao;
	@Autowired
	private Jedis jedis;
	
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

	@Override
	public Sku selectSkuById(Long id) {

		Sku sku = skuDao.selectByPrimaryKey(id);
		sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));
		sku.setProduct(productDao.selectByPrimaryKey(sku.getProductId()));
		return sku;
		
	}

	@Override
	public void setBuyCartToRedis(BuyerCart buyerCart, String username) {

		List<BuyerItem> items = buyerCart.getItems();
		if(items.size() > 0){
			for (BuyerItem buyerItem : items) {
				
				jedis.hincrBy("buycart:"+username, String.valueOf(buyerItem.getSku().getId()),buyerItem.getAmount());
			}
		}
	}

	@Override
	public BuyerCart getBuyCartFromRedis(String username) {
		BuyerCart buyerCart = new BuyerCart();
		Map<String, String> map = jedis.hgetAll("buycart:"+username);
		if(null != map){
			Set<Entry<String, String>> entrySet = map.entrySet();
			for (Entry<String, String> entry : entrySet) {
				Sku sku = new Sku();
				sku.setId(Long.parseLong(entry.getKey()));
				BuyerItem buyerItem = new BuyerItem();
				buyerItem.setSku(sku);
				buyerItem.setAmount(Integer.parseInt(entry.getValue()));
				
				buyerCart.addItem(buyerItem);
			}
		}
		return buyerCart;
	}

	//保存订单
		@Override
		public void insertOrder(Order order,String username){
//			ID（订单编号）全国唯一Redis
			Long id = jedis.incr("oid");
			order.setId(id);
			//加载购物车
			BuyerCart buyerCart = getBuyCartFromRedis(username);
			List<BuyerItem> items = buyerCart.getItems();
			for (BuyerItem buyerItem : items) {
				buyerItem.setSku(selectSkuById(buyerItem.getSku().getId()));
			}
//			运费　　　　　由购物车提供
			order.setDeliverFee(buyerCart.getFee());
//			总价
			order.setTotalPrice(buyerCart.getTotalPrice());
//			订单金额
			order.setOrderPrice(buyerCart.getProductPrice());
//			支付状态：0到付1待付款,2已付款,3待退款,4退款成功,5退款失败
			if(order.getPaymentWay() == 1){
				order.setIsPaiy(0);
			}else{
				order.setIsPaiy(1);
			}
//			订单状态：0:提交订单 1:仓库配货 2:商品出库 3:等待收货 4:完成 5待退货 6已退货
			order.setOrderState(0);
//			时间  ：  后台程序自己写的
			order.setCreateDate(new Date());
//			用户ID ：   前台用户做注册 （ 用户名、密码）用户ID  由Redis生成   用户名： 用户ID  保存到Redis中   5个
			String uid = jedis.get(username);
			order.setBuyerId(Long.parseLong(uid));
			//保存订单
			orderDao.insertSelective(order);
			//保存订单详情
			for (BuyerItem buyerItem : items) {
				Detail detail = new Detail();
			//		ID 
			//		订单ID
					detail.setOrderId(id);
			//		商品编号
					detail.setProductId(buyerItem.getSku().getProductId());
			//		商品名称
					detail.setProductName(buyerItem.getSku().getProduct().getName());
			//		颜色
					detail.setColor(buyerItem.getSku().getColor().getName());
			//		尺码
					detail.setSize(buyerItem.getSku().getSize());
			//		价格
					detail.setPrice(buyerItem.getSku().getPrice());
		//		数量
					detail.setAmount(buyerItem.getAmount());
		//		购物车提供
					detailDao.insertSelective(detail);
			}

			
			//清空购物车
			jedis.del("buyerCart:fbb2016");
			//练习 hash 指定 K 
			
			
			
		}
	
}

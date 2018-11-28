package cn.itcast.core.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.core.bean.product.Color;
import cn.itcast.core.bean.product.Product;
import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.service.CmsService;
import cn.itcast.core.service.CmsServiceImpl;
import cn.itcast.core.service.SearchService;
import cn.itcast.core.service.staticpage.StaticPageService;

public class CustomerMessageListener implements MessageListener{

	@Autowired
	private CmsService cmsService;
	@Autowired
	private StaticPageService staticPageService;
	
	@Override
	public void onMessage(Message message) {

		ActiveMQTextMessage am =  (ActiveMQTextMessage) message;
		
		try {
			System.out.println("cms收到的商品id:"+am.getText());
			String id = am.getText();
			Map<String,Object> rootMap = new HashMap<>();
			
			Product product = cmsService.selectProdictById(Long.parseLong(id));
			
			List<Sku> skus = cmsService.selectSkuListById(Long.parseLong(id));
			Set<Color> colors = new HashSet<>();
			for (Sku sku : skus) {
				colors.add(sku.getColor());
			}
			
			rootMap.put("colors", colors);
			rootMap.put("product", product);
			rootMap.put("skus", skus);
			
			staticPageService.productStaticPage(rootMap , id);
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

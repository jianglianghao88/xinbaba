package cn.itcast.core.message;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.core.service.SearchService;

public class CustomerMessageListener implements MessageListener{

	@Autowired
	private SearchService searchService;
	
	@Override
	public void onMessage(Message message) {

		ActiveMQTextMessage am =  (ActiveMQTextMessage) message;
		
		try {
			searchService.addProductToSolr(Long.parseLong(am.getText()));
			System.out.println("solr收到的商品id:"+am.getText());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

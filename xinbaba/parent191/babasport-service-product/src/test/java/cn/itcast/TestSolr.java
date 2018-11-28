package cn.itcast;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.core.bean.TestTb;
import cn.itcast.core.bean.product.Product;
import cn.itcast.core.bean.product.ProductQuery;
import cn.itcast.core.bean.product.ProductQuery.Criteria;
import cn.itcast.core.dao.TestTbDao;
import cn.itcast.core.dao.product.ProductDao;
import cn.itcast.core.service.TestTbService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class TestSolr {
	
	@Autowired
	private SolrServer solrServer;

	@Test
	public void testSolrJ() throws Exception, IOException{
		
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", 4);
		doc.addField("name", "赵丽颖");
		
		solrServer.add(doc);
		
		solrServer.commit();
	}
}

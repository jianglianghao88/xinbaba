package cn.itcast.core.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.SolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.core.bean.product.Product;

@Service("searchService")
public class SearchServiceImpl implements SearchService{

	@Autowired
	private SolrServer solrServer;
	
	@Override
	public List<Product> searchProductByKeyWord(String keyword) throws Exception{
		
		List<Product> products = new ArrayList<>();
		
		
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(keyword);
		
		QueryResponse response = solrServer.query(solrQuery);
		
		SolrDocumentList docs = response.getResults();
		for (SolrDocument doc : docs) {
			Product product = new Product();
			
			product.setId(Long.parseLong((String)doc.get("id")));
			
			product.setName((String) doc.get("name_id"));
			
			product.setImgUrl((String) doc.get("url"));
			
			product.setPrice((Float) doc.get("price"));
			
			Integer brandId = (Integer) doc.get("brandId");
			product.setBrandId(Long.parseLong(String.valueOf(brandId)));
			
			products.add(product);
		}
		
		return products;
	}
}

package cn.itcast.core.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.Product;
import cn.itcast.core.bean.product.ProductQuery;
import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.bean.product.SkuQuery;
import cn.itcast.core.dao.product.ProductDao;
import cn.itcast.core.dao.product.SkuDao;

@Service("searchService")
public class SearchServiceImpl implements SearchService{

	@Autowired
	private SolrServer solrServer;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private SkuDao skuDao;
	
	@Override
	public Pagination searchProductByKeyWord(Integer pageNo , String keyword , String price, Long brandId) throws Exception{
		
		List<Product> products = new ArrayList<>();
		
		ProductQuery productQuery = new ProductQuery();
		
		productQuery.setPageNo(Pagination.cpn(pageNo));
		productQuery.setPageSize(12);
		
		StringBuilder params  = new StringBuilder();
		params.append("keyword=").append(keyword);
		
		
		
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.set("q", "name_ik:" + keyword);
		
		solrQuery.setStart(productQuery.getStartRow());
		solrQuery.setRows(productQuery.getPageSize());
		
		solrQuery.addSort("price", ORDER.asc);
		
		if(brandId != null){
			params.append("&brandId=").append(brandId);
			
			solrQuery.addFilterQuery("brandId:"+brandId);
		}
		
		if(null != price && !"".equals(price)){
			params.append("&price=").append(price);
			if(price.contains("-")){
				String[] p = price.split("-");
				solrQuery.addFilterQuery("price:["+p[0]+" TO " + p[1] + "]");
			}else{
				solrQuery.addFilterQuery("price:["+price+" TO *]");
			}
		}
		
		//高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("name_ik");
		solrQuery.setHighlightSimplePre("<span style='color:red'>");
		solrQuery.setHighlightSimplePost("</span>");
		
		QueryResponse response = solrServer.query(solrQuery);
		
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		
		SolrDocumentList docs = response.getResults();
		long numFound = docs.getNumFound();
		for (SolrDocument doc : docs) {
			Product product = new Product();
			
			product.setId(Long.parseLong((String)doc.get("id")));
			
			List<String> list = highlighting.get((String)doc.get("id")).get("name_ik");
			
			product.setName(list.get(0));
			
			product.setImgUrl((String) doc.get("url"));
			
			product.setPrice((Float) doc.get("price"));
			
			product.setBrandId(Long.parseLong(String.valueOf((Integer) doc.get("brandId"))));
			
			products.add(product);
		}
		
		Pagination pagination = new Pagination(productQuery.getPageNo(), productQuery.getPageSize(), (int)numFound , products );
		
		
		String url = "/search";
		pagination.pageView(url, params.toString());
		
		return pagination;
	}
	@Override
	public void addProductToSolr(Long id){
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", id);
		
		Product p = productDao.selectByPrimaryKey(id);
		doc.setField("name_ik", p.getName());
		doc.setField("url", p.getImages()[0]);
		
		SkuQuery skuQuery = new SkuQuery();
		skuQuery.createCriteria().andProductIdEqualTo(id);
		skuQuery.setOrderByClause("price asc");
		skuQuery.setPageNo(1);
		skuQuery.setPageSize(1);
		skuQuery.setFields("price");
		List<Sku> skus = skuDao.selectByExample(skuQuery);
		doc.setField("price", skus.get(0).getPrice());
		doc.setField("brandId", p.getBrandId());
		try {
			solrServer.add(doc);
			solrServer.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

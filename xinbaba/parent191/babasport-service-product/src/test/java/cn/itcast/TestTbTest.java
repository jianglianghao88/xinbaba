package cn.itcast;

import java.util.Date;
import java.util.List;

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
public class TestTbTest {

	@Autowired
	private TestTbService testTbService;
	@Autowired
	private ProductDao productDao;
	
	@Test
	public void testAdd(){
		TestTb tb = new TestTb();
		tb.setName("苍井空10");
		tb.setBirthday(new Date());
		System.out.println("ppp");
		testTbService.insertTestTb(tb);
		System.out.println("====ppp");
	}
	@Test
	public void testProduct(){
//		Product product = productDao.selectByPrimaryKey(441L);
//		System.out.println(product);
		
		ProductQuery example = new ProductQuery();
		Criteria criteria = example.createCriteria();
		criteria.andBrandIdEqualTo(1L);
		List<Product> list = productDao.selectByExample(example );
		System.out.println(list);
	}
}

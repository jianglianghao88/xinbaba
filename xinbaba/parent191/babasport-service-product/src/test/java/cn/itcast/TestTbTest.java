package cn.itcast;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.core.bean.TestTb;
import cn.itcast.core.dao.TestTbDao;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class TestTbTest {

	@Autowired
	private TestTbDao testTbDao;
	@Test
	public void testAdd(){
		TestTb tb = new TestTb();
		tb.setName("孙悟空");
		tb.setBirthday(new Date());
		System.out.println("ppp");
		System.out.println(testTbDao);
		testTbDao.insertTestTb(tb);
		System.out.println("====ppp");
	}
}

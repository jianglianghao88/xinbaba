package cn.itcast;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.core.bean.TestTb;
import cn.itcast.core.dao.TestTbDao;
import cn.itcast.core.service.TestTbService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class TestTbTest {

	@Autowired
	private TestTbService testTbService;
	@Test
	public void testAdd(){
		TestTb tb = new TestTb();
		tb.setName("苍井空10");
		tb.setBirthday(new Date());
		System.out.println("ppp");
		testTbService.insertTestTb(tb);
		System.out.println("====ppp");
	}
}

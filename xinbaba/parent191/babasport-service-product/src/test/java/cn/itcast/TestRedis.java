package cn.itcast;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;

public class TestRedis {
	@Autowired
	private Jedis jedis;

	@Test
	public void testRedis(){
		String a = "111";
		String[] str = a.split(",");
		String string = str[0];
		System.out.println(string);
	}
}

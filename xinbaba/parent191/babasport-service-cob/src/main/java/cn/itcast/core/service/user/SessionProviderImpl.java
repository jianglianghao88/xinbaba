package cn.itcast.core.service.user;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;

public class SessionProviderImpl implements SessionProvider{
	
	@Autowired
	private Jedis jedis;
	private Integer exp = 30;
	

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	@Override
	public void setUsernameToRedis(String token, String username) {

		jedis.set(token, username);
		
		jedis.expire(token, exp*60);
	}

	@Override
	public String getUsernameFromRedis(String token) {
		// TODO Auto-generated method stub
		String username = jedis.get(token);
		
		if(null != username){
			jedis.expire(token, exp*60);
		}
		
		return username;
	}

}

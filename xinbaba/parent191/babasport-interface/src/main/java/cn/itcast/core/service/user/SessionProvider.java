package cn.itcast.core.service.user;

public interface SessionProvider {

	public void setUsernameToRedis(String token , String username);
	
	public String getUsernameFromRedis(String token);
}

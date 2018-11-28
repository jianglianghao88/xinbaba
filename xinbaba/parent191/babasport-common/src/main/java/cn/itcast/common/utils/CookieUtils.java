package cn.itcast.common.utils;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	
	public static String getToken(HttpServletRequest request, HttpServletResponse response){
		
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null && cookies.length > 0){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals("BB_TOKEN")){
					return cookie.getValue();
				}
			}
		}
		
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		
		Cookie cookie = new Cookie("BB_TOKEN", token);
		cookie.setPath("/");
		cookie.setMaxAge(-1);
		
		response.addCookie(cookie);
		return token;
	}
}

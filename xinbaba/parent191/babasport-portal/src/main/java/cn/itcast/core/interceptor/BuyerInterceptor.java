package cn.itcast.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.common.utils.CookieUtils;
import cn.itcast.core.service.user.SessionProvider;

public class BuyerInterceptor implements HandlerInterceptor{
	
	@Autowired
	private SessionProvider sessionProvider;

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		String username = sessionProvider.getUsernameFromRedis(CookieUtils.getToken(request, response));
		if(null == username){
			response.sendRedirect("http://localhost:8083/login.aspx?returnUrl=http://localhost:8084/");
			return false;
		}
		return true;
	}

}

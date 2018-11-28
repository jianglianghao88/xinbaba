package cn.itcast.core.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.common.utils.CookieUtils;
import cn.itcast.core.bean.user.Buyer;
import cn.itcast.core.service.user.BuyerService;
import cn.itcast.core.service.user.SessionProvider;

@Controller
public class LoginController {
	
	@Autowired
	private BuyerService buyerService;
	@Autowired
	private SessionProvider sessionProvider;
	
	@RequestMapping(value="/login.aspx",method=RequestMethod.GET)
	public String toLogin(){
		
		return "login";
	}
	
	@RequestMapping(value="/login.aspx",method=RequestMethod.POST)
	public String toLogin(String username , String password ,String returnUrl , Model model , HttpServletRequest request, HttpServletResponse response){
		
		if(null != username){
			if(null != password){
				Buyer buyer = buyerService.selectBuyerByUsername(username);
				if(null != buyer){
					if(buyer.getPassword().equals(encode(password))){
						sessionProvider.setUsernameToRedis(CookieUtils.getToken(request, response), buyer.getUsername());
					
						return "redirect:"+returnUrl;
					}else{
						model.addAttribute("error", "密码错误");
					}
				}else{
					model.addAttribute("error", "用户名或密码错误");
				}
				
			}else{
				model.addAttribute("error", "密码不能为空");
			}
		}else{
			model.addAttribute("error", "用户名不能为空");
		}
		
		return "login";
	}

	public String encode(String password) {
		// TODO Auto-generated method stub
		
		String algorithm = "MD5";
		char[] hex = null;
		try {
			//MD5加密
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			
			byte[] bytes = digest.digest(password.getBytes());
			//十六进制
			hex = Hex.encodeHex(bytes);
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return new String(hex);
	}
	
	@RequestMapping("/isLogin.aspx")
	@ResponseBody
	public MappingJacksonValue isLogin(String callback,HttpServletRequest request, HttpServletResponse response){
		
		Integer result = 0;
		String token = CookieUtils.getToken(request, response);
		String username = sessionProvider.getUsernameFromRedis(token);
		if(null != username){
			result = 1;
		}
		
		MappingJacksonValue mjv = new MappingJacksonValue(result);
		mjv.setJsonpFunction(callback);
		
		return mjv;
	}
	
	public static void main(String[] args) {
		LoginController login = new LoginController();
		String encode = login.encode("123456");
		System.out.println(encode);
	}
}

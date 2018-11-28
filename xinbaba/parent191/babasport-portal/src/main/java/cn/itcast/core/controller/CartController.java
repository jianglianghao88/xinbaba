package cn.itcast.core.controller;

import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.utils.CookieUtils;
import cn.itcast.common.web.Contants;
import cn.itcast.core.bean.cart.BuyerCart;
import cn.itcast.core.bean.cart.BuyerItem;
import cn.itcast.core.bean.order.Order;
import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.service.product.SkuService;
import cn.itcast.core.service.user.SessionProvider;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CartController {

	@Autowired
	private SkuService skuService;
	@Autowired
	private SessionProvider sessionProvider;
	
	@RequestMapping("/addCart")
	public String addCart(Long skuId,Integer amount , Model model
			,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		//1：从Request中取Cookies、遍历Cookie 取出之前的购物车
		ObjectMapper om = new ObjectMapper();
		//不要NULL 不要转了
		om.setSerializationInclusion(Include.NON_NULL);
		//声明
		BuyerCart buyerCart = null;
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0){
			
			for (Cookie cookie : cookies) {
				if(Contants.CART.equals(cookie.getName())){
					buyerCart = om.readValue(cookie.getValue(), BuyerCart.class);
				}
			}
		}
		
		if(null == buyerCart){
			buyerCart = new BuyerCart();
		}
		
		Sku sku = new Sku();
		sku.setId(skuId);
		BuyerItem buyerItem = new BuyerItem();
		buyerItem.setSku(sku);
		buyerItem.setAmount(amount);
		
		buyerCart.addItem(buyerItem);
		
		String username = sessionProvider.getUsernameFromRedis(CookieUtils.getToken(request, response));
		if(username != null){
			skuService.setBuyCartToRedis(buyerCart,username);
			Cookie cookie = new Cookie(Contants.CART,null);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
		}else{
			StringWriter w = new StringWriter();
			om.writeValue(w , buyerCart);
			Cookie cookie = new Cookie(Contants.CART, w.toString());
			
			cookie.setMaxAge(60*60*24);
			cookie.setPath("/");
			
			response.addCookie(cookie);
		}
		
		
		
		
		return "redirect:/toCart";
	}
	

	@RequestMapping("/toCart")
	public String toCart(Model model
			,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		//1：从Request中取Cookies、遍历Cookie 取出之前的购物车
		ObjectMapper om = new ObjectMapper();
		//不要NULL 不要转了
		om.setSerializationInclusion(Include.NON_NULL);
		//声明
		BuyerCart buyerCart = null;
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0){
			
			for (Cookie cookie : cookies) {
				if(Contants.CART.equals(cookie.getName())){
					buyerCart = om.readValue(cookie.getValue(), BuyerCart.class);
					break;
				}
			}
		}
		String username = sessionProvider.getUsernameFromRedis(CookieUtils.getToken(request, response));
		if(username != null){
			if(null != buyerCart){
				skuService.setBuyCartToRedis(buyerCart,username);
				Cookie cookie = new Cookie(Contants.CART,null);
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
			buyerCart = skuService.getBuyCartFromRedis(username);
			
		}
		if(null != buyerCart){
			List<BuyerItem> items = buyerCart.getItems();
			for (BuyerItem buyerItem : items) {
				buyerItem.setSku(skuService.selectSkuById(buyerItem.getSku().getId()));
			}
		}
		model.addAttribute("buyerCart", buyerCart);
		return "cart";
	}
	
	@RequestMapping("/buyer/trueBuy")
	public String trueBuy(Long[] skuIds , Model model , HttpServletRequest request,HttpServletResponse response){
		
		String username = sessionProvider.getUsernameFromRedis(CookieUtils.getToken(request, response));
		BuyerCart buyerCart = skuService.getBuyCartFromRedis(username);
		List<BuyerItem> items = buyerCart.getItems();
		Boolean flag = false;
		if(items.size() > 0){
			for (BuyerItem buyerItem : items) {
				buyerItem.setSku(skuService.selectSkuById(buyerItem.getSku().getId()));
				
				if(buyerItem.getAmount() > buyerItem.getSku().getStock()){
					buyerItem.setIsHave(false);
					flag = true;
				}
			}
			if(flag){
				model.addAttribute("buyerCart", buyerCart);
				return "cart";
			}
			
			
		}else{
			return "redirect:/toCart";
		}
		
		return "order";
	}
	@RequestMapping("/buyer/submitOrder")
	public String submitOrder(Order order , Model model , HttpServletRequest request,HttpServletResponse response){
		String username = sessionProvider.getUsernameFromRedis(CookieUtils.getToken(request, response));
		skuService.insertOrder(order, username);
		
		return "success";
	}
}

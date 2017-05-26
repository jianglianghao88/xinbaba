package cn.itcast.core.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.bean.TestTb;
import cn.itcast.core.service.TestTbService;

@Controller
@RequestMapping("/control")
public class CenterController {
//	
//	@Autowired
//	private TestTbService testTbService;

	@RequestMapping(value="/index.do")
	public String showIndex(Model model){
/*		TestTb tb = new TestTb();
		tb.setName("苍井空vs钱金磊");
		tb.setBirthday(new Date());
		System.out.println("ppp");
		testTbService.insertTestTb(tb);
		System.out.println("====ppp");
*/		return "index";
	}
	@RequestMapping(value="/top.do")
	public String top(Model model){
	
		
		return "top";
	}
	
	@RequestMapping(value="/main.do")
	public String main(Model model){
	
		
		return "main";
	}
	
	@RequestMapping(value="/left.do")
	public String left(Model model){
	
		
		return "left";
	}
	
	@RequestMapping(value="/right.do")
	public String right(Model model){
	
		
		return "right";
	}
	@RequestMapping(value="/frame/product_main.do")
	public String product_main(Model model){
		
		
		return "/frame/product_main";
	}
	@RequestMapping(value="/frame/product_left.do")
	public String product_left(Model model){
		
		
		return "/frame/product_left";
	}
}

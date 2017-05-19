package cn.itcast.core.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.core.bean.TestTb;
import cn.itcast.core.service.TestTbService;

@Controller
public class CenterController {
	
	@Autowired
	private TestTbService testTbService;

	@RequestMapping(value="/test/index.do")
	public String showIndex(Model model){
		TestTb tb = new TestTb();
		tb.setName("苍井空vs钱金磊");
		tb.setBirthday(new Date());
		System.out.println("ppp");
		testTbService.insertTestTb(tb);
		System.out.println("====ppp");
		return "index";
	}
}

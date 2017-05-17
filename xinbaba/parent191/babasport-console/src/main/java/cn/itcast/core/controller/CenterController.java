package cn.itcast.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CenterController {

	@RequestMapping(value="/test/index.do")
	public String showIndex(Model model){
		
		return "index";
	}
}

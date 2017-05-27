package cn.itcast.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.service.product.BrandService;

@Controller
public class BrandController {

	@Autowired
	private BrandService brandService;
	
	@RequestMapping(value="/brand/list.do")
	public String list(String name , Integer isDisplay , Integer pageNo , Model model){
		
		Pagination pagination = brandService.selectPaginationByQuery(name, isDisplay, pageNo);
	
		model.addAttribute("pagination", pagination);
		
		model.addAttribute("name", name);
		
		model.addAttribute("isDisplay", isDisplay == null ? 1 : isDisplay);
		
		return "brand/list";
	}
}

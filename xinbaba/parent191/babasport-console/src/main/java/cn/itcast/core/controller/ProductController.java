package cn.itcast.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.Brand;
import cn.itcast.core.bean.product.Color;
import cn.itcast.core.bean.product.Product;
import cn.itcast.core.service.product.BrandService;
import cn.itcast.core.service.product.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;
	
	@RequestMapping("/product/list.do")
	public String showProdcut(String name,Long brandId,Boolean isShow,
			Integer pageNo, Model model){
		List<Brand> brands = brandService.selectBrandListByQuery(1);
		
		Pagination pagination = productService.selectPaginationByQuery(name, brandId, isShow, pageNo);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("brands", brands);
		model.addAttribute("name", name);
		model.addAttribute("brandId", brandId);
		model.addAttribute("isShow", isShow);
		return "product/list";
	}
	@RequestMapping("/product/toAdd.do")
	public String toAdd(Model model){
		List<Brand> brands = brandService.selectBrandListByQuery(1);
		model.addAttribute("brands", brands);
		
		List<Color> colors = productService.selectColorList();
		
		model.addAttribute("colors", colors);
		
		return "product/add";
	}
	@RequestMapping("/product/add.do")
	public String add(Product product){
		productService.insertProdcut(product);
		
		return "redirect:/product/list.do";
	}
	
	@RequestMapping("/product/isShow.do")
	public String isShow(Long[] ids){
		productService.isShow(ids);
		
		return "forward:/product/list.do";
	}
}

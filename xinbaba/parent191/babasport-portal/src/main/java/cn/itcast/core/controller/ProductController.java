package cn.itcast.core.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.common.page.Pagination;
import cn.itcast.core.bean.product.Brand;
import cn.itcast.core.bean.product.Color;
import cn.itcast.core.bean.product.Product;
import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.service.CmsService;
import cn.itcast.core.service.SearchService;
import cn.itcast.core.service.product.BrandService;

@Controller
public class ProductController {

	@Autowired
	private SearchService searchService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private CmsService cmsService;
	
	@RequestMapping("/")
	public String index(){

		return "index";
	}
	
	@RequestMapping("/search")
	public String search(String keyword, Long brandId , String price , Integer pageNo , Model model) throws Exception{
		
		List<Brand> brands = brandService.selectBrandListByRedis();
		
		Pagination pagination = searchService.searchProductByKeyWord(pageNo,keyword,price,brandId);
		model.addAttribute("pagination", pagination);
		model.addAttribute("keyword", keyword);
		model.addAttribute("brands", brands);
		model.addAttribute("price", price);
		model.addAttribute("brandId", brandId);
		Map<String,String> map = new HashMap<>();
		
		if(brandId != null){
			for (Brand brand : brands) {
				if(brand.getId() == brandId){
					
					map.put("品牌", brand.getName());
					break;
				}
			}
			
		}
		if(null != price){
			if(price.contains("-")){
				map.put("价格", price);
			}else{
				map.put("价格", price + "以上");
			}
		}
		
		model.addAttribute("map", map);
		return "search";
	}
	@RequestMapping("/product/detail")
	public String showDetail(Long id , Model model){
		
		Product product = cmsService.selectProdictById(id);
		
		List<Sku> skus = cmsService.selectSkuListById(id);
		Set<Color> colors = new HashSet<>();
		for (Sku sku : skus) {
			colors.add(sku.getColor());
		}
		
		model.addAttribute("colors", colors);
		model.addAttribute("product", product);
		model.addAttribute("skus", skus);
		
		
		return "product";
	}
}

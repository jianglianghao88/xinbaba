package cn.itcast.core.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itcast.core.bean.product.Sku;
import cn.itcast.core.service.product.SkuService;

@Controller
public class SkuController {
	
	@Autowired
	private SkuService skuService;

	@RequestMapping("/sku/list.do")
	public String list(Long productId,Model model){
		
		List<Sku> skus = skuService.getSkuListByProductId(productId);
		
		model.addAttribute("skus", skus);
		
		return "sku/list";
	}
	
	@RequestMapping("/sku/addSku.do")
	@ResponseBody
	public String addSku(@RequestBody Sku sku){
		
		skuService.updateSkuById(sku);
		
		JSONObject jo = new JSONObject();
		jo.put("message", "保存成功");
		
		return jo.toString();
	}
}

package cn.itcast.core.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.itcast.common.web.Contants;
import cn.itcast.core.service.product.UploadService;

@Controller
public class UploadController {

	@Autowired
	private UploadService uploadService;
	
	@RequestMapping("upload/uploadPic.do")
	public void uploadPic(@RequestParam(required=false) MultipartFile pic , HttpServletResponse response) throws IOException{
		
		String path = uploadService.uploadPic(pic.getBytes(),pic.getOriginalFilename());
		
		String url = Contants.IMG_URL + path;
		
		JSONObject jo = new JSONObject();
		
		jo.put("url", url);
		
		response.setContentType("application/json;charset=UTF-8");
		
		response.getWriter().write(jo.toString());
	}
	@RequestMapping("upload/uploadPics.do")
	@ResponseBody
	public List<String> uploadPics(@RequestParam(required=false) MultipartFile[] pics , HttpServletResponse response) throws IOException{
		
		List<String> list = new ArrayList<>();
		
		if(pics != null){
			for (MultipartFile pic : pics) {
				
				String path = uploadService.uploadPic(pic.getBytes(),pic.getOriginalFilename());
				String url = Contants.IMG_URL + path;
				list.add(url);
			}
		}
		
		
		
	return list;
	}
}

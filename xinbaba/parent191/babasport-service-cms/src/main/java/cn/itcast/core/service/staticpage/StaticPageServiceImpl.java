package cn.itcast.core.service.staticpage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service("staticPageService")
public class StaticPageServiceImpl implements StaticPageService,ServletContextAware{

	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	private ServletContext servletContext;
	
	@Override
	public void productStaticPage(Map rootMap,String id){
		Configuration conf = freeMarkerConfigurer.getConfiguration();
		
		String path = getPath("/html/product/"+id+".html");
		File file = new File(path);
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		
		Writer out = null;
		
		try {
			Template template = conf.getTemplate("product.html");
			out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			template.process(rootMap, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}
	
	public String getPath(String path){
		return servletContext.getRealPath(path);
	}
	
	
}

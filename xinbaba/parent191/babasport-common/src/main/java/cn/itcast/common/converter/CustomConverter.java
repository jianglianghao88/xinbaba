package cn.itcast.common.converter;


import org.springframework.core.convert.converter.Converter;

public class CustomConverter implements Converter<String, String>{

	@Override
	public String convert(String source) {

		if(null != source){
			source = source.trim();
			if(!"".equals(source)){
				return source;
			}
		}
		return null;
		
	}

}

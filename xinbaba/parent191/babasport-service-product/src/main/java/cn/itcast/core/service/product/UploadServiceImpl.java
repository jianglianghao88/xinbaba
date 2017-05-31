package cn.itcast.core.service.product;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.itcast.common.utils.FastDFSClient;

@Service("uploadService")
public class UploadServiceImpl implements UploadService{

	@Override
	public String uploadPic(byte[] pic , String name) {
		String path = null;
		ClassPathResource resource = new ClassPathResource("fdfs_client.conf");
		
		String conf = resource.getClassLoader().getResource("fdfs_client.conf").getPath();
		try {
			FastDFSClient client = new FastDFSClient(conf);
			path = client.uploadFile(pic, FilenameUtils.getExtension(name));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return path;
	}

}

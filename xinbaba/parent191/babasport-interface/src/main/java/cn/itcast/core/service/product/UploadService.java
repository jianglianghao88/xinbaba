package cn.itcast.core.service.product;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

	public String uploadPic(byte[] pic , String name);
}

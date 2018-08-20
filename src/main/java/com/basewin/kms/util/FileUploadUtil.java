package com.basewin.kms.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 通用上传方法
 */
public class FileUploadUtil {

	public static String uploadFile(MultipartFile file, HttpSession session,String savePath ) {
		//springmvc上传，首先要在服务端创建上传文件
		//创建文件涉及到文件file的使用，必须是绝对路径转换为相对路径的方法，过时方法request.getRealPath("");
		//获取绝对路径的不过时方法
		String realPath = session.getServletContext().getRealPath("uploadfiles/"+savePath);
		//获取原文件名称
		String oldFileName = file.getOriginalFilename();
		//获取原文件后缀
		String suffix  = oldFileName.substring(oldFileName.lastIndexOf("."));
		//创建新的文件名称UUID。readomUUID(),获取一个36位的随机数，并且不会重复
		String newFileName = UUID.randomUUID()+suffix;
		//在服务端创建文件
		File files=new File(realPath, newFileName);
		//判断文件目录是否存在，不存在创建所有父目录
		if (!files.exists()) {
			files.mkdirs();
		}
		//利用文件读写把本地文件读取文件内容到服务新建文件里面
		try {
			file.transferTo(files);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "uploadfiles/"+savePath+"/"+newFileName;
	}

}

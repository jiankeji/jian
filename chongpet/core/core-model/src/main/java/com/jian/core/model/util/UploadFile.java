package com.jian.core.model.util;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 上传文件 删除文件的工具类 
 * @author shen
 *
 */
public class UploadFile {

	/**
	 * 
	 * @param file   文件
	 * @param imgPath  文件地址文件夹
	 * @param imgName  文件名
	 * @throws Exception
	 */
	public static void uploadFileUtil(byte[] file, String imgPath, String imgName) throws Exception {
		File targetFile = new File(imgPath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(imgPath + imgName);
		out.write(file);
		out.flush();
		out.close();
	}
	
	/**
	 * 删除服务上的文件
	 * @param filePath 文件
	 * @return
	 */
	public static boolean deleteServerFile(String filePath){
		boolean delete_flag = false;
		File file = new File(filePath);
		if (file.exists() && file.isFile() && file.delete())
			delete_flag = true;
        else
        	delete_flag = false;
		return delete_flag;
	}

}

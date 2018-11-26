package com.jian.core.model.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class PropertiesUtil {	
	
	private static Properties properties;
	
	/**
	 * 读取属性文件key对应的value,单例
	 * @param key
	 * @return
	 * @throws IOException 
	 */
	public static synchronized String getProperty(String filename, String key) {
		if (properties == null) {
			try {
				properties = readProperty(filename);				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties.getProperty(key);
	}
	
	/**
	 * 读取属性文件
	 * @param file 文件的相对路径
	 * @throws IOException 
	 */
	private static <T> Properties readProperty(String file) throws IOException{
		Properties proper = new Properties();
		InputStream in  = null;
		try {
			in = PropertiesUtil.class.getClassLoader().getResourceAsStream(file);
			proper.load(in);
		} catch (IOException e) {
			System.out.println("sorry, the file you request does not exist:["+file+"]");
			throw new IOException();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				System.out.println("sorry, the file you request does not exist:["+file+"]");
				throw new IOException();
			}
		}
		return proper;
	}
	
	/**
	 * 读取属性文件key对应的value
	 * @param key
	 * @return
	 * @throws IOException 
	 * @throws IOException 
	 */
	public static String getProperty(String key) {
		return StringUtil.trimStr(getProperty("sysconfig.properties", key));
	}
}

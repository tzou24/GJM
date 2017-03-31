package com.abina.generate.template.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件操作工具类
 * @author abina
 * @date 2017-03-31
 */
public class PropertiesUtils {
	
	// 根据key读取value
	public static String readValue(String filePath, String key) {
		String basepath = PropertiesUtils.class.getClass().getResource("/").getPath();
		filePath = basepath + filePath;
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

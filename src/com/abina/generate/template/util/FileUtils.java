package com.abina.generate.template.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	/**
	 * 创建目录文件
	 * 
	 * @param path
	 */
	public static void mkDir(String path) {
		File fd = null;
		try {
			fd = new File(path);
			if (!fd.exists()) {
				fd.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fd = null;
		}
	}

	/**
	 * 判断文件夹是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean checkFolderExist(String path) {
		Boolean tmpBool = Boolean.valueOf(false);
		File file = new File(path);
		if (file.isDirectory()) {
			tmpBool = Boolean.valueOf(true);
		}
		return tmpBool;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean checkFileExist(String path) {
		Boolean tmpBool = Boolean.valueOf(false);
		File file = new File(path);
		if (file.exists()) {
			tmpBool = Boolean.valueOf(true);
		}
		return tmpBool;
	}

	/**
	 * 读文本文件的内容
	 * 
	 * @param path
	 * @return
	 */
	public static String readFile(String path) {
		String tmpStr = "";
		try {
			FileInputStream fis = new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				tmpStr += line;
				tmpStr += "\r\n"; // 补上换行符
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return tmpStr;
	}

	/**
	 * 写内容到文件
	 * 
	 * @param path
	 * @param content
	 */
	public static void wirteFile(String path, String content) {
		String FileContent = content;
		try {
			File fileDir = new File(path);
			Writer out = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(fileDir), Charset.forName("utf-8")));
			out.append(FileContent);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param Path
	 *            被删除文件的文件名
	 * @return void
	 */
	public static void deleteFile(String path) {
		File file = new File(path);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
		}
	}

	private static ArrayList<String> filelist = new ArrayList<String>();

	public static List<String> getFiles(String filePath) throws Exception {
		File root = new File(filePath);
		File[] files = root.listFiles();
		if (files == null)
			throw new Exception("文件模版路径不存在");
		for (File file : files) {
			if (file.isDirectory()) {
				// 递归调用
				getFiles(file.getAbsolutePath());
			} else {
				filelist.add(file.getAbsolutePath());
			}
		}
		return filelist;
	}

	public static void main(String[] args) {
		// FileKit.wirteFile("E:\\tmp\\test\\1.txt", "alskdjflkasdfklasdl;kf");
	}
}

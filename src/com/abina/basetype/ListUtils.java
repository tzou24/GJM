package com.abina.basetype;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * list 集合工具类
 * 
 * @author abina
 * @date 20170309
 */
public class ListUtils {

	/**
	 * list string to array
	 * 
	 * @param strList
	 * @return
	 */
	public static String[] list2Array(List<String> strList) {
		String[] array = new String[strList.size()];
		array = strList.toArray(array);
		return array;
	}
	
	/**
	 * remove list string value
	 * @param value
	 */
	public static void StrListRemove(String value) {
		List<String> strList = new ArrayList<String>();
		Iterator<String> iterator = strList.iterator();
		while (iterator.hasNext()) {
			String temp = (String) iterator.next();
			if (temp.equals(value)) {
				iterator.remove();
			}
		}
	}
}

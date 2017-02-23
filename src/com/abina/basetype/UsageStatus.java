package com.abina.basetype;

import java.util.Arrays;
import java.util.List;

/**
 * 可用状态枚举类
 * @author Administrator
 *
 */
public enum UsageStatus {

	ENABLED_STATUS("1", "有效的"),
	
	DISABLED_STATUS("0", "失效的");
	
	/**
	 * 可用状态的值。
	 */
	private String value;
	/**
	 * 可用状态的中文描述。
	 */
	private String text;
	
	/** 
	  * @param status 可用状态的值 
	  * @param desc 可用状态的中文描述 
	  */ 
   private UsageStatus(String status, String desc) { 
	    value = status; 
	    text = desc; 
	}

	/**
	 * @return 当前枚举对象的值。
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return 当前状态的中文描述。
	 */
	public String getText() {
		return text;
	}

	/**
	 * 根据可用状态的值获取枚举对象。
	 * 
	 * @param status
	 *            可用状态的值
	 * @return 枚举对象
	 */
	public static UsageStatus getInstance(String status) {
		UsageStatus[] allStatus = UsageStatus.values();
		for (UsageStatus us : allStatus) {
			if (us.getValue().equalsIgnoreCase(status)) {
				return us;
			}
		}
		throw new IllegalArgumentException("status值非法，没有符合可用状态的枚举对象");
	}
	
	/**
	 * 获取枚举集合
	 * @return
	 */
	public static List<UsageStatus> getList(){
		UsageStatus[] values = UsageStatus.values();
		List<UsageStatus> list = Arrays.asList(values);
		return list;
	}
	
}

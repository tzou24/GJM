package com.abina.basetype;

/**
 * 正则表达式验证工具类
 * 
 * @author abina
 * @date 20170228
 */
public class RegexUtils {

	private static String emailRegex = "^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	private static String phoneRegex = "^((1\\d{10}$)$)";

	/**
	 * 验证Email地址是否有效
	 * 
	 * @param sEmail
	 * @return true-通过，false-不通过
	 */
	public static boolean validEmail(String sEmail) {
		return sEmail.matches(emailRegex);
	}

	/**
	 * 判断一个字符串是否为手机号码
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean validMobile(String mobile) {
		return mobile.matches(phoneRegex);
	}

	/**
	 * 判断一个字符串是否为数值类型数据
	 * 
	 * @param strValue
	 * @return
	 */
	public static boolean isNumeric(String strValue) {
		if (StrUtils.isEmpty(strValue)) {
			return false;
		} else {
			if (strValue.indexOf(".") > -1) {
				return strValue.matches("^[\\d]+.[\\d]*$");
			} else {
				return strValue.matches("^[\\d]+$");
			}
		}
	}
	
	/**
	 * 过滤字符串当中对应的emoji符号
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {
		if(StrUtils.isNotEmpty(source)){  
	        return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");  
	    }else{  
	        return source;  
	    } 
	}
}

package com.abina.basetype;

import javax.servlet.http.HttpServletRequest;

/**
 * 路径工具类
 * @author abina
 * @date 20170228
 */
public class PathUtils {

	public static void main(String[] args) {
		HttpServletRequest request = null;
		request.getRealPath("");
		//项目名称, example:food-security
		request.getContextPath(); 
		//WEB项目的全路径,explame:F:\apache-tomcat-7.0.73-food\webapps\food-security
		request.getSession().getServletContext().getRealPath("/");
		
		///projectName/testPage.jsp
		String requestURI = request.getRequestURI();
		
		//example:projectName/jsp/testPage.jsp
		request.getServletPath();
		
		//example:D://ProjectName/WebRoot/WEB-INF/classes/...
		Class.class.getClass().getResource("/").getPath();
	}
}

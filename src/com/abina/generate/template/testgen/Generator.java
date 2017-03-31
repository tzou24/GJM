package com.abina.generate.template.testgen;

import java.util.HashMap;
import java.util.Map;

import com.abina.generate.template.util.FreemarkerUtils;

public class Generator {

	public static void main(String[] args) {
		FreemarkerUtils freemarkerUtils = new FreemarkerUtils();
		Map<String, Object> params = new HashMap<String, Object>();
		GenProject p = new GenProject("cxc", "一个简单的项目");
		params.put("project", p);
		freemarkerUtils.parse(params);
	}
}

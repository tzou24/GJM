package com.abina.generate.template.testgen;

public class GenProject {

	private String name;
	private String des;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public GenProject(String name, String des) {
		super();
		this.name = name;
		this.des = des;
	}
}

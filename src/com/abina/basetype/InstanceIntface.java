package com.abina.basetype;

import java.io.File;

/**
 * 快速实例化接口
 * @author abina
 * @date 2017-03-31
 */
public class InstanceIntface {

	public static final Filter fileAccpet = new Filter() {
		
		@Override
		public boolean accpet(File file) {
			return file.exists();
		}
	};
}


interface Filter{
	boolean accpet(File file);
}
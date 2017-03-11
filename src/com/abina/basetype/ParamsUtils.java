package com.abina.basetype;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

/**
 * 参数校验工具类
 * 
 * @author abina
 * @date 20170228
 */
public class ParamsUtils extends PropertyUtilsBean {

	/**
	 * 获取Map，并验证对象中key值
	 * 
	 * @param params
	 * @param key
	 * @return
	 */
	public static Object getParamsByKey(Map<String, Object> params, String key) {
		if (params.isEmpty() || key == null) {
			return null;
		}
		if (params.get(key) != null && !"".equals(params.get(key).toString())) {
			return params.get(key);
		} else {
			return null;
		}
	}

	/**
	 * 将对象转MAP
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> transBean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					map.put(key, value);
				}
			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}
		return map;
	}

	/**
	 * 对象拷贝 数据对象空值不拷贝到目标对象
	 * 
	 * @param databean 待拷贝对象
	 * @param tobean 目标对象
	 * @throws NoSuchMethodException
	 */
	public static void copyBeanNotNull2Bean(Object databean, Object tobean) throws Exception {
		PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(databean);
		for (int i = 0; i < origDescriptors.length; i++) {
			String name = origDescriptors[i].getName();
			// String type = origDescriptors[i].getPropertyType().toString();
			if ("class".equals(name)) {
				continue; // No point in trying to set an object's class
			}
			if (PropertyUtils.isReadable(databean, name) && PropertyUtils.isWriteable(tobean, name)) {
				try {
					Object value = PropertyUtils.getSimpleProperty(databean, name);
					if (value != null) {
						getInstance().setSimpleProperty(tobean, name, value);
					}
				} catch (java.lang.IllegalArgumentException ie) {
					; // Should not happen
				} catch (Exception e) {
					; // Should not happen
				}

			}
		}
	}
	
	/**
	   * Map内的key与Bean中属性相同的内容复制到BEAN中
	   * 对于存在空值的取默认值
	   * @param bean Object
	   * @param properties Map
	   * @param defaultValue String
	   * @throws IllegalAccessException
	   * @throws InvocationTargetException
	   */
	  public static void copyMap2Bean(Object bean, Map properties, String defaultValue) throws
	      IllegalAccessException, InvocationTargetException {
	      // Do nothing unless both arguments have been specified
	      if ( (bean == null) || (properties == null)) {
	          return;
	      }
	      // Loop through the property name/value pairs to be set
	      Iterator names = properties.keySet().iterator();
	      while (names.hasNext()) {
	          String name = (String) names.next();
	          // Identify the property name and value(s) to be assigned
	          if (name == null) {
	              continue;
	          }
	          Object value = properties.get(name);
	          try {
	              Class clazz = PropertyUtils.getPropertyType(bean, name);
	              if (null == clazz) {
	                  continue;
	              }
	              String className = clazz.getName();
	              if (className.equalsIgnoreCase("java.sql.Timestamp")) {
	                  if (value == null || value.equals("")) {
	                      continue;
	                  }
	              }
	              if (className.equalsIgnoreCase("java.lang.String")) {
	                  if (value == null) {
	                      value = defaultValue;
	                  }
	              }
	              getInstance().setSimpleProperty(bean, name, value);
	          }
	          catch (NoSuchMethodException e) {
	              continue;
	          }
	      }
	  }
}

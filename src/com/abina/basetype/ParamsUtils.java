package com.abina.basetype;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数校验工具类
 * @author abina
 * @date 20170228
 */
public class ParamsUtils {

	
	/**
	 * 获取Map，并验证对象中key值
	 * @param params
	 * @param key
	 * @return
	 */
	public static Object getParamsByKey(Map<String, Object> params, String key){
		if(params.isEmpty() || key == null){
			return null;
		}
		if(params.get(key) != null && !"".equals(params.get(key).toString())){
			return params.get(key);
		}else{
			return null;
		}
	}
	
	/**
	 * 将对象转MAP
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> transBean2Map(Object obj) {  
        if(obj == null){  
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
}

package com.abina.basetype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;

/**
 * @author abina
 * @date 2018-03-08
 */
public class CollectionUtil {

	/**
	 * @方法名称 pullCollectionsField
	 * @功能描述 <pre>将 Map 集合中的属性抽取组装集合</pre>
	 * @创建时间 2017年8月15日 下午8:17:31
	 * @param list 集合数据
	 * @param field 抽离属性名称
	 * @return 抽离属性Map集合
	 */
	public static List<Map<String, Object>> pullCollectionsField(List<Map<String, Object>> list, String field) {
		List<Map<String, Object>> ctrIdsMap = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : list) {
			String ctrId = MapUtils.getString(map, field);
			Map<String, Object> ctrMap = new HashMap<String, Object>();
			ctrMap.put(field, ctrId);
			ctrIdsMap.add(ctrMap);
		}
		return ctrIdsMap;
	}
	
	/**
	 * @方法名称 popCollections
	 * @功能描述 <pre>移除一对多，只查询一对一</pre>
	 * @创建时间 2017年12月14日 下午11:36:24
	 * @param srcMap 源 Map 集合
	 * @param popMap 需要塞入 Map 集合
	 * @param field 关联属性 {'源属性', '关联属性'}
	 * @param attrs 需要插入的属性, 为 null 则插入全部
	 * @return
	 */
	public static List<Map<String, Object>> popCollections(List<Map<String, Object>> srcMap, List<Map<String, Object>> popMap, String[] field, String[] attrs) {
		return popCollections(srcMap, popMap, field, attrs, true);
	}
	
	
	/**
	 * @方法名称 popCollections
	 * @功能描述 <pre>循环插入已匹配属性Map中</pre>
	 * @创建时间 2017年8月15日 下午8:25:36
	 * @param srcMap 源 Map 集合
	 * @param popMap 需要塞入 Map 集合
	 * @param field 关联属性 {'源属性', '关联属性'}
	 * @param attrs 需要插入的属性, 为 null 则插入全部
	 * @param remove 是否移除一对一查询
	 * @return
	 */
	public static List<Map<String, Object>> popCollections(List<Map<String, Object>> srcMap, List<Map<String, Object>> popMap, String[] field, String[] attrs, boolean remove) {
		if(field.length > 0) {
			String popField = field[0];
			if(field.length == 2) {
				popField = field[1];
			}
			Iterator<Map<String, Object>> iter = srcMap.iterator();
			while (iter.hasNext()) {
				Map<String, Object> src = (Map<String, Object>) iter.next();
				boolean isRel = false;
				String srctValue = MapUtils.getString(src, field[0]);
				for (Map<String, Object> pop : popMap) {
					String popValue = MapUtils.getString(pop, popField);
					if (srctValue.equals(popValue)) {
						src.putAll(keepAttrs(pop, attrs));
						if (remove) {
							popMap.remove(pop);
						}
						isRel = true;
						break;
					} 
				}
				//如果没有匹配，则未达成关联条件，移除之
				if (remove && !isRel) {
					iter.remove();
				}
			}
		}
		return srcMap;
	}
	
	/**
	 * @方法名称 keepAttrs
	 * @功能描述 <pre>保留属性</pre>
	 * @创建时间 2017年11月28日 下午8:12:23
	 * @param pop
	 * @param attrs 存在相同属性名称，但是语义不同，可以使用 & 进行分隔，如：cstId&oblgCstId 
	 * @return
	 */
	public static Map<String, Object> keepAttrs(Map<String, Object> pop, String... attrs) {
		if (attrs == null || attrs.length == 0) {
			return pop;
		} else {
			Map<String, Object> popTmp = new HashMap<String, Object>();
			List<String> atList = Arrays.asList(attrs);
			Map<String, String> attrMap = new HashMap<String, String>();
			for (String attr : atList) {
				if (attr.contains("&")) {
					attrMap.put(attr.split("&")[0], attr.split("&")[1]);
				} else {
					attrMap.put(attr, attr);
				}
			}
			for (Entry<String, Object> entry : pop.entrySet()) {
				if (attrMap.containsKey(entry.getKey())) {
					popTmp.put(attrMap.get(entry.getKey()), entry.getValue());
				}
			}
			return popTmp;
		}
	}
	
	/**
	 * @方法名称 convertStrList
	 * @功能描述 <pre>转换集合</pre>
	 * @创建时间 2017年9月9日 下午6:17:02
	 * @param mapList Map 集合
	 * @param mapKey 转换集合 key 名称
	 * @return
	 */
	public static List<String> convertStrList(List<Map<String, Object>> mapList, String mapKey) {
		List<String> resultList = new ArrayList<String>();
		for (Map<String, Object> map : mapList) {
			if (map.containsKey(mapKey)) {
				resultList.add(MapUtils.getString(map, mapKey));
			}
		}
		return resultList;
	}
}

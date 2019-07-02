package com.libs.utils.dataUtil.dealUtil;//package myUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
///** FastJSON解析工具类 */
//public class FastJSONHelper {
//
//	public FastJSONHelper() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	/**
//	 * 用fastjson 将json字符串解析为一个 JavaBean
//	 *
//	 * @param jsonString
//	 * @param cls
//	 * @return
//	 */
//	public static <T> T getPerson(String jsonString, Class<T> cls) {
//		T t = null;
//		try {
//			t = JSON.parseObject(jsonString, cls);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return t;
//	}
//
//	/**
//	 * 用fastjson 将json字符串 解析成为一个 List<JavaBean> 及 List<String>
//	 *
//	 * @param jsonString
//	 * @param cls
//	 * @return
//	 */
//	public static <T> List<T> getPersons(String jsonString, Class<T> cls) {
//		List<T> list = new ArrayList<T>();
//		try {
//			list = JSON.parseArray(jsonString, cls);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return list;
//	}
//
//	/**
//	 * 用fastjson 将jsonString 解析成 List<Map<String,Object>>
//	 *
//	 * @param jsonString
//	 * @return
//	 */
//	public static List<Map<String, Object>> getListMap(String jsonString) {
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		try {
//			// 两种写法
//			// list = JSON.parseObject(jsonString, new
//			// TypeReference<List<Map<String, Object>>>(){}.getType());
//
//			list = JSON.parseObject(jsonString,
//					new TypeReference<List<Map<String, Object>>>() {
//					});
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return list;
//
//	}
//}
//

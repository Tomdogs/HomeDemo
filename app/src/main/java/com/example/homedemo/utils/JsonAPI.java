package com.example.homedemo.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json解析工具类
 *
 * @author huangchao
 * @since 1.0
 */
public class JsonAPI {
	/**
	 * JSON转换成对象
	 *
	 * @since 1.0
	 * @author huangchao
	 * @param target
	 * @param json
	 * @return
	 * @throws Exception
	 */

	public static <T> T jsonToObject(Class<T> target, JSONObject json)
			throws Exception {
		try {
			Gson gson = new Gson();
			return gson.fromJson(json.toString(), target);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * String转换成对象
	 *
	 * @since 1.0
	 * @author huangchao
	 * @param jsonStr
	 * @param target
	 * @throws Exception
	 */

	public static <T> T parseJsonToObj(String jsonStr, Class<T> target)
			throws Exception {
		try {
			Gson gson = new Gson();
			return gson.fromJson(jsonStr, target);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 对象转化成JSON
	 *
	 * @since 1.0
	 * @author huangchao
	 * @param obj
	 * @return
	 */

	public static String objectToJson(Object obj) throws Exception {
		try {
			Gson gson = new Gson();
			return gson.toJson(obj);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * JSON转换成对象数组
	 *
	 * @since 1.0
	 * @author huangchao
	 * @param target
	 * @param array
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public static <T> List<T> jsonToList(Class<T> target, JSONArray array)
			throws JSONException {
		List<T> list = new ArrayList<T>();

		for (int i = 0; i < array.length(); i++) {
			if (target == String.class) {
				String json = array.getString(i);
				list.add((T) json);
			} else {
				JSONObject json = array.getJSONObject(i);
				try {
					list.add(jsonToObject(target, json));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	/**
	 * List转换成JSON字符串
	 *
	 * @since 1.0
	 * @author huangchao
	 * @param list
	 * @return
	 */

	public static <T> String listToJson(List<T> list) throws Exception {
		Gson gson = new Gson();
		return gson.toJson(list);
	}


	/**
	 * 把一个map变成json字符串
	 * @param map
	 * @return
	 */
	public static String parseMapToJson(Map<?, ?> map) {
		try {
			Gson gson = new Gson();
			return gson.toJson(map);
		} catch (Exception e) {
			ILog.e("转换异常");
		}
		return null;
	}

	/**
	 * 把一个json字符串变成对象
	 * @param json
	 * @param cls
	 * @return
	 */
	public static <T> T parseJsonToBean(String json, Class<T> cls) {
		Gson gson = new Gson();
		T t = null;
		try {
			t = gson.fromJson(json, cls);
		} catch (Exception e) {

		}
		return t;
	}

	/**
	 * 把json字符串变成map
	 * @param json
	 * @return
	 */
	public static HashMap<String, ?> parseJsonToMap(String json) {
		Gson gson = new Gson();
		Type type = new TypeToken<HashMap<String, ?>>() {
		}.getType();
		HashMap<String, ?> map = null;
		try {
			map = gson.fromJson(json, type);
		} catch (Exception e) {
		}
		return map;
	}

	/**
	 * 把json字符串变成集合
	 * params: new TypeToken<List<yourbean>>(){}.getType(),
	 *
	 * @param json
	 * @param type  new TypeToken<List<yourbean>>(){}.getType()
	 * @return
	 */
	public static List<?> parseJsonToList(String json, Type type) {
		Gson gson = new Gson();
		List<?> list = gson.fromJson(json, type);
		return list;
	}

	/**
	 *
	 * 获取json串中某个字段的值，注意，只能获取同一层级的value
	 *
	 * @param json
	 * @param key
	 * @return
	 */
	public static String getFieldValue(String json, String key) {
		if (TextUtils.isEmpty(json))
			return null;
		if (!json.contains(key))
			return "";
		JSONObject jsonObject;
		String value = null;
		try {
			jsonObject = new JSONObject(json);
			value = jsonObject.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return value;
	}

}

package com.rogrand.kkmy.util;



import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * action_info数据处理工具类
 * 
 * @author qimin
 * 
 */
public class ActinoInfoUtils {

	/**
	 * 解密信息并返回MAP
	 */
	public static Map<String, Object> info2map(String info, String mallKey) {
		try {
			return jsonToMap(DecodeUtils.enData(info, mallKey));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new HashMap<String, Object>();
		}
	}

	/**
	 * 返回JSON加密字符串
	 */
	public static String map2info(Map<String, Object> map, String mallKey) {
		try {
			return DecodeUtils.deData(mapToJson(map), mallKey);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * map转化为json
	 */
	public static Map<String, Object> jsonToMap(String json) throws UnsupportedEncodingException {
		return JsonMapper.getInstance()
				.fromJson(json, JsonMapper.getInstance().createCollectionType(Map.class, String.class, Object.class));

	}

	/**
	 * json转化为MAP
	 */
	public static String mapToJson(Map<String, Object> map) throws UnsupportedEncodingException {
		// System.out.println(JsonMapper.getInstance().toJson(map));
		return JsonMapper.getInstance().toJson(map);
	}

}

package com.campusnews.network;

import com.alibaba.fastjson.JSON;
import com.campusnewes.bean.RootPojo;
/**
 * Json解析器
 */
public class JsonParser {
  /** shared gson object */
//static Gson gson = new Gson();

/**
 * cast json to Class<T>
 * 
 * @param jsonText json string
 * @param cls Class<T>
 * @return T
 */
public static <T extends RootPojo> T fromJson(String jsonText, Class<T> cls) {
  T item = (T) JSON.parseObject(jsonText, cls);
  return item;
}

/**
 * cast object to json text
 * 
 * @param src Object
 * @return String
 */
public static String toJson(Object src) {
  return JSON.toJSONString(src);
}
}

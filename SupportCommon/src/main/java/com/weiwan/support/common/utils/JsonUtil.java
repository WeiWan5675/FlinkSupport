package com.weiwan.support.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Set;

/**
 * @author: XuYang
 * @date: 2019-01-04 17:17
 * @description: json 工具类
 **/
public class JsonUtil {


    /**
     * 转换 json格式从驼峰到下划线格式
     * @param jsonObject
     * @return
     */
    public static JSONObject convertHumpToUnderline(JSONObject jsonObject) {
        String jsonString = JSON.toJSONString(jsonObject, new com.weiwan.common.utils.SnakeKeyFilter());
        JSONObject resultJson = JSONObject.parseObject(jsonString);
        return resultJson;
    }

    public final static void convert(Object json) {
        if (json instanceof JSONArray) {
            JSONArray arr = (JSONArray) json;
            for (Object obj : arr) {
                convert(obj);
            }
        } else if (json instanceof JSONObject) {
            JSONObject jo = (JSONObject) json;
            Set<String> keys = jo.keySet();
            //此处不能直接遍历keys，不然将报ConcurrentModificationException异常
            String[] array = keys.toArray(new String[keys.size()]);
            for (String key : array) {
                Object value = jo.get(key);
                String[] key_strs = key.split("_");
                if (key_strs.length > 1) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < key_strs.length; i++) {
                        String ks = key_strs[i];
                        if (!"".equals(ks)) {
                            if (i == 0) {
                                sb.append(ks);
                            } else {
                                int c = ks.charAt(0);
                                if (c >= 97 && c <= 122) {
                                    int v = c - 32;
                                    sb.append((char) v);
                                    if (ks.length() > 1) {
                                        sb.append(ks.substring(1));
                                    }
                                } else {
                                    sb.append(ks);
                                }
                            }
                        }
                        jo.remove(key);
                        jo.put(sb.toString(), value);
                    }
                    convert(value);
                }
            }
        }

    }

    public final static Object convert(String json) {
        Object obj = JSONObject.parse(json);
        convert(obj);
        return obj;
    }
}

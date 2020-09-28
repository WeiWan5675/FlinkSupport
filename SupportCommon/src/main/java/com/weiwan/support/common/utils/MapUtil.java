package com.weiwan.support.common.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.internal.LinkedHashTreeMap;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MapUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();


    public static Map<String, Object> convertToHashMap(Map<String, Object> target){
        for(Map.Entry<String, Object> tmp : target.entrySet()){
            if (null == tmp.getValue()) {
                continue;
            }

            if(tmp.getValue().getClass().equals(LinkedTreeMap.class) ||
                    tmp.getValue().getClass().equals(LinkedHashTreeMap.class)){
                Map<String, Object> convert = convertToHashMap((Map)tmp.getValue());
                HashMap<String, Object> hashMap = new HashMap<>(convert.size());
                hashMap.putAll(convert);
                tmp.setValue(hashMap);
            }
        }

        return target;
    }


    public static Map<String,Object> objectToMap(Object obj) throws Exception{
        return objectMapper.readValue(objectMapper.writeValueAsBytes(obj), Map.class);
    }

    public static <T> T jsonStrToObject(String jsonStr, Class<T> clazz) throws JsonParseException, JsonMappingException, JsonGenerationException, IOException {
        return  objectMapper.readValue(jsonStr, clazz);
    }
}

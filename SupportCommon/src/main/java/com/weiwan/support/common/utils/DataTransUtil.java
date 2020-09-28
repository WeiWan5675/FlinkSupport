package com.weiwan.support.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class DataTransUtil{

    /**
     * 交换map的key value
     * @param map
     * @return
     */
    public static Map<String, String> swapMapKeyValue(Map<String,String> map) {
        Map<String, String> hashMap=new HashMap<String, String>();
        Set<String> set = map.keySet();
        for (String s:set) {
            hashMap.put(map.get(s),s);
        }
        return hashMap;
    }

}

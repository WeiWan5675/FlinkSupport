package com.weiwan.support.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author: XuYang
 * @date: 2019-01-21 15:23
 * @description: 常用方法工具类
 **/
public class CommonUtil {
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }


    /**
     * @param var
     * @param defaultVar
     * @param <P>
     * @return val
     */
    public static <P> P getNotNullOrDefault(P var, P defaultVar) {
        P val = null;
        if (var != null) {
            val = var;
        } else {
            val = defaultVar;
        }
        return val;
    }


    /**
     * 可以闯进来各种不同的对象,参数 都不能为空 为空返回true
     * @param obj
     * @return 为空 true
     */
    public static boolean checkParameterIsNull(Object ... obj) {
        for (Object o : obj) {
            if (isNullOrEmpty(o)) {
                return true;
            }
        }
        return false;
    }
}

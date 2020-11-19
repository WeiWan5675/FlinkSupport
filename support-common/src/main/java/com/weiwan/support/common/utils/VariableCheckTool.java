/*
 *      Copyright [2020] [xiaozhennan1995@gmail.com]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *      http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weiwan.support.common.utils;

import com.weiwan.support.common.exception.SupportException;

import java.util.Collection;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/10/16 11:33
 * @Package: com.weiwan.support.launcher.Util
 * @ClassName: VariableCheckTool
 * @Description:
 **/
public class VariableCheckTool {


    /**
     * 检查参数是否为null,如果为null,返回默认值
     *
     * @param obj        待检查参数
     * @param defaultVar 默认值
     * @param <T>        参数类型
     * @return
     */
    public static <T> T checkNullOrDefault(T obj, T defaultVar) {
        if (obj != null) {
            return obj;
        }
        return defaultVar;
    }


    /**
     * 检查参数是否为null
     *
     * @param obj 待检查参数
     * @param <T>
     * @return 如果为null, 将会抛出 {@link NullPointerException}
     */
    public static <T> boolean checkNullExecption(T obj) {
        if (obj == null) {
            throw new NullPointerException("variable is empty please check");
        }
        return false;
    }


    /**
     * 检查参数是否为null
     *
     * @param obj 待检查参数
     * @param <T>
     * @return 如果为null, 将会抛出 {@link com.weiwan.support.common.exception.SupportException}
     */
    public static <T> boolean checkNullSupportExecption(T obj) {
        if (obj == null) {
            throw new SupportException("variable is empty please check");
        }
        return false;
    }

    public static boolean checkNullOrEmpty(Object obj) {
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
                if (!checkNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }


    /**
     * 可以闯进来各种不同的对象,参数 都不能为空 为空返回true
     *
     * @param obj
     * @return 为空 true
     */
    public static boolean checkVersIsNull(Object... obj) {
        for (Object o : obj) {
            if (checkNullOrEmpty(o)) {
                return true;
            }
        }
        return false;
    }

}

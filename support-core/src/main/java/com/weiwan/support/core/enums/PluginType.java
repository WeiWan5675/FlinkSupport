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
package com.weiwan.support.core.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/8 14:07
 * @Package: PluginType
 * @ClassName: PluginType
 * @Description:
 **/
public enum PluginType {


    STREAM("STREAM", "流模式"),
    BATCH("BATCH", "批模式");
    private String code;
    private String msg;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    PluginType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static boolean isStream(String pluginType) {
        if (StringUtils.isBlank(pluginType)) {
            return false;
        }
        if (PluginType.valueOf(pluginType.toUpperCase()) == PluginType.STREAM) {
            return true;
        }
        return false;
    }
}

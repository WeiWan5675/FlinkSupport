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

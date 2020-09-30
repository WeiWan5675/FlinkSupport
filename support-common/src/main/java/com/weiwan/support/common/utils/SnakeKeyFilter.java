package com.weiwan.support.common.utils;

import com.alibaba.fastjson.serializer.NameFilter;

/**
 * @author: XuYang
 * @date: 2019-01-05 18:31
 * @description:
 **/
public class SnakeKeyFilter implements NameFilter {

    public String process(Object o, String name, Object value) {
        return Underline2CamelUtil.camel2Underline(name);
    }
}

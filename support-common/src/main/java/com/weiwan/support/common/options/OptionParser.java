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
package com.weiwan.support.common.options;

import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/8/13 13:36
 * @Package: com.weiwan.common.options.OptionParser
 * @ClassName: OptionParser
 * @Description:
 **/
public class OptionParser {

    private String[] args;
    private JCommander jCommander;


    public OptionParser(String[] args) {
        jCommander = new JCommander();
        jCommander.setAcceptUnknownOptions(true);
        this.args = args;
    }


    public <T> T parse(Class<T> tClass) {
        T options = null;
        try {
            options = tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        jCommander.addObject(options);
        try {
            jCommander.parse(args);
        } catch (ParameterException e) {
            System.out.println(e.getLocalizedMessage());
            jCommander.usage();
        }
        return options;
    }


    public static <T> String[] optionToArgs(T options) throws Exception {
        Map<String, Object> optionToMap = optionToMap(options);
        List<String> argsList = new ArrayList<>();
        for (String key : optionToMap.keySet()) {
            if (optionToMap.get(key) instanceof Map) {
                Map dMap = (Map) optionToMap.get(key);
                for (Object dKey : dMap.keySet()) {
                    String dValue = String.valueOf(dMap.get(dKey));
                    if (dValue != null) {
                        argsList.add("-D" + String.valueOf(dKey) + "=" + dValue);
                    }
                }
                continue;
            }

            String var = String.valueOf(optionToMap.get(key));
            if (StringUtils.isEmpty(var) || "null".equalsIgnoreCase(var)) {
                continue;
            }
            if (StringUtils.isNotEmpty(var)) {
                if ("true".equalsIgnoreCase(var)) {
                    argsList.add(key);
                    continue;
                } else {
                    if (!"false".equalsIgnoreCase(var)) {
                        argsList.add(key);
                        argsList.add(var);
                    }
                }
            }
        }

        String[] argsAll = argsList.toArray(new String[argsList.size()]);
        return argsAll;
    }


    public static <T> Map<String, Object> optionToMap(T options) throws Exception {
        Field[] optionFields = getSuperFields(options.getClass());
        Map<String, Object> res = new HashMap();
        for (Field field : optionFields) {
            Object fieldValue = com.weiwan.support.common.utils.ReflectUtil.getObjectValue(options, field);
            Parameter optionField = field.getAnnotation(Parameter.class);
            if (optionField != null) {
                String[] oweKeys = optionField.names();
                if (oweKeys.length > 0) {
                    //取oweKeys的数据
                    res.put(oweKeys[0], fieldValue);
                }
            } else if (field.getAnnotation(DynamicParameter.class) != null) {
                res.put("DynamicParameter", fieldValue);
            }
        }
        return res;
    }


    /**
     * 递归获得类对象所有字段
     * @param aClass
     * @return
     */
    private static Field[] getSuperFields(Class<?> aClass) {
        Field[] fields = aClass.getDeclaredFields();
        if (aClass.getSuperclass() != null && !aClass.getSuperclass().equals(Object.class)) {
            fields = ArrayUtils.addAll(fields, getSuperFields(aClass.getSuperclass()));
        }
        return fields;
    }


    public void usage() {
        jCommander.usage();
    }

//        public static void main(String[] args) {
//        OptionParser objectOptionParserV2 = new OptionParser(args);
//        JCommanderTest parse = objectOptionParserV2.parse(JCommanderTest.class);
//        System.out.println(parse);
//    }
}

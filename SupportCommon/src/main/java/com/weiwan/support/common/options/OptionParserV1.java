/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weiwan.support.common.options;



import com.weiwan.support.common.utils.ReflectUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * The Parser of Launcher commandline options
 * <p>
 * Company: www.dtstack.com
 *
 * @author huyifan.zju@163.com
 */
public class OptionParserV1 {

//    private Options options = new Options();
//
//    private BasicParser parser = new BasicParser();
//
//    private Class<?> optionTemplate;
//
//    private CommandLine commandLine;

    private String[] args;
    private boolean inited;


    public OptionParserV1(String[] args) {
        this.args = args;
    }


    public <E> E parse(Class<E> eClass) throws Exception {
        E e = null;
//        try {
//            if (!inited) {
//                initOptions(eClass);
//            }
//
//            e = (E) optionTemplate.newInstance();
//            Field[] fields = e.getClass().getDeclaredFields();
//            for (Field field : fields) {
//                OptionField optionField = field.getAnnotation(OptionField.class);
//                //兼容字段名字的形式,和简写Key
//                String[] optionNames = optionField.oweKeys();
//                String fieldName = field.getName();
//                String value = null;
//                for (String optionName : optionNames) {
//                    value = commandLine.getOptionValue(optionName);
//                    if (value != null) {
//                        break;
//                    }
//
//                    if (!optionField.hasArg()) {
//                        //没参数
//                        value = String.valueOf(commandLine.hasOption(optionName));
//                    }
//                }
//
//                if (value == null) {
//                    value = commandLine.getOptionValue(fieldName);
//                }
//
//
//                //如果参数不能为空,但是命令行为空,抛出异常
//                if (optionField != null) {
//                    if (optionField.required() && StringUtils.isEmpty(value)) {
//                        throw new RuntimeException(String.format("parameters of %s is required", fieldName));
//                    }
//                }
//
//                //给反射的对象设置属性
//                field.setAccessible(true);
//                if (StringUtils.isBlank(value)) {
//                    value = optionField.defaultValue();
//                    if (value == null || value.equalsIgnoreCase("")) {
//                        //默认值为空 TODO 默认值为空,这里需要拿到实体类的默认值
//                        Object fieldValue = ReflectUtil.getFieldStrValue(e, field);
//                        if (fieldValue != null) {
//                            value = String.valueOf(fieldValue);
//                        }
//                    }
//                }
//
//
//                if (!optionField.hasArg() && value != null) {
//                    field.setBoolean(e, Boolean.valueOf(value));
//                } else {
//                    field.set(e, value);
//                }
//            }
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            throw exception;
//        }
        return e;
    }

    private <E> void initOptions(Class<E> eClass) throws Exception {
//        this.optionTemplate = eClass;
//        Field[] fields = optionTemplate.getDeclaredFields();
//        Option annotation = optionTemplate.getAnnotation(Option.class);
//        if (annotation == null) {
//            throw new RuntimeException(String.format(" option Class %s must haveing @Option annotation", optionTemplate.getName()));
//        }
//        for (Field field : fields) {
//            String fieldName = field.getName();
//            OptionField optionField = field.getAnnotation(OptionField.class);
//            if (optionField != null) {
//                String opKey = null;
//                String[] oweKeys = optionField.oweKeys();
//                for (String oweKey : oweKeys) {
//                    if (oweKey != null) {
//                        opKey = oweKey;
//                        options.addOption(oweKey, optionField.hasArg(), optionField.description());
//                    }
//                }
//                if (opKey == null) {
//                    options.addOption(fieldName, optionField.hasArg(), optionField.description());
//                }
//            }
//        }
//        this.commandLine = parser.parse(this.options, args, true);
        inited = true;
    }


    public Map<String, Object> optionToMap(Object options, Class<?> clazz) throws Exception {
        Field[] declaredFields = clazz.getDeclaredFields();
        Map<String, Object> res = new HashMap();
        for (Field field : declaredFields) {
            Object fieldValue = ReflectUtil.getObjectValue(options, field);
            OptionField optionField = field.getAnnotation(OptionField.class);
            String[] oweKeys = optionField.oweKeys();
            if (oweKeys.length > 0) {
                //取oweKeys的数据
                res.put(oweKeys[0], fieldValue);
            } else {
                //取字段名称作为Key
                res.put(field.getName(), fieldValue);
            }
        }
        return res;
    }
}

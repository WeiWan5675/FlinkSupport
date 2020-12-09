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
package com.weiwan.support.api.enums;


import com.weiwan.support.common.constant.Constans;
import java.io.Serializable;
import java.util.List;

import static java.util.Arrays.*;

/**
 * @Author: xiaozhennan
 * @Date: 2020/8/7 10:54
 * @Package: com.weiwan.support.core.pub.output.hdfs
 * @ClassName: SupportType
 * @Description:
 **/
public enum SupportType implements Serializable {

    /**
     * string type
     */
    STRING, VARCHAR, VARCHAR2, CHAR, NVARCHAR, TEXT, KEYWORD, BINARY, JSON,

    /**
     * number type
     */
    INT, INT32, MEDIUMINT, TINYINT, SMALLINT, BIGINT, LONG, INT64, SHORT, INTEGER,

    /**
     * double type
     */
    DOUBLE, FLOAT,
    BOOLEAN, DECIMAL,

    /**
     * date type
     */
    DATE, TIMESTAMP, TIME,
    YEAR, DATETIME,


    /**
     * 组合类型
     */
    ARRAYLIST,HASHMAP,LIST,
    NULL;

    public static List<SupportType> TIME_TYPE = asList(
            DATE, DATETIME, TIME, TIMESTAMP, YEAR
    );

    public static List<SupportType> NUMBER_TYPE = asList(
            INT, INTEGER, MEDIUMINT, TINYINT, SMALLINT, BIGINT, LONG, SHORT, DOUBLE, FLOAT, DECIMAL
    );

    public static SupportType fromString(String type) {
        if (type == null) {
            throw new RuntimeException("null SupportType!");
        }

        if (type.contains(Constans.LEFT_PARENTHESIS_SYMBOL)) {
            type = type.substring(0, type.indexOf(Constans.LEFT_PARENTHESIS_SYMBOL));
        }

        return valueOf(type.toUpperCase());
    }

    public static SupportType getType(String type) {
        if (type.contains(Constans.LEFT_PARENTHESIS_SYMBOL)) {
            type = type.substring(0, type.indexOf(Constans.LEFT_PARENTHESIS_SYMBOL));
        }
        if (type.contains(Constans.POINT_SYMBOL)) {
            type = type.substring(type.lastIndexOf(Constans.POINT_SYMBOL) + 1, type.length());
        }

        if (type.toLowerCase().contains(TIMESTAMP.name().toLowerCase())) {
            return TIMESTAMP;
        }

        for (SupportType value : values()) {
            if (type.equalsIgnoreCase(value.name())) {
                return value;
            }
        }

        return STRING;
    }

    public static boolean isTimeType(String type) {
        return TIME_TYPE.contains(getType(type));
    }

    public static boolean isNumberType(String type) {
        return NUMBER_TYPE.contains(getType(type));
    }
}

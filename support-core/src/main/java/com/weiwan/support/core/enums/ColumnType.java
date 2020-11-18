package com.weiwan.support.core.enums;


import com.weiwan.support.common.constant.Constans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: xiaozhennan
 * @Date: 2020/8/7 10:54
 * @Package: com.weiwan.support.core.pub.output.hdfs
 * @ClassName: ColumnType
 * @Description:
 **/
public enum ColumnType implements Serializable {

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

    public static List<ColumnType> TIME_TYPE = Arrays.asList(
            DATE, DATETIME, TIME, TIMESTAMP, YEAR
    );

    public static List<ColumnType> NUMBER_TYPE = Arrays.asList(
            INT, INTEGER, MEDIUMINT, TINYINT, SMALLINT, BIGINT, LONG, SHORT, DOUBLE, FLOAT, DECIMAL
    );

    public static ColumnType fromString(String type) {
        if (type == null) {
            throw new RuntimeException("null ColumnType!");
        }

        if (type.contains(Constans.LEFT_PARENTHESIS_SYMBOL)) {
            type = type.substring(0, type.indexOf(Constans.LEFT_PARENTHESIS_SYMBOL));
        }

        return valueOf(type.toUpperCase());
    }

    public static ColumnType getType(String type) {
        if (type.contains(Constans.LEFT_PARENTHESIS_SYMBOL)) {
            type = type.substring(0, type.indexOf(Constans.LEFT_PARENTHESIS_SYMBOL));
        }
        if (type.contains(Constans.POINT_SYMBOL)) {
            type = type.substring(type.lastIndexOf(Constans.POINT_SYMBOL) + 1, type.length());
        }

        if (type.toLowerCase().contains(ColumnType.TIMESTAMP.name().toLowerCase())) {
            return TIMESTAMP;
        }

        for (ColumnType value : ColumnType.values()) {
            if (type.equalsIgnoreCase(value.name())) {
                return value;
            }
        }

        return ColumnType.STRING;
    }

    public static boolean isTimeType(String type) {
        return TIME_TYPE.contains(getType(type));
    }

    public static boolean isNumberType(String type) {
        return NUMBER_TYPE.contains(getType(type));
    }
}

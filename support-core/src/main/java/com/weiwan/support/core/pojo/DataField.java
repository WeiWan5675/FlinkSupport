package com.weiwan.support.core.pojo;

import com.weiwan.support.core.enums.ColumnType;
import org.apache.flink.annotation.PublicEvolving;

import java.io.Serializable;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/27 17:22
 * @Package: com.weiwan.support.core.pub.pojo
 * @ClassName: DataField
 * @Description:
 **/
@PublicEvolving
public class DataField<T> implements Serializable {

    private String fieldKey;
    private ColumnType fieldType;
    private T value;


    public DataField(String fieldKey, ColumnType fieldType, T value) {
        this.fieldKey = fieldKey;
        this.fieldType = fieldType;
        this.value = value;
    }

    public DataField() {
    }


    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public ColumnType getFieldType() {
        return fieldType;
    }

    public void setFieldType(ColumnType fieldType) {
        this.fieldType = fieldType;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DataField{" +
                "fieldKey='" + fieldKey + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", value=" + value +
                '}';
    }


}

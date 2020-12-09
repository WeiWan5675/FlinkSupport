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
package com.weiwan.support.api.pojo;

import org.apache.flink.annotation.PublicEvolving;
import com.weiwan.support.api.enums.*;
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
    private SupportType fieldType;
    private T value;


    public DataField(String fieldKey, SupportType fieldType, T value) {
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

    public SupportType getFieldType() {
        return fieldType;
    }

    public void setFieldType(SupportType fieldType) {
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

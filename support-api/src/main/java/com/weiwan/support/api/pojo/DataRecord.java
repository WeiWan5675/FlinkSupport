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

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/14 17:52
 * @Package: com.weiwan.support.reader.mysql.input
 * @ClassName: DataRecord
 * @Description:
 **/
@PublicEvolving
public class DataRecord<T> implements Serializable {
    private String tableName;
    private String primaryKey;
    private String timestamp;
    private String schemaName;
    private String dataPath;
    private String dataType;
    private Map<String,String> dataMeta;
    private T data;

    public DataRecord(T data) {
        this.data = data;
    }

    public DataRecord() {

    }

    public Map<String, String> getDataMeta() {
        return dataMeta;
    }

    public void setDataMeta(Map<String, String> dataMeta) {
        this.dataMeta = dataMeta;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Override
    public String toString() {
        return "DataRecord{" +
                "tableName='" + tableName + '\'' +
                ", primaryKey='" + primaryKey + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", schemaName='" + schemaName + '\'' +
                ", dataPath='" + dataPath + '\'' +
                ", dataType='" + dataType + '\'' +
                ", data=" + data +
                '}';
    }


}

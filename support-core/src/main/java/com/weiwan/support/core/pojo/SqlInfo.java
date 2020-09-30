package com.weiwan.support.core.pojo;

import java.io.Serializable;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/23 14:19
 * @Package: 
 * @ClassName: SqlInfo
 * @Description:
 **/
public class SqlInfo implements Serializable {
    private String tableName;
    //增量字段
    private String incrField;
    //分区字段
    private String splitField;
    //要查询的列
    private String[] columns;
    //过滤条件,可以连续写,也可以分开写 循环拼接
    private String[] filters;
    //分区的个数
    private Integer splitNum;
    //当前分区的Index
    private Integer thisSplitNum;

    private String dbSchema;

    private String customSql;

    public String getCustomSql() {
        return customSql;
    }

    public void setCustomSql(String customSql) {
        this.customSql = customSql;
    }

    public SqlInfo(Builder builder) {
        setTableName(builder.tableName);
        setIncrField(builder.incrField);
        setSplitField(builder.splitField);
        setColumns(builder.columns);
        setFilters(builder.filters);
        setSplitNum(builder.splitNum);
        setThisSplitNum(builder.thisSplitNum);
        setDbSchema(builder.dbSchema);
        setCustomSql(builder.customSql);
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIncrField() {
        return incrField;
    }

    public void setIncrField(String incrField) {
        this.incrField = incrField;
    }

    public String getSplitField() {
        return splitField;
    }

    public void setSplitField(String splitField) {
        this.splitField = splitField;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public String[] getFilters() {
        return filters;
    }

    public void setFilters(String[] filters) {
        this.filters = filters;
    }

    public Integer getSplitNum() {
        return splitNum;
    }

    public void setSplitNum(Integer splitNum) {
        this.splitNum = splitNum;
    }

    public Integer getThisSplitNum() {
        return thisSplitNum;
    }

    public void setThisSplitNum(Integer thisSplitNum) {
        this.thisSplitNum = thisSplitNum;
    }

    public String getDbSchema() {
        return dbSchema;
    }

    public void setDbSchema(String dbSchema) {
        this.dbSchema = dbSchema;
    }

    public static final class Builder {
        private String customSql;
        private String dbSchema;
        private String tableName;
        private String incrField;
        private String splitField;
        private String[] columns;
        private String[] filters;
        private Integer splitNum;
        private Integer thisSplitNum;

        private Builder() {
        }

        public Builder tableName(String val) {
            tableName = val;
            return this;
        }

        public Builder incrField(String val) {
            incrField = val;
            return this;
        }

        public Builder customSql(String val) {
            customSql = val;
            return this;
        }

        public Builder splitField(String val) {
            splitField = val;
            return this;
        }

        public Builder columns(String[] val) {
            columns = val;
            return this;
        }

        public Builder filters(String[] val) {
            filters = val;
            return this;
        }

        public Builder splitNum(Integer val) {
            splitNum = val;
            return this;
        }

        public Builder thisSplitNum(Integer val) {
            thisSplitNum = val;
            return this;
        }

        public Builder dbSchema(String val) {
            dbSchema = val;
            return this;
        }

        public SqlInfo build() {
            return new SqlInfo(this);
        }
    }
}


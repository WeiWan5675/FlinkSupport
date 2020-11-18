package com.weiwan.support.plugins.jdbc;

import com.weiwan.support.core.pojo.SqlInfo;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/23 11:27
 * @Package: com.weiwan.support.core.pub.api
 * @ClassName: SqlGenerator
 * @Description: mysql的sql生成器, 同时也适用大多数可以使用jdbc的数据库
 **/
public class SqlGeneratorForMysql implements SqlGenerator {

    private static final String SPACE = " ";
    private static final String sql_basic = "select ${columns} from ${dbSchema}.${tableName} where 1 = 1 ${filterSql}";
    private static final String filterSql = " and ";
    private static final String splitSql = " and ${splitField} mod ${splitNum} = ${thisNum}";
    private static final String incrSql = " and ${incrField} BETWEEN ? AND ?";
    String maxSql = "select max(${incrField}) as " + MAX_VALUE + " from ${dbSchema}.${tableName} where 1 = 1";
    String minSql = "select min(${incrField}) as " + MIN_VALUE + " from ${dbSchema}.${tableName} where 1 = 1";
    private String sqlGenerated = "select 1 ";
    private SqlInfo sqlInfo;


    public SqlGeneratorForMysql() {

    }


    public String generatorSql(SqlInfo info) {
        this.sqlInfo = info;
        StringBuffer filterSb = new StringBuffer(filterSql);
        String[] filters = sqlInfo.getFilters();

        for (int i = 0; i < filters.length; i++) {
            if (i < filters.length - 1) {
                filterSb.append(filters[i])
                        .append(" and ");
            } else {
                filterSb.append(filters[i] + "");
            }
        }

        StringBuffer columnSb = new StringBuffer("");
        String[] columns = sqlInfo.getColumns();
            for (int i = 0; i < columns.length; i++) {
                if (i < columns.length - 1) {
                    columnSb.append(columns[i])
                            .append(",");
                } else {
                    columnSb.append(columns[i]);
                }
            }

        String sql_tmp1 = sql_basic.replace("${columns}", columnSb.toString());
        String sql_tmp2 = sql_tmp1.replace("${filterSql}", filterSb.toString());
        String sql_tmp3 = sql_tmp2.replace("${tableName}", sqlInfo.getTableName()).replace("${dbSchema}", sqlInfo.getDbSchema());
        String sql_tmp4 = sql_tmp3;
        if (StringUtils.isNotEmpty(sqlInfo.getIncrField())) {
            //是增量任务,拼接增量SQL
            sql_tmp4 = sql_tmp3 + incrSql.replace("${incrField}", sqlInfo.getIncrField());
        }

        String sql_tmp5 = sql_tmp4;
        if (StringUtils.isNotEmpty(sqlInfo.getSplitField()) && sqlInfo.getSplitNum() != null && sqlInfo.getThisSplitNum() != null) {
            if (sqlInfo.getSplitNum() > 1) {
                sql_tmp5 = sql_tmp4 + splitSql
                        .replace("${splitField}", sqlInfo.getSplitField())
                        .replace("${splitNum}", String.valueOf(sqlInfo.getSplitNum()))
                        .replace("${thisNum}", String.valueOf(sqlInfo.getThisSplitNum()));
            }
        }

        this.sqlGenerated = sql_tmp5;
        return sqlGenerated;
    }


    public static void main(String[] args) {

        SqlInfo sqlInfo = SqlInfo.newBuilder()
                .thisSplitNum(0)
                .splitNum(3)
                .splitField("id")
                .incrField("update_time_dw")
                .columns(new String[]{"id", "broker_id", "dev_id"})
                .filters(new String[]{"id > 10000", "broker_id is not null and dev_id > 100"})
                .tableName("easylife_order")
                .dbSchema("easylife")
                .build();

        SqlGenerator sqlGenerator = new SqlGeneratorForMysql();
        sqlGenerator.generatorSql(sqlInfo);

        System.out.println(sqlGenerator.getSql());

    }


    public String getSql() {
        return this.sqlGenerated;
    }

    /**
     * 获取incr字段最大值SQL
     * max字段命名为:{@link SqlGenerator#MAX_VALUE}
     *
     * @return
     */
    @Override
    public String generatorIncrMaxSql() {
        return maxSql.replace("${incrField}", sqlInfo.getIncrField())
                .replace("${dbSchema}", sqlInfo.getDbSchema())
                .replace("${tableName}", sqlInfo.getTableName());
    }

    /**
     * 获取incr字段最小值SQL
     * min字段名称为:{@link SqlGenerator#MIN_VALUE}
     *
     * @return
     */
    @Override
    public String generatorIncrMinSql() {
        return minSql.replace("${incrField}", sqlInfo.getIncrField())
                .replace("${dbSchema}", sqlInfo.getDbSchema())
                .replace("${tableName}", sqlInfo.getTableName());
    }
}

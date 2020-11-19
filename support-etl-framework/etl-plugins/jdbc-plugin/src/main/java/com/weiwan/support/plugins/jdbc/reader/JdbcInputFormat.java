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
package com.weiwan.support.plugins.jdbc.reader;

import com.weiwan.support.common.utils.DateUtils;
import com.weiwan.support.common.utils.StringUtil;
import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.enums.ColumnType;
import com.weiwan.support.core.pojo.DataField;
import com.weiwan.support.core.pojo.DataRecord;
import com.weiwan.support.core.pojo.DataRow;
import com.weiwan.support.core.pojo.SqlInfo;
import com.weiwan.support.etl.framework.api.reader.BaseInputFormat;
import com.weiwan.support.etl.framework.streaming.JobFormatState;
import com.weiwan.support.plugins.jdbc.SqlGenerator;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/22 15:42
 * @Package: com.weiwan.support.core.pub.api
 * @ClassName: JdbcInputFormat
 * @Description:
 **/
public abstract class JdbcInputFormat extends BaseInputFormat<DataRecord<DataRow>, JdbcInputSpliter> {

    Logger logger = LoggerFactory.getLogger(JdbcInputFormat.class);

    private static final String KEY_READER_TYPE = "reader.type";
    private static final String KEY_READER_NAME = "reader.name";
    private static final String KEY_READER_CLASS = "reader.class";
    private static final String KEY_READER_JDBC_URL = "reader.jdbc.url";
    private static final String KEY_READER_JDBC_DRIVE_CLASS = "reader.jdbc.drive";
    private static final String KEY_READER_JDBC_USERNAME = "reader.jdbc.username";
    private static final String KEY_READER_JDBC_PASSWROD = "reader.jdbc.password";
    private static final String KEY_READER_JDBC_SCHEMA = "reader.jdbc.schema";
    private static final String KEY_READER_TABLENAME = "reader.tableName";
    private static final String KEY_READER_BATCHSIZE = "reader.batchSize";
    private static final String KEY_READER_QUERY_TIMEOUT = "reader.queryTimeout";
    private static final String KEY_READER_SPLIT_FIELD = "reader.splitField";
    private static final String KEY_READER_SQL_FILTER = "reader.sql.filter";
    private static final String KEY_READER_SQL_COLUMNS = "reader.sql.columns";
    private static final String KEY_READER_SQL_CUSTOMSQL = "reader.sql.customSql";
    private static final String KEY_READER_INCR_INCRFIELD = "reader.increment.incrField";
    private static final String KEY_READER_INCR_LASTOFFSET = "reader.increment.lastOffset";
    private static final String KEY_READER_INCR_ENABLE_POLLING = "reader.increment.enablePolling";
    private static final String KEY_READER_INCR_POLL_INTERVAL = "reader.increment.pollingInterval";

    protected String driveClassName;
    protected String jdbcUrl;
    protected String username;
    protected String password;

    protected String dbSchema;
    protected String tableName;
    protected int batchSize;
    protected int queryTimeout;
    protected boolean isPolling;
    protected Long pollingInterval;
    protected String[] columns;
    protected String[] filters;
    protected String incrField;
    protected String splitField;
    protected String customSql;

    protected Connection dbConn;
    protected PreparedStatement statement;
    protected ResultSet resultSet;
    protected ResultSetMetaData tableMetaData;
    protected int columnCount;
    protected String lastOffset;
    protected SqlGenerator sqlGenerator;

    public JdbcInputFormat(SupportAppContext context) {
        super(context);
    }

    @Override
    public void configure(Configuration parameters) {
        this.username = readerConfig.getStringVal(KEY_READER_JDBC_USERNAME);
        this.password = readerConfig.getStringVal(KEY_READER_JDBC_PASSWROD);
        this.driveClassName = readerConfig.getStringVal(KEY_READER_JDBC_DRIVE_CLASS);
        this.dbSchema = readerConfig.getStringVal(KEY_READER_JDBC_SCHEMA);
        this.tableName = readerConfig.getStringVal(KEY_READER_TABLENAME);
        this.batchSize = readerConfig.getIntVal(KEY_READER_BATCHSIZE, 1000);
        this.queryTimeout = readerConfig.getIntVal(KEY_READER_QUERY_TIMEOUT, 60);
        this.jdbcUrl = readerConfig.getStringVal(KEY_READER_JDBC_URL);
        this.pollingInterval = readerConfig.getLongVal(KEY_READER_INCR_POLL_INTERVAL, 10000L);
        this.isPolling = readerConfig.getBooleanVal(KEY_READER_INCR_ENABLE_POLLING, false);
        this.customSql = readerConfig.getStringVal(KEY_READER_SQL_CUSTOMSQL);
        this.columns = readerConfig.getStringVal(KEY_READER_SQL_COLUMNS, "*").split(",");
        this.filters = readerConfig.getStringVal(KEY_READER_SQL_FILTER, "1=1").split(",");
        this.incrField = readerConfig.getStringVal(KEY_READER_INCR_INCRFIELD);
        this.splitField = readerConfig.getStringVal(KEY_READER_SPLIT_FIELD);
        this.lastOffset = readerConfig.getStringVal(KEY_READER_INCR_LASTOFFSET);
        logger.info("set JDBCReader Related Parameters");
    }


    public abstract SqlGenerator getSqlGenerator(JdbcInputSpliter split);


    /**
     * 打开InputFormat,根据split读取数据
     *
     * @param split 当前处理的分区InputSplit
     */
    @Override
    public void openInput(JdbcInputSpliter split) {
        try {
            Class.forName(driveClassName);
            this.dbConn = DriverManager.getConnection(jdbcUrl, username, password);

            sqlGenerator = this.getSqlGenerator(split);
            //构建sql
            SqlInfo sqlInfo = SqlInfo.newBuilder()
                    .columns(columns)
                    .filters(filters)
                    .dbSchema(dbSchema)
                    .tableName(tableName)
                    .incrField(incrField)
                    .customSql(customSql)
                    .splitField(splitField)
                    .splitNum(split.getTotalNumberOfSplits())
                    .thisSplitNum(split.getSplitNumber()).build();
            sqlGenerator.generatorSql(sqlInfo);
            String sql = sqlGenerator.getSql();
            logger.info("generated SQL Content: {}", sql);
            String maxSql = sqlGenerator.generatorIncrMaxSql();
            logger.info("generated MaxSQL Content: {}", maxSql);
            String minSql = sqlGenerator.generatorIncrMinSql();
            logger.info("generated MinSQL Content: {}", minSql);
            statement = dbConn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            //获得最大最小 max min
            /**
             * 获取最大最小得offset 如果没有传lastOffset 就使用数据库最大 和最小
             * 如果是轮询,就要把每次查询得最小值保存起来 然后
             */
            Statement statement = dbConn.createStatement();
            System.out.println(maxSql);
            ResultSet rsMax = statement.executeQuery(maxSql);
            Object maxVar = getMaxOrMinValue(rsMax, SqlGenerator.MAX_VALUE);
            System.out.println(minSql);
            ResultSet rsMin = statement.executeQuery(minSql);
            Object minVar = getMaxOrMinValue(rsMin, SqlGenerator.MIN_VALUE);

            this.statement.setFetchSize(batchSize);
            this.statement.setQueryTimeout(queryTimeout);
            //var object 支持三种类型  1. Long 2. Date 3.Timestamp
            logger.info("maxValue:{}", maxVar);
            logger.info("minValue:{}", minVar);
            if (isPolling) {
                this.statement.setObject(1, minVar);
                this.statement.setObject(2, maxVar);
            } else {
                //获取offset Sql 直接访问数据库获得 根据IncrField 字段
                this.statement.setObject(1, minVar);
                this.statement.setObject(2, maxVar);

            }

            if (StringUtils.isNotEmpty(lastOffset)) {
                this.statement.setObject(1, lastOffset);
                logger.info("use the lastOffset [{}] provided by user", lastOffset);
            }
            this.resultSet = this.statement.executeQuery();
            tableMetaData = this.resultSet.getMetaData();
            columnCount = tableMetaData.getColumnCount();
            //初始化调用下next,不然result会报错
            boolean next = this.resultSet.next();
            if (!next) {
                isComplete(true);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private Object getMaxOrMinValue(ResultSet resultSet, String flag) throws SQLException {
        Object var = null;
        List<Map> maps = convertResultToMap(resultSet);
        Object obj = null;
        if (maps.size() == 1) {
            Map map = maps.get(0);
            obj = map.get(flag);
        }
        if (obj instanceof Number) {
            var = Long.valueOf(String.valueOf(obj));
        }
        if (obj instanceof Date) {
            var = (Date) obj;
        }
        if (obj instanceof Timestamp) {
            var = (Timestamp) obj;
        }
        return var;
    }

    /**
     * 根据minNumSplits决定如何划分分区
     *
     * @param minNumSplits 最小分区数
     * @return 分区数组
     */
    @Override
    public JdbcInputSpliter[] getInputSpliter(int minNumSplits) {
        JdbcInputSpliter[] spliters = new JdbcInputSpliter[minNumSplits];
        for (int i = 0; i < minNumSplits; i++) {
            JdbcInputSpliter jdbcInputSpliter = new JdbcInputSpliter(i, minNumSplits);
            String stringVal = readerConfig.getStringVal(KEY_READER_INCR_LASTOFFSET); //从启动参数中获取offset
//            jdbcInputSpliter.setUserLastOffser(Long.valueOf(stringVal));
            spliters[i] = jdbcInputSpliter;
        }
        return spliters;
    }


    /**
     * 返回一条记录
     * 当数据处理结束后,需要手动调用{@link BaseInputFormat#isComplete} }
     *
     * @param row
     * @return 数据
     */
    @Override
    public DataRecord<DataRow> nextRecordInternal(DataRecord<DataRow> row) {
        DataRecord<DataRow> rowDataRecord = new DataRecord();
        rowDataRecord.setTableName(tableName);
        rowDataRecord.setSchemaName(dbSchema);
        rowDataRecord.setTimestamp(DateUtils.getDateStr(new java.util.Date()));
        try {
            DataRow currentRow = new DataRow(columnCount);
            rowDataRecord.setData(currentRow);
            if (!isComplete()) {
                for (int i = 0; i < columnCount; i++) {
                    DataField dataField = new DataField();
                    Object object = resultSet.getObject(i + 1);
                    String columnName = tableMetaData.getColumnName(i + 1);
                    dataField.setFieldKey(columnName);
                    if (object == null) {
                        dataField.setValue(null);
                        dataField.setFieldType(ColumnType.NULL);
                    } else {
                        if (object instanceof String) {
                            String s = String.valueOf(object);
                            if (StringUtil.isNotEmpty(s)) {
                                object = s.replaceAll("[\\t\\n\\r]", "").replace("\\u0001", "");
                            }
                        }
                        dataField.setValue(object);
                        ColumnType columnType = ColumnType.getType(object.getClass().getTypeName());
                        dataField.setFieldType(columnType);
                    }
                    currentRow.setField(i, dataField);
                }
            }

            boolean next = resultSet.next();
            //如果没有数据了,就isComplete()
            if (!next) {
                isComplete(true);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        row = rowDataRecord;
        return rowDataRecord;
    }


    /**
     * 关闭Input,释放资源
     */
    @Override
    public void closeInput() {

    }

    /**
     * 快照前允许特殊处理
     *
     * @param formatState
     */
    @Override
    public void snapshot(JobFormatState formatState) {

    }


    public List<Map> convertResultToMap(ResultSet resultSet) throws SQLException {
        ResultSetMetaData md = resultSet.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取行的数量
        List<Map> list = new ArrayList<>();// 定义一个list，用来存放数据
        while (resultSet.next()) {
            Map rowData = new HashMap();//声明Map
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), resultSet.getObject(i));//获取键名及值
            }
            list.add(rowData);//将数据添加到list中
        }
        resultSet.close();
        return list;
    }

    /**
     *
     *
     *
     * 1. 支持的模式
     *      1.1 全量拉取 最大最小值 直接抽取
     *      1.2 指定offset 增量拉取 从指定offset开始拉取
     *      1.3 轮询拉取 根据起始offset 按指定间隔 将最新的数据抽取出去
     *          两种模式: 1. 按数值列新增抽取  2. 按update_time列 抽取更新的数据
     *
     * 2. {@link JdbcInputFormat} 要做的事情
     *      2.1 创建数据库连接
     *      2.2 执行SQL
     *      2.3 如果是轮询模式,需要处理动态sql问题
     *      2.4 多表暂时不考虑,单独提供InputFormat
     *3. 继承JdbcInputFormat的子类做的事情
     *
     *      3.1 打开数据源
     *      3.2 获取下一条数据
     *      3.3 拼接SQL,抽象SQL返回分段
     *
     */


}

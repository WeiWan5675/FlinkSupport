package com.weiwan.support.plugins.reader;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.plugins.jdbc.reader.JdbcInputFormat;
import com.weiwan.support.plugins.jdbc.reader.JdbcInputSpliter;
import com.weiwan.support.plugins.jdbc.SqlGenerator;
import com.weiwan.support.plugins.jdbc.SqlGeneratorForMysql;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/14 17:51
 * @Package: com.weiwan.support.reader.mysql.input
 * @ClassName: MysqlInputFormat
 * @Description:
 **/
public class MysqlInputFormat extends JdbcInputFormat {

    public MysqlInputFormat(SupportAppContext context) {
        super(context);
    }

    @Override
    public SqlGenerator getSqlGenerator(JdbcInputSpliter split) {
        //打开数据源
        return new SqlGeneratorForMysql();
    }

}

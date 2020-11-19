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

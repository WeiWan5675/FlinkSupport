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
package com.weiwan.support.plugins.jdbc;


import com.weiwan.support.core.pojo.SqlInfo;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/23 18:19
 * @Package: com.weiwan.support.core.pub.api
 * @ClassName: SqlGenerator
 * @Description:
 **/
public interface SqlGenerator {

    public static final String MAX_VALUE = "incrMaxValue";

    public static final String MIN_VALUE = "incrMinValue";

    /**
     * 1. 根据传递得SQLINFO 生成SQL
     * 2. 如果{@link org.apache.flink.core.io.GenericInputSplit#totalNumberOfPartitions} > 1
     * 还需要根据SqlInfo的SplitField生成对应的mod子句 例: ${splitField} mod ${splitNum} = ${thisNum}
     * 3. 如果IncrField不为空,需要生成增量子句,并将maxOffset MinOffser 使用"?" 例: ${incrField} BETWEEN ? AND ?"
     *
     * @return
     */
    public String generatorSql(SqlInfo sqlInfo);

    /**
     * 获取生成后的sql,需要能够多次获取同一个Sql
     *
     * @return SQL
     */
    public String getSql();


    /**
     * 获取incr字段最大值SQL
     *  max字段命名为:{@link SqlGenerator#MAX_VALUE}
     * @param sqlInfo
     * @return
     */
    String generatorIncrMaxSql();


    /**
     * 获取incr字段最小值SQL
     * min字段名称为:{@link SqlGenerator#MIN_VALUE}
     * @return
     */
    String generatorIncrMinSql();
}

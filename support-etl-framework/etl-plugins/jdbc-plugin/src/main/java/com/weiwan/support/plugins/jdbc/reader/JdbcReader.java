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

import com.weiwan.support.api.config.SupportContext;
import com.weiwan.support.api.pojo.DataRecord;
import com.weiwan.support.api.pojo.DataRow;
import com.weiwan.support.etl.framework.api.reader.BaseInputFormat;
import com.weiwan.support.etl.framework.api.reader.BaseReader;
import org.apache.flink.core.io.InputSplit;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/18 16:29
 * @Package: com.weiwan.support.etl.plugins.JdbcReader
 * @ClassName: JdbcReader
 * @Description:
 **/
public class JdbcReader extends BaseReader<DataRecord<DataRow>> {
    @Override
    public void require(SupportContext context) {

    }

    @Override
    public <IS extends InputSplit> BaseInputFormat<DataRecord<DataRow>, IS> getInputFormat(SupportContext context) {
        JdbcInputFormat jdbcInputFormat = new JdbcInputFormat();
        jdbcInputFormat.setContext(context);
        return (BaseInputFormat<DataRecord<DataRow>, IS>) jdbcInputFormat;
    }
}

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


import com.weiwan.support.etl.framework.api.reader.BaseInputSpliter;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/22 15:43
 * @Package: com.weiwan.support.core.pub.api
 * @ClassName: JdbcInputSpliter
 * @Description:
 **/
public class JdbcInputSpliter extends BaseInputSpliter {

    private long minOffset;

    private long maxOffset;

    private long userLastOffser;

    private String[] tables;

    public JdbcInputSpliter(int partitionNumber, int totalNumberOfPartitions) {
        super(partitionNumber, totalNumberOfPartitions);
    }


    public long getMinOffset() {
        return minOffset;
    }

    public void setMinOffset(long minOffset) {
        this.minOffset = minOffset;
    }

    public long getMaxOffset() {
        return maxOffset;
    }

    public void setMaxOffset(long maxOffset) {
        this.maxOffset = maxOffset;
    }

    public long getUserLastOffser() {
        return userLastOffser;
    }

    public void setUserLastOffser(long userLastOffser) {
        this.userLastOffser = userLastOffser;
    }

    public String[] getTables() {
        return tables;
    }

    public void setTables(String[] tables) {
        this.tables = tables;
    }
}

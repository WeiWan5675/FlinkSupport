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
package com.weiwan.support.etl.framework.api.reader;

import org.apache.flink.core.io.GenericInputSplit;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/14 17:53
 * @Package: com.weiwan.support.etl.framework.api.reader
 * @ClassName: BaseInputSpliter
 * @Description:
 **/
public abstract class BaseInputSpliter extends GenericInputSplit {

    //处理模式,批处理,流处理
    private String mod;

    //父类方法,这些必须要传递
    public BaseInputSpliter(int partitionNumber, int totalNumberOfPartitions) {
        super(partitionNumber, totalNumberOfPartitions);
    }


    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }
}

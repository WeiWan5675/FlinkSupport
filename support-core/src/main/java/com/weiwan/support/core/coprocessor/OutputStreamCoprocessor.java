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
package com.weiwan.support.core.coprocessor;

import com.weiwan.support.core.SupportContext;
import com.weiwan.support.core.api.SupportDataFlow;
import org.apache.flink.streaming.api.datastream.DataStreamSink;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/12 9:53
 * @Package: com.weiwan.support.core.coprocessor.OutputStreamCoprocessor
 * @ClassName: OutputStreamCoprocessor
 * @Description:
 **/
public class OutputStreamCoprocessor extends SupportCoprocessor {
    public OutputStreamCoprocessor(SupportContext context) {
        super(context);
    }

    @Override
    public <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) throws Exception {
        DataStreamSink output = dataFlow.output((S2) obj);
        return nextProcess(env, dataFlow, output);
    }
}

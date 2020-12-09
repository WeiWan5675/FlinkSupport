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

import com.weiwan.support.api.SupportDataFlow;
import com.weiwan.support.api.config.SupportContext;
import com.weiwan.support.core.annotation.SupportSource;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/12 9:51
 * @Package: com.weiwan.support.core.coprocessor.OpenStreamCoprocessor
 * @ClassName: OpenStreamCoprocessor
 * @Description:
 **/
public class OpenStreamCoprocessor extends SupportCoprocessor {

    private static final Logger logger = LoggerFactory.getLogger(OpenStreamCoprocessor.class);

    public OpenStreamCoprocessor(SupportContext context) {
        super(context);
    }


    @Override
    public <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) throws Exception {
        logger.info("open stream coprocessor process");
        S1 open = dataFlow.open(env, getContext());
        SupportSource annotation = dataFlow.getClass().getAnnotation(SupportSource.class);
        if (annotation != null && obj != null && obj instanceof DataStream) {
            return nextProcess(env, dataFlow, (S1) obj);
        }
        return nextProcess(env, dataFlow, open);
    }
}

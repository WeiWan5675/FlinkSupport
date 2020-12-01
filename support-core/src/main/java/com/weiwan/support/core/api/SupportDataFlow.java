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
package com.weiwan.support.core.api;

import com.weiwan.support.core.SupportContext;
import org.apache.flink.streaming.api.datastream.DataStreamSink;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/11 15:05
 * @Package: com.weiwan.support.core.api.SupportDataFlow
 * @ClassName: SupportDataFlow
 * @Description:
 **/
public interface SupportDataFlow<E, S1, S2> {

    S1 open(E env, SupportContext context);

    S2 process(S1 stream);

    DataStreamSink output(S2 stream);

}

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

import com.weiwan.support.core.SupportContextHolder;
import com.weiwan.support.api.SupportDataFlow;
import com.weiwan.support.api.config.SupportContext;
/**
 * @Author: xiaozhennan
 * @Date: 2020/12/2 11:25
 * @Package: com.weiwan.support.core.coprocessor.EtlStreamCoprocessorChain
 * @ClassName: EtlStreamCoprocessorChain
 * @Description:
 **/
public class EtlStreamCoprocessorChain<E, S1, S2> implements CoprocessorChain<E, S1, S2> {
    private final Object obj;
    private final E env;
    private final SupportDataFlow<E, S1, S2> dataFlow;
    private final SupportContext context;
    private SupportCoprocessor head;

    public EtlStreamCoprocessorChain(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) {
        this.env = env;
        this.dataFlow = dataFlow;
        this.obj = obj;
        this.context = SupportContextHolder.getContext();
        initChain();
    }

    private void initChain() {
        head = new FirstPreCoprocessor(context);
        SupportCoprocessor next = head;
        next = next.nextCoprocessor(new EtlCoprocessor(context))
                .nextCoprocessor(new LastPreCoprocessor(context));
    }


    @Override
    public Object coProcessing() throws Exception {
        if (head != null) {
            return head.process(env, dataFlow, obj);
        } else {
            throw new RuntimeException("The coprocessor has not been initialized");
        }
    }
}

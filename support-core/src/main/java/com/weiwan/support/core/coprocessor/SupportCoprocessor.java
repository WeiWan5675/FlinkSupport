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
import com.weiwan.support.core.api.Support;
import com.weiwan.support.core.api.SupportDataFlow;
import com.weiwan.support.core.config.JobConfig;
import com.weiwan.support.core.start.RunOptions;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/10 16:50
 * @Package: com.weiwan.support.core.coprocessor.SupportCoprocessor
 * @ClassName: SupportCoprocessor
 * @Description:
 **/
public abstract class SupportCoprocessor implements Support {

    private SupportContext context;
    private JobConfig jobConfig;

    private SupportCoprocessor nextCoprocessor;

    public SupportCoprocessor(SupportContext context) {
        this.context = context;
    }

    @Override
    public SupportContext getContext() {
        return context;
    }

    public void setContext(SupportContext context) {
        this.context = context;
    }

    public abstract <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) throws Exception;


    public final SupportCoprocessor nextCoprocessor(SupportCoprocessor next) {
        this.nextCoprocessor = next;
        return next;
    }

    protected final <E, S1, S2> Object nextProcess(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) throws Exception {
        if (this.nextCoprocessor != null) {
            return this.nextCoprocessor.process(env, dataFlow, obj);
        }
        return obj;
    }
}

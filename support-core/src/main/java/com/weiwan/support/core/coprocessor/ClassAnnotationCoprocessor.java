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

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.api.SupportDataFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/12 22:25
 * @Package: com.weiwan.support.core
 * @ClassName: ClassAnnotationCoprocessor
 * @Description:
 **/
public class ClassAnnotationCoprocessor extends SupportCoprocessor {
    public ClassAnnotationCoprocessor(SupportAppContext context) {
        super(context);
    }

    private static final Logger logger = LoggerFactory.getLogger(ClassAnnotationCoprocessor.class);

    @Override
    public <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) throws Exception {
        Class<? extends SupportDataFlow> userClass = dataFlow.getClass();

        Annotation[] annotations = dataFlow.getClass().getAnnotations();

        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }

        return nextProcess(env,dataFlow,obj);
    }
}

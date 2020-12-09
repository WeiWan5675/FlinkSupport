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
package com.weiwan.support.core.annotation;


import com.weiwan.support.api.etl.Writer;

import java.lang.annotation.*;

/**
 * @Author: xiaozhennan
 * @Date: 2020/12/2 9:01
 * @Package: com.weiwan.support.core.annotation.SupportSink
 * @ClassName: SupportSink
 * @Description:
 **/
@Inherited
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SupportSink {

    Class<? extends Writer> type();

    int parallelism() default 1;

    String name() default "SupportSource";

    String[] vars() default {};

}

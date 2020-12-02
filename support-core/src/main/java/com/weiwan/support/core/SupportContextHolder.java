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
package com.weiwan.support.core;

/**
 * @Author: xiaozhennan
 * @Date: 2020/12/1 13:57
 * @Package: com.weiwan.support.core.SupportContextHolder
 * @ClassName: SupportContextHandler
 * @Description:
 **/
public class SupportContextHolder {

    private final ThreadLocal<SupportContext> threadLocal = new ThreadLocal<SupportContext>();

    private volatile static SupportContextHolder instance;

    private SupportContextHolder() {
    }

    private static SupportContextHolder getInstance() {
        if (instance == null) {
            synchronized (SupportContextHolder.class) {
                if (instance == null) {
                    instance = new SupportContextHolder();
                }
            }
        }
        return instance;
    }

    public void initContext(SupportContext context) {
        if (context != null) {
            threadLocal.set(context);
        }
    }

    private final SupportContext getInternalContext() {
        return threadLocal.get();
    }

    public static final SupportContext getContext() {
        return SupportContextHolder.getInstance().getInternalContext();
    }

    public static final void init(SupportContext context) {
        SupportContextHolder.getInstance().initContext(context);
    }

}

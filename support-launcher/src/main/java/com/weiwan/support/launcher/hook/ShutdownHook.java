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
package com.weiwan.support.launcher.hook;

import com.weiwan.support.launcher.envs.ApplicationEnv;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/30 14:51
 * @Package: com.weiwan.support.launcher.hook.ShutdownHook
 * @ClassName: ShutdownHook
 * @Description:
 **/
public class ShutdownHook extends Thread {

    private boolean isRuning;
    private ApplicationEnv env;

    public ShutdownHook(ApplicationEnv env, boolean isRuning) {
        this.isRuning = isRuning;
        this.env = env;
    }

    @Override
    public void run() {
        //处理退出内容
        env.shutdown();
        isRuning = false;
    }
}

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
package com.weiwan.support.launcher;

import com.weiwan.support.launcher.envs.ApplicationEnv;
import com.weiwan.support.launcher.options.GenericRunOption;

import java.io.IOException;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/27 10:42
 * @Package: com.weiwan.support.launcher.K8sJobApplicationProcessor
 * @ClassName: K8sJobApplicationProcessor
 * @Description:
 **/
public class K8sJobApplicationProcessor extends ApplicationEnv {
    public K8sJobApplicationProcessor(String[] args) {
        super(args);
    }

    @Override
    public void init(GenericRunOption genericRunOption) throws IOException {

    }

    @Override
    public boolean process() {
        return false;
    }

    /**
     * 检查通过返回true
     *
     * @param genericRunOption
     * @return
     */
    @Override
    public void emptyParameterCheck(GenericRunOption genericRunOption) {

    }

    /**
     * 检查通过返回true
     *
     * @param genericRunOption
     * @return
     */
    @Override
    public void illegalParameterCheck(GenericRunOption genericRunOption) {

    }

    @Override
    public void stop() {

    }
}

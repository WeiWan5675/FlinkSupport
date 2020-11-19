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
package com.weiwan.support.launcher.envs.processer;

import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.launcher.envs.ApplicationEnv;
import com.weiwan.support.launcher.options.GenericRunOption;
import com.weiwan.support.launcher.options.ReplRunOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/30 10:45
 * @Package: com.weiwan.support.launcher.envs.processer.ReplApplicationProcessor
 * @ClassName: ReplApplicationProcessor
 * @Description:
 **/
public class ReplApplicationProcessor extends ApplicationEnv {
    private ReplRunOption option;

    private static final Logger logger = LoggerFactory.getLogger(ReplApplicationProcessor.class);

    public ReplApplicationProcessor(String[] args) {
        super(args);
        OptionParser optionParser = new OptionParser(args);
        option = optionParser.parse(ReplRunOption.class);
    }

    @Override
    public void init(GenericRunOption option) {

    }

    @Override
    public boolean process() {
        System.out.println("选择? Y or N");
        return true;
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

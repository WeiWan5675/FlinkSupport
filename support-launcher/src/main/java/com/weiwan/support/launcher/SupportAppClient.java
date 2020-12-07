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


import com.weiwan.support.common.exception.SupportException;
import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.launcher.envs.ApplicationEnv;
import com.weiwan.support.launcher.envs.processer.*;
import com.weiwan.support.launcher.hook.ShutdownHook;
import com.weiwan.support.launcher.options.GenericRunOption;
import com.weiwan.support.launcher.enums.RunMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/29 16:31
 * @Package: com.weiwan.support.launcher.SupportAppClient
 * @ClassName: RunSupportApp
 * @Description:
 **/
public class SupportAppClient {

    public static final Logger logger = LoggerFactory.getLogger(SupportAppClient.class);
    private static boolean isRuning = true;

    public static void main(String[] args) {
        logger.info("starting the flink support framework client!");
        try {
            OptionParser optionParser = new OptionParser(args);

            GenericRunOption _option = optionParser.parse(GenericRunOption.class);
            if (_option.isVerbose()) {
                System.out.println("\t Flink Support Version : 1.0 Create By Release-1.0.0");
                return;
            } else if (_option.isHelp()) {
                optionParser.usage();
                return;
            }

            logger.info("using client mode is {}", _option.getRunMode().toUpperCase());
            ApplicationEnv applicationEnv = null;
            RunMode runMode = RunMode.valueOf(_option.getRunMode().toUpperCase());
            switch (runMode) {
                case JOB:
                    boolean enableK8s = Boolean.valueOf(_option.getParams().getOrDefault("enable.kubernetes", "false"));
                    applicationEnv = enableK8s ?
                            new K8sJobApplicationProcessor(args) : new YarnJobApplicationProcessor(args);
                    logger.debug("running job env mode");
                    break;
//                case API: TODO 这里不考虑API模式,单独提供一个API服务,这里只作为应用启动的入口
//                    applicationEnv = new ApiApplicationProcessor(args);
//                    System.out.println("api env mode");
//                    break;
                case REPL:
                    applicationEnv = new ReplApplicationProcessor(args);
                    logger.debug("running repl env mode");
                    break;
                case LOCAL:
                    applicationEnv = new LocalApplicationProcesser(args);
                    break;
                case INIT:
                    applicationEnv = new InitProcesser(args);
                    break;
                default:
                    throw new SupportException("unsupported client environment");
            }

            Runtime.getRuntime().addShutdownHook(new ShutdownHook(applicationEnv, isRuning));
            while (!applicationEnv.enter(runMode) && isRuning) {
                synchronized (SupportAppClient.class) {
                    System.out.println("\n");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            logger.info("The client is closed, thanks for using bye~~~");
        }

    }
}

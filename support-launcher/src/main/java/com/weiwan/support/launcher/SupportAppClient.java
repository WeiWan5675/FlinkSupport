package com.weiwan.support.launcher;

import com.weiwan.support.common.exception.SupportException;
import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.launcher.enums.RunMode;
import com.weiwan.support.launcher.envs.ApiApplicationProcessor;
import com.weiwan.support.launcher.envs.ApplicationEnv;
import com.weiwan.support.launcher.envs.JobApplicationProcessor;
import com.weiwan.support.launcher.envs.ReplApplicationProcessor;
import com.weiwan.support.launcher.hook.ShutdownHook;
import com.weiwan.support.launcher.options.GenericRunOption;
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

            logger.info("using client mode is {}", _option.getRunMode());
            String runMode = _option.getRunMode().toUpperCase();
            ApplicationEnv applicationEnv = null;
            switch (RunMode.valueOf(runMode)) {
                case JOB:
                    applicationEnv = new JobApplicationProcessor(args);
                    logger.info("running job env mode");
                    break;
//                case API: TODO 这里不考虑API模式,单独提供一个API服务,这里只作为应用启动的入口
//                    applicationEnv = new ApiApplicationProcessor(args);
//                    System.out.println("api env mode");
//                    break;
                case REPL:
                    applicationEnv = new ReplApplicationProcessor(args);
                    logger.info("running repl env mode");
                    break;
                default:
                    throw new SupportException("unsupported client environment");
            }

            Runtime.getRuntime().addShutdownHook(new ShutdownHook(applicationEnv, isRuning));
            while (!applicationEnv.enter() && isRuning) {
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

package com.weiwan.support.launcher.envs;

import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.launcher.options.JobRunOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/30 10:46
 * @Package: com.weiwan.support.launcher.envs.JobApplicationProcessor
 * @ClassName: JobApplicationProcessor
 * @Description:
 **/
public class JobApplicationProcessor extends ApplicationEnv {

    private static final Logger logger = LoggerFactory.getLogger(JobApplicationProcessor.class);
    private JobRunOption option;


    public JobApplicationProcessor(String[] args) {
        super(args);
        this.option = optionParser.parse(JobRunOption.class);
    }

    @Override
    public boolean process() {
        logger.info("The Job application handler starts and starts processing the job submission work!");
        String appName = option.getAppName();
        logger.info("job Name is : {}", appName);

        //判断是
        //获取工程目录(AppHome)
        //读取配置文件
        //
        logger.info("The job handler is processed and the job has been submitted!");
        return true;
    }

    @Override
    protected void stop() {
        System.out.println("job process shutdown is run");
    }
}

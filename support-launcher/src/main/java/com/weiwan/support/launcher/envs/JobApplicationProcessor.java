package com.weiwan.support.launcher.envs;

import com.weiwan.support.common.options.OptionParser;
import com.weiwan.support.launcher.enums.RunMode;
import com.weiwan.support.launcher.options.GenericRunOption;
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
        super.genericRunOption = optionParser.parse(JobRunOption.class);
    }


    @Override
    public void init(GenericRunOption option) {
        this.option = (JobRunOption) option;
        //
    }

    @Override
    public boolean process() {
        logger.info("The Job application handler starts and starts processing the job submission work!");
        String appName = option.getAppName();
        logger.info("job Name is : {}", appName);


        /**
         * 上下文解析
         */

        /**
         * job配置
         * 启动配置
         *
         */
        /**
         * 配置文件名称路径解析
         */


        /**
         * 资源处理
         * 1. hdfs资源处理
         * 2. 本地资源处理
         */


        /**
         * 任务提交
         * 1. 本地模式
         * 2. Yarn模式
         * 3. s模式
         */


        //读取配置文件
        //判断资源是在本地 还是在远程
        //如果是在本地,就需要去加载用户的依赖程序,如果是在远程,直接组装参数,准备运行
        //在本地的话,需要上传到HDFS
        //获取工程目录(AppHome)
        //读取配置文件
        //
        logger.info("The job handler is processed and the job has been submitted!");
        return true;
    }


    /**
     * 检查通过返回true
     *
     * @param genericRunOption
     * @return
     */
    @Override
    public boolean emptyParameterCheck(GenericRunOption genericRunOption) {
        return false;
    }

    /**
     * 检查通过返回true
     *
     * @param genericRunOption
     * @return
     */
    @Override
    public boolean illegalParameterCheck(GenericRunOption genericRunOption) {
        return false;
    }

    @Override
    public void stop() {

    }

}

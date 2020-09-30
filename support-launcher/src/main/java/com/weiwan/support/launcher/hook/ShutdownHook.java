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

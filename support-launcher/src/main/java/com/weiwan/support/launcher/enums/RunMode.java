package com.weiwan.support.launcher.enums;

/**
 * @Author: xiaozhennan
 * @Date: 2020/9/30 10:09
 * @Package: com.weiwan.support.launcher.enums.RunMode
 * @ClassName: RunMode
 * @Description:
 **/
public enum RunMode {
    REPL,   //交互模式,为了以后flinkSql/table相关api做预留
    JOB,    //传统客户端提交模式
    API,    //api模式,以后可以通过api方式直接远程拉起任务
    LOCAL;  //本地模式,本地调试使用
}

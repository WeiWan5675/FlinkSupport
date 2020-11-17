package com.weiwan.support.launcher.enums;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/17 9:07
 * @Package: com.weiwan.support.launcher.enums.RunCmd
 * @ClassName: RunCmd
 * @Description:
 **/
public enum RunCmd {
    RUN,
    STOP,
    CANAL,
    INFO,
    LIST,
    SAVEPOINT;

    public static String print() {
        StringBuffer sb =new StringBuffer();

        RunCmd[] values = RunCmd.values();
        for (RunCmd value : values) {
            sb.append(value.name());
            sb.append("|");
        }
        String s = sb.toString();
        String substring = s.substring(0, s.lastIndexOf("|"));
        return substring;
    }
}

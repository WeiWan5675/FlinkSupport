package com.weiwan.support.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author: XuYang
 * @date: 2019-01-06 18:20
 * @description: 堆栈异常工具
 **/
public class StackTraceUtil {

    public static String toStackTrace(Exception e)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try
        {
            e.printStackTrace(pw);
            return sw.toString();
        }
        catch(Exception e1)
        {
            return "";
        }
    }
}

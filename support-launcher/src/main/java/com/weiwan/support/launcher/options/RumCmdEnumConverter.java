package com.weiwan.support.launcher.options;

import com.beust.jcommander.IStringConverter;
import com.weiwan.support.launcher.enums.RunCmd;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/17 9:12
 * @Package: com.weiwan.support.launcher.options.EnumConverter
 * @ClassName: EnumConverter
 * @Description:
 **/
public class RumCmdEnumConverter implements IStringConverter<RunCmd> {
    @Override
    public RunCmd convert(String value) {
        if (value != null && value.length() > 0) {
            try {
                return RunCmd.valueOf(value.toUpperCase());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Please specify the command to execute", e);
            }
        }
        return null;
    }
}

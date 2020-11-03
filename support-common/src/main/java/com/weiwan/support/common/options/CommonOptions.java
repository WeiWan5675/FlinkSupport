package com.weiwan.support.common.options;

import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.Parameter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/3 13:37
 * @Package: com.weiwan.support.common.options.CommonOptions
 * @ClassName: CommonOptions
 * @Description:
 **/
public class CommonOptions {

    @DynamicParameter(names = "-D", description = "Dynamic parameters go here")
    private Map<String, String> params = new HashMap<>();

    @Parameter(names = {"--help", "-help", "-h"}, help = true, description = "help info")
    private boolean help;

    @Parameter(names = {"--version", "-version", "-v"}, description = "support framework client version")
    private boolean verbose;

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public boolean isHelp() {
        return help;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
}

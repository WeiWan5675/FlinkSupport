package com.weiwan.support.core.api;

import com.weiwan.support.core.SupportContext;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/30 11:36
 * @Package: com.weiwan.support.core.api.Support
 * @ClassName: Support
 * @Description:
 **/
public interface Support {

    public SupportContext getContext();

    public void setContext(SupportContext context);
}

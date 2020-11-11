package com.weiwan.support.core;


import com.weiwan.support.core.api.FlinkSupport;
import com.weiwan.support.core.api.SupportDataFlow;

import java.util.List;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/10 16:50
 * @Package: com.weiwan.support.core.SupportCoprocessor
 * @ClassName: SupportCoprocessor
 * @Description:
 **/
public abstract class SupportCoprocessor {

    public abstract <E, S1, S2> void process(SupportDataFlow<E, S1, S2> dataFlow);

}

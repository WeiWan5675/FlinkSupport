package com.weiwan.support.core.coprocessor;

import com.weiwan.support.core.SupportAppContext;
import com.weiwan.support.core.api.SupportDataFlow;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/11 15:44
 * @Package: com.weiwan.support.core.coprocessor.TableCoprocessor
 * @ClassName: TableCoprocessor
 * @Description:
 **/
public class TableCoprocessor extends SupportCoprocessor {
    @Override
    public <E, S1, S2> Object process(E env, SupportDataFlow<E, S1, S2> dataFlow, Object obj) {
        return null;
    }
}

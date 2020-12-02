package com.weiwan.support.core.coprocessor;

/**
 * @Author: xiaozhennan
 * @Date: 2020/12/2 11:04
 * @Package: com.weiwan.support.core.coprocessor.CoprocessorChain
 * @ClassName: CoprocessorChain
 * @Description:
 **/
public interface CoprocessorChain<E, S1, S2> {

    public Object coProcessing() throws Exception;

}

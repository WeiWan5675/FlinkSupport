package com.weiwan.support.core;

/**
 * @Author: xiaozhennan
 * @Date: 2020/12/1 13:57
 * @Package: com.weiwan.support.core.SupportContextHolder
 * @ClassName: SupportContextHandler
 * @Description:
 **/
public class SupportContextHolder {

    private final ThreadLocal<SupportContext> threadLocal = new ThreadLocal<SupportContext>();

    private volatile static SupportContextHolder instance;

    private SupportContextHolder() {
    }

    private static SupportContextHolder getInstance() {
        if (instance == null) {
            synchronized (SupportContextHolder.class) {
                if (instance == null) {
                    instance = new SupportContextHolder();
                }
            }
        }
        return instance;
    }

    public void initContext(SupportContext context) {
        if (context != null) {
            threadLocal.set(context);
        }
    }

    private final SupportContext getInternalContext() {
        return threadLocal.get();
    }

    public static final SupportContext getContext() {
        return SupportContextHolder.getInstance().getInternalContext();
    }

    public static final void init(SupportContext context) {
        SupportContextHolder.getInstance().initContext(context);
    }

}

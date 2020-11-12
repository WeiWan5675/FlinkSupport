package com.weiwan.support.common.cla;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/20 14:18
 * @Package: org.weiwan.argus.core
 * @ClassName: ClassLoaderSupplierCallBack
 * @Description:
 **/
public class ClassLoaderSupplierCallBack {

    public static <R> R callbackAndReset(ClassLoaderSupplier<R> supplier, ClassLoader toSetClassLoader) throws Exception {
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(toSetClassLoader);
        try {
            return supplier.get(toSetClassLoader);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }
}

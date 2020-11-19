/*
 *      Copyright [2020] [xiaozhennan1995@gmail.com]
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *      http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weiwan.support.common.cla;

/**
 * @Author: xiaozhennan
 * @Date: 2020/7/20 14:18
 * @Package: com.weiwan.support.core
 * @ClassName: ClassLoaderSupplier
 * @Description:
 **/
@FunctionalInterface
public interface ClassLoaderSupplier<T> {

    /**
     * 使用给定的类加载器创建对象
     *
     * @param cl 类加载器
     * @return 实例化的对象
     * @throws Exception NoSuchMethodException SecurityException
     */
    T get(ClassLoader cl) throws Exception;
}

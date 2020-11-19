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
package com.weiwan.support.core;

import java.lang.annotation.Annotation;

/**
 * @Author: xiaozhennan
 * @Date: 2020/11/11 14:53
 * @Package: com.weiwan.support.core.AnnotationInfo
 * @ClassName: AnnotationInfo
 * @Description:
 **/
public class AnnotationInfo {

    private String className;
    private String methonName;
    private Class annoClass;

    public AnnotationInfo(Class<? extends Annotation> aClass) {
        this.annoClass = aClass;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethonName() {
        return methonName;
    }

    public void setMethonName(String methonName) {
        this.methonName = methonName;
    }

    public Class getAnnoClass() {
        return annoClass;
    }

    public void setAnnoClass(Class annoClass) {
        this.annoClass = annoClass;
    }
}

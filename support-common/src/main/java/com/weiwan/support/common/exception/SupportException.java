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
package com.weiwan.support.common.exception;

import com.alibaba.fastjson.JSONObject;
import com.weiwan.support.common.enums.SupportExceptionEnum;

/**
 * @Author: xiaozhennan
 * @Date: 2020/4/27 17:24
 * @Package: com.hopson.dc.realtime.common.exception
 * @ClassName: SupportException
 * @Description:
 **/
public class SupportException extends RuntimeException {
    public String code;
    public String msg;

    public SupportException(SupportExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.name();
        this.msg = exceptionEnum.getMsg();
    }

    public SupportException(Throwable e) {
        super(e);
    }

    public SupportException() {
    }

    public SupportException(SupportExceptionEnum exceptionEnum, String msg) {
        super(msg);
        this.code = exceptionEnum.name();
        this.msg = msg;
    }

    public SupportException(String msg) {
        super(msg);
        this.code = SupportExceptionEnum.FAILED.name();
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public static SupportException generateParameterEmptyException(String msg) {
        return new SupportException(SupportExceptionEnum.PARAMETER_EMPTY, msg);
    }

    public static SupportException generateUnknownException(String msg) {
        return new SupportException(SupportExceptionEnum.UNKNOWN, msg);
    }

    public static SupportException generateParameterIllegalException(String msg) {
        return new SupportException(SupportExceptionEnum.PARAMETER_EMPTY, msg);
    }

    public static SupportException generateDataException(String msg) {
        return new SupportException(SupportExceptionEnum.PARAMETER_EMPTY, msg);
    }
}

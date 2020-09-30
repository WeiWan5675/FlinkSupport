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


    public static SupportException generateParameterEmptyException(String msg) {
        return new SupportException(SupportExceptionEnum.PARAMETER_EMPTY, msg);
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

    public JSONObject converToJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", this.getCode());
        jsonObject.put("msg", this.getMsg());
        return jsonObject;
    }
}

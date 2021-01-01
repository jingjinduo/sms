package com.huhi.sms.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ResponseMessage<T> {
    private static final Log logger = LogFactory.getLog(ResponseMessage.class);

    private String respCode;

    private String respMsg;

    private T data;

    private boolean ok;

    public ResponseMessage() {
    }

    public ResponseMessage(String respCode, String message) {
        this.respCode = respCode;
        this.respMsg = message;
        logger.info(toString());
    }

    public ResponseMessage(String respCode, String message, boolean ok) {
        this.respCode = respCode;
        this.respMsg = message;
        this.ok = ok;
        logger.info(toString());
    }

    public ResponseMessage(String respCode, String message, boolean ok, T data) {
        this.respCode = respCode;
        this.respMsg = message;
        this.ok = ok;
        this.data = data;
        logger.info(toString());
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getMessage() {
        return respMsg;
    }

    public void setMessage(String message) {
        this.respMsg = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk() {
        return ok;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
//        return GsonUtil.toJson(this);
    }
}
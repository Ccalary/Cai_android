package com.bc.caibiao.model;

import java.util.List;

/**
 * @author wangkai
 * @Description : Json基本格式
 * create at 16/8/15 下午1:40
 */
public class BaseResponse<T> {
    private static final int SUCCESS = 0;
    private static final int FAIL = -1;
    private static final String DEFAULT_MSG = "服务器未知异常";
    private int state;
    private List<FieldError> fieldErrors;
    private T data;

    /**
     * @return 请求是否成功
     */
    public boolean isSuccess() {
        return state == SUCCESS;
    }

    /**
     * @return 请求是否失败
     */
    public boolean isFail() {
        return state == FAIL;
    }

    /**
     * @return 服务器返回的错误提示
     */
    public String getFailTip() {
        if (fieldErrors != null && fieldErrors.size() > 0) {
            return fieldErrors.get(0).getMessage();
        }
        return DEFAULT_MSG;
    }

    /**
     * @return 服务器返回的错误对象，默认报-99的服务器未知异常
     */
    public FieldError getFailObj() {
        if (fieldErrors != null && fieldErrors.size() > 0) {
            return fieldErrors.get(0);
        }
        FieldError fieldError = new FieldError();
        fieldError.setField("-99");
        fieldError.setMessage(DEFAULT_MSG);
        return fieldError;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}

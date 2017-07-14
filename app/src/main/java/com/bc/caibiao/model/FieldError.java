package com.bc.caibiao.model;

/**
 * Created with IntelliJ IDEA.
 * User: sting
 * Date: 12/3/14
 * Time: 15:35
 * To change this template use File | Settings | File Templates.
 */
public class FieldError {
    private String field;
    private String message;

    public FieldError() {
    }

    public FieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

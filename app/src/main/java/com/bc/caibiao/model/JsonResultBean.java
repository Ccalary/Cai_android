package com.bc.caibiao.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sting
 * Date: 12/3/14
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class JsonResultBean {
	public  static int JSONRESULTBEAN_STATE_NORMAL = 0;
	public  static int JSONRESULTBEAN_STATE_ERROR = -1;
    private int state;
    private List<FieldError> fieldErrors = new ArrayList<FieldError>();
    private Object data;
    private String attr1;
    private String attr2;
    private String attr3;
    private String attr4;
    private String attr5;
    

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

    public void addFieldError(FieldError error) {
        fieldErrors.add(error);
    }

    public void addFieldError(String field, String message) {
        addFieldError(new FieldError(field, message));
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

	public String getAttr1() {
		return attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

	public String getAttr3() {
		return attr3;
	}

	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}

	public String getAttr4() {
		return attr4;
	}

	public void setAttr4(String attr4) {
		this.attr4 = attr4;
	}

	public String getAttr5() {
		return attr5;
	}

	public void setAttr5(String attr5) {
		this.attr5 = attr5;
	}
}

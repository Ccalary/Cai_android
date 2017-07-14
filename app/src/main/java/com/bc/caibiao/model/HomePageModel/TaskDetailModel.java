package com.bc.caibiao.model.HomePageModel;

import com.bc.caibiao.model.FieldError;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/20.
 */

public class TaskDetailModel {
    public String state;
    public ArrayList<FieldError> fieldErrors = new ArrayList<>();
    public TaskDetailInnerModel data;
}

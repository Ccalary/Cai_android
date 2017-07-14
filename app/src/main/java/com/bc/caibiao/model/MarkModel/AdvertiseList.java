package com.bc.caibiao.model.MarkModel;

import com.bc.caibiao.model.FieldError;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/18.
 */

public class AdvertiseList {

    public String state;

    public ArrayList<FieldError> fieldErrors = new ArrayList<>();

    public ArrayList<Advertise> data = new ArrayList<>();
}

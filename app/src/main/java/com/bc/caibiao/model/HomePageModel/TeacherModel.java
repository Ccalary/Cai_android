package com.bc.caibiao.model.HomePageModel;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/17.
 *
 * portrait": "http://imgcdn.58caibiao.com/filetest/30235e7e49782c.png",
 "isTouGaoRen": 0,
 "memberName": "马就好",
 "isDaShi": 1,

 "memberId": 680,
 "mobilePhone": "18651569105",
 "state": 1,
 "groupId": 0
 */

public class TeacherModel {
    public String portrait;
    public String isTouGaoRen;
    public String memberName;
    public String isDaShi;
    public String memberId;
    public String mobilePhone;
    public String state;
    public String groupId;
    public TeacherExtentModel memberExtend;

    public ArrayList<CaseModel> caseList = new ArrayList<>();

}

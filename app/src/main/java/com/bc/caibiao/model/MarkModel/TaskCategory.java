package com.bc.caibiao.model.MarkModel;

import com.bc.caibiao.model.HomePageModel.CategotyChild;

import java.util.ArrayList;

/**
 * Created by chengyanfang on 2017/4/18.
 *
 * "name" : "化学原料",
 "id" : 1,
 "children" : [

 ],
 "level" : "1",
 "parent_id" : 0,
 "state" : 0,
 "trademark_number" : "1"
 */

public class TaskCategory {
//    public String category_name;
//    public String id;
//    public String category_descrption;

    /* 商标类型标识 */
    public String id;
    /* 编号 */
    public String category_number;
    /* 商标类型名称 */
    public String category_name;
    /* 标题 ：如：用于工业、科学、摄影、农业、园艺、森林的化学品，未加工人造合成树脂，未加工塑料物质，肥料，灭火用合成物，淬火和金属焊接用制剂，保存食品用化学品，鞣料，工业用粘全剂*/
    public String category_title;
    /* 描述 */
    public String category_descrption;

    public String category_group_number;
    /* ?? */
    public String category_trademark_code;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCategory_number() {
        return category_number;
    }
    public void setCategory_number(String category_number) {
        this.category_number = category_number;
    }
    public String getCategory_name() {
        return category_name;
    }
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
    public String getCategory_title() {
        return category_title;
    }
    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }
    public String getCategory_descrption() {
        return category_descrption;
    }
    public void setCategory_descrption(String category_descrption) {
        this.category_descrption = category_descrption;
    }

    public String getCategory_group_number() {
        return category_group_number;
    }
    public void setCategory_group_number(String category_group_number) {
        this.category_group_number = category_group_number;
    }
    public String getCategory_trademark_code() {
        return category_trademark_code;
    }
    public void setCategory_trademark_code(String category_trademark_code) {
        this.category_trademark_code = category_trademark_code;
    }
}

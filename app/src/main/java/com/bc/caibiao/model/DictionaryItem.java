package com.bc.caibiao.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典项详细信息表
 * @author zhaochunjiao
 * @create 2017年2月21日 上午9:40:07
 * @version 1.0
 */
public class DictionaryItem {
	/* 主键 */
	private int id;
	/* 分组编码 */
	private String groupCode;
	/* 数据项内容 */
	private String itemContent;
	
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItemContent() {
		return itemContent;
	}
	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}


	public static List<DictionaryItem> generateData(){
		List<DictionaryItem> list=new ArrayList<>();
		for(int i=0;i<5;i++){
			DictionaryItem model=new DictionaryItem();
			model.itemContent="中文名";
			list.add(model);
		}
		return list;
	}

}

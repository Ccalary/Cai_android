package com.bc.caibiao.model;

/**
 * 提问或回复实体类
 * @author zhaochunjiao
 * @create 2017年2月17日 下午3:44:35
 * @version 1.0
 */
public class TaskAskAnswer {
	/* 类型-提问 */
	public static final short MODELTYPE_ASK  = 1;
	/* 类型-回复 */
	public static final short MODELTYPE_ANSWER  = 2;
	
	/* 主键 */
	private int id;
	/* 任务投稿标识 */
	private int touGaoId;
	/* 类型，取值见常量 */
	private int modelType;
	/* 提问或回复内容 */
	private String content;
	/* 提问人或回复人标识 */
	private int creatorId;
	/* 提问人或回复人姓名 */
	private String creatorName;
	/* 提问或回复时戳 */
	private int createdTimestamp;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTouGaoId() {
		return touGaoId;
	}
	public void setTouGaoId(int touGaoId) {
		this.touGaoId = touGaoId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public int getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(int createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
	public int getModelType() {
		return modelType;
	}
	public void setModelType(int modelType) {
		this.modelType = modelType;
	}
}

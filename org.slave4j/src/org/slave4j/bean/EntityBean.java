package org.slave4j.bean;

import java.util.ArrayList;
import java.util.List;

public class EntityBean {
	private String modeName;
	private String entityPackageName;
	private String entityClassName;
	List<FieldBean> entityFieldInfoList = new ArrayList();

	public String getEntityPackageName() {
		return entityPackageName;
	}

	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	public String getEntityClassName() {
		return entityClassName;
	}

	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}

	public List<FieldBean> getEntityFieldInfoList() {
		return this.entityFieldInfoList;
	}

	public void setEntityFieldInfoList(List<FieldBean> entityFieldInfoList) {
		this.entityFieldInfoList = entityFieldInfoList;
	}

	public void setEntityPackageName(String entityPackageName) {
		this.entityPackageName = entityPackageName;
	}
	
	

	
}
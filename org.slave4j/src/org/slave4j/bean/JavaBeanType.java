package org.slave4j.bean;

public enum JavaBeanType {
	DAO{
		@Override
		public String getTplName() {
			return "dao.ftl";
		}
		
	}, SERVICE{
		@Override
		public String getTplName() {
			return "service.ftl";
		}
		
	}, ACTION{
		@Override
		public String getTplName() {
			return "action.ftl";
		}
		
	}, JAVABEAN{
		@Override
		public String getTplName() {
			return "javabean.ftl";
		}
		
	}, ENTITY{
		@Override
		public String getTplName() {
			return "entity.ftl";
		}
		
	}; // JAVABEAN liusu+
	
	public abstract String getTplName();
}

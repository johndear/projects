package org.slave4j.bean;

public enum JspBeanType {
	 LIST{

		@Override
		public String getTplName() {
			// TODO Auto-generated method stub
			return "jspListV2.ftl";
		}
	 }, ADD{

		@Override
		public String getTplName() {
			// TODO Auto-generated method stub
			return "jspAdd.ftl";
		}
	 }, EDIT{

		@Override
		public String getTplName() {
			// TODO Auto-generated method stub
			return "jspEdit.ftl";
		}
	}, BASIC{

		@Override
		public String getTplName() {
			// TODO Auto-generated method stub
			return "jsp.ftl";
		}
	};
	 
	 public abstract String getTplName();
}

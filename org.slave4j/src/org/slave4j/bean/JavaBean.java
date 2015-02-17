package org.slave4j.bean;


public class JavaBean extends EntityBean {
	private String packageName;
	private String className;
	private JavaBeanType type;
	private boolean subpackage; // 是否分包创建

	public String getPackageName() {
		if(subpackage && this.type != null){
			switch (this.type.ordinal()) {
			case 0:
				this.packageName = getPackageName(this.getEntityPackageName(),
						"dao");
				break;
			case 1:
				this.packageName = getPackageName(this.getEntityPackageName(),
						"service");
				break;
			case 2:
				this.packageName = getPackageName(this.getEntityPackageName(),
						"action");
				break;
			case 3:
				this.packageName = getPackageName(this.getEntityPackageName(), "");
				break;
			case 4:
				this.packageName = getPackageName(this.getEntityPackageName(),
						"entity");
			}
		}
		
		return this.getEntityPackageName();
		
	}
	
	public String getPackageName(int type) {
		String packageName = null;
		switch (this.type.ordinal()) {
		case 0:
			packageName = getPackageName(this.getEntityPackageName(),
					"dao");
			break;
		case 1:
			packageName = getPackageName(this.getEntityPackageName(),
					"service");
			break;
		case 2:
			packageName = getPackageName(this.getEntityPackageName(),
					"action");
			break;
		case 3:
			packageName = getPackageName(this.getEntityPackageName(), "");
			break;
		case 4:
			packageName = getPackageName(this.getEntityPackageName(),
					"entity");
		}
	
		return packageName;
	}

	public String getClassName() {
		if(this.type != null){
			switch (this.type.ordinal()) {
			case 0:
				this.className = this.getEntityClassName() + "Dao";
				break;
			case 1:
				this.className = this.getEntityClassName() + "Service";
				break;
			case 2:
				this.className = this.getEntityClassName() + "Action";
				break;
			case 3:
				this.className = this.getEntityClassName() + "";
				break;
			case 4:
				this.className = this.getEntityClassName() + "Entity";
			}
		}

		return this.className;
	}
	
	public String getClassName(int type) {
		String className = null;
		switch (type) {
		case 0:
			className = this.getEntityClassName() + "Dao";
			break;
		case 1:
			className = this.getEntityClassName() + "Service";
			break;
		case 2:
			className = this.getEntityClassName() + "Action";
			break;
		case 3:
			className = this.getEntityClassName() + "";
			break;
		case 4:
			className = this.getEntityClassName() + "Entity";
		}

		return className;
	}

	public JavaBeanType getType() {
		return this.type;
	}

	public void setType(JavaBeanType type) {
		this.type = type;
	}
	
	public boolean isSubpackage() {
		return subpackage;
	}

	public void setSubpackage(boolean subpackage) {
		this.subpackage = subpackage;
	}

	private String getPackageName(String entityPackageName, String type) {
		if (entityPackageName.indexOf(".") != -1) {
			int index = entityPackageName.lastIndexOf(".");
			return entityPackageName.substring(0, index) + "." + type;
		}

		return type;
	}
}
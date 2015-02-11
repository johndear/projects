package org.slave4j.bean;

public class JsTemplateArgs extends BaseTemplateArgs {
	private String entityName;
	private String jsName;
	private JspTemplateType type;

	public String getModeName() {
		if (getPackageNameInfo() != null) {
			return getPackageNameInfo().getModeName();
		}
		return null;
	}

	public String getEntityName() {
		return this.entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getJsName() {
		return this.jsName + ".js";
	}

	public void setJsName(String jsName) {
		this.jsName = jsName;
	}

	public JspTemplateType getType() {
		return this.type;
	}

	public void setType(JspTemplateType type) {
		this.type = type;
	}

}
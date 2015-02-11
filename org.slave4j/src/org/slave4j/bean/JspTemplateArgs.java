package org.slave4j.bean;

public class JspTemplateArgs extends BaseTemplateArgs {
	private String entityName;
	private String jspName;
	private JspTemplateType type;

	public String getModeName() {
		if(getPackageNameInfo()!=null){
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

	public String getJspName() {
		if(type!=null){
			switch (this.type.ordinal())
		    {
		    case 1:
		      this.jspName = "list.jsp";
		      break;
		    case 2:
		      this.jspName = "input.jsp";
		    }
			
			return this.jspName;
		}else{
			return this.jspName + ".jsp";
		}
	}

	public void setJspName(String jspName) {
		this.jspName = jspName;
	}

	public JspTemplateType getType() {
		return this.type;
	}

	public void setType(JspTemplateType type) {
		this.type = type;
	}

}
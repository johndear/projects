package org.slave4j.bean;

import org.apache.commons.lang.StringUtils;

public class JspBean extends JavaBean {
	private JspBeanType jspType;
	private String jspName;

	public String getJspName() {
		if(jspType!=null){
			switch (this.jspType.ordinal())
		    {
		    case 0:
		      this.jspName = StringUtils.uncapitalize(this.getEntityClassName()) + "_list";
		      break;
		    case 1:
		      this.jspName = StringUtils.uncapitalize(this.getEntityClassName()) + "_add";
		      break;
		    case 2:
		      this.jspName = StringUtils.uncapitalize(this.getEntityClassName()) + "_edit";
		    }
			
			return this.jspName;
		}else{
			return this.jspName + "";
		}
	}

	public void setJspName(String jspName) {
		this.jspName = jspName;
	}

	public JspBeanType getJspType() {
		return jspType;
	}

	public void setJspType(JspBeanType jspType) {
		this.jspType = jspType;
	}

	
	
}
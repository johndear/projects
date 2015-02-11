package org.slave4j.bean;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.core.ICompilationUnit;

public class ClassNameInfo
{
  private String entityClassName;
  private boolean flag;

  public ClassNameInfo(ICompilationUnit compilationUnit)
  {
    String entityFileName = compilationUnit.getElementName();
    this.entityClassName = entityFileName.substring(0, entityFileName.lastIndexOf("."));
  }
  
  public String getEntityClassName()
  {
	  if(flag){
		  return this.entityClassName + "Entity";
	  }else{
		  return this.entityClassName;
	  }
  }

  public String getEntityObjectName()
  {
    return StringUtils.uncapitalize(this.entityClassName + "Entity");
  }

  public String getDaoClassName()
  {
    return this.entityClassName + "Dao";
  }

  public String getDaoObjectName()
  {
    return StringUtils.uncapitalize(this.entityClassName + "Dao");
  }

  public String getServicClassName()
  {
    return this.entityClassName + "Service";
  }

  public String getServicObjectName()
  {
    return StringUtils.uncapitalize(this.entityClassName + "Service");
  }

  public String getActionClassName()
  {
    return this.entityClassName + "Action";
  }

  public String getActionObjectName()
  {
    return StringUtils.uncapitalize(this.entityClassName + "Action");
  }
  
  /**------------- liusu ----------------*/
  public ClassNameInfo(String entityClassName)
  {
    this.entityClassName = entityClassName;
  }

	public boolean isFlag() {
		return flag;
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
  
  
  
}
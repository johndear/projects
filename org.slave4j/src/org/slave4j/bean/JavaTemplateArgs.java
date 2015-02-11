package org.slave4j.bean;

public class JavaTemplateArgs extends BaseTemplateArgs
{
  private String packageName;
  private String className;
  private JavaTemplateType type;

  public String getModeName()
  {
    return getPackageNameInfo().getModeName();
  }

  public String getPackageName()
  {
	if(getPackageNameInfo().getSameEntityPackageName()){ // liusu+
		return getPackageNameInfo().getEntityPackageName();
	}
	
    switch (this.type.ordinal())
    {
    case 0:
      this.packageName = getPackageNameInfo().getDaoPackageName();
      break;
    case 1:
      this.packageName = getPackageNameInfo().getServicPackageName();
      break;
    case 2:
      this.packageName = getPackageNameInfo().getActionPackageName();
      break;
    case 3:
      this.packageName = getPackageNameInfo().getEntityPackageName(); // liusu+
      break;
    case 4:
      this.packageName = getPackageNameInfo().getEntityPackageName(); // liusu+
    }

    return this.packageName;
  }

  public String getClassName()
  {
    switch (this.type.ordinal())
    {
    case 0:
      this.className = getClassNameInfo().getDaoClassName();
      break;
    case 1:
      this.className = getClassNameInfo().getServicClassName();
      break;
    case 2:
      this.className = getClassNameInfo().getActionClassName();
      break;
    case 3:
      this.className = getClassNameInfo().getEntityClassName(); // liusu+
      break;
    case 4:
      getClassNameInfo().setFlag(true);
      this.className = getClassNameInfo().getEntityClassName(); // liusu+
    }

    return this.className;
  }

  public JavaTemplateType getType()
  {
    return this.type;
  }

  public void setType(JavaTemplateType type)
  {
    this.type = type;
  }
}
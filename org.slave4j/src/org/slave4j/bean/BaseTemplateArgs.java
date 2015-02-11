package org.slave4j.bean;

import java.util.ArrayList;
import java.util.List;

public class BaseTemplateArgs
{
  private PackageNameInfo packageNameInfo;
  private ClassNameInfo classNameInfo;
  List<EntityFieldInfo> entityFieldInfoList = new ArrayList();

  public PackageNameInfo getPackageNameInfo()
  {
    return this.packageNameInfo;
  }

  public void setPackageNameInfo(PackageNameInfo packageNameInfo)
  {
    this.packageNameInfo = packageNameInfo;
  }

  public ClassNameInfo getClassNameInfo()
  {
    return this.classNameInfo;
  }

  public void setClassNameInfo(ClassNameInfo classNameInfo)
  {
    this.classNameInfo = classNameInfo;
  }

  public List<EntityFieldInfo> getEntityFieldInfoList()
  {
    return this.entityFieldInfoList;
  }

  public void setEntityFieldInfoList(List<EntityFieldInfo> entityFieldInfoList)
  {
    this.entityFieldInfoList = entityFieldInfoList;
  }
}
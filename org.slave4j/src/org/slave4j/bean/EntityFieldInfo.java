package org.slave4j.bean;

import java.util.ArrayList;
import java.util.List;

public class EntityFieldInfo
{
  private String fieldType;
  private List<String> fieldNames = new ArrayList();

  public String getFieldType()
  {
    return this.fieldType;
  }

  public void setFieldType(String fieldType)
  {
    this.fieldType = fieldType;
  }

  public List<String> getFieldNames()
  {
    return this.fieldNames;
  }

  public void addFieldName(String fieldName)
  {
    this.fieldNames.add(fieldName);
  }
}
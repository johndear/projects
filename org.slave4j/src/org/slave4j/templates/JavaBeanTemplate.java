package org.slave4j.templates;

import java.util.HashMap;
import java.util.Map;

import org.slave4j.bean.JavaTemplateArgs;
import org.slave4j.templates.util.FreeMarkertUtil;

public class JavaBeanTemplate
{
//  public final String NL = System.getProperties().getProperty("line.separator");

  public String generate(Object argument)
  {
    JavaTemplateArgs args = (JavaTemplateArgs)argument;
    Map<String,Object> dataModel = new HashMap<String,Object>();
    dataModel.put("packageName", args.getPackageName());
    dataModel.put("className", args.getClassName());
    
    return FreeMarkertUtil.generate("javabean.ftl", dataModel);
  }
}
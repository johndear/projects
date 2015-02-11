package org.slave4j.templates;

import java.util.HashMap;
import java.util.Map;

import org.slave4j.bean.JavaTemplateArgs;
import org.slave4j.templates.util.FreeMarkertUtil;

public class ActionTemplate
{
  public String generate(Object argument){
	JavaTemplateArgs args = (JavaTemplateArgs)argument;
    
	Map<String, Object> dataModel = new HashMap<String, Object>();
	dataModel.put("packageName", args.getPackageName());
	dataModel.put("entityPackageName", args.getPackageNameInfo().getEntityPackageName());
	dataModel.put("entityClassName", args.getClassNameInfo().getEntityClassName());
	dataModel.put("className", args.getClassName());
	dataModel.put("modeName", args.getModeName()!=null? args.getModeName() : "");
	dataModel.put("entityObjectName", args.getClassNameInfo().getEntityObjectName());
	dataModel.put("serviceClassName", args.getClassNameInfo().getServicClassName());
	dataModel.put("serviceObjectName", args.getClassNameInfo().getServicObjectName());
	dataModel.put("servicePackageName", args.getPackageNameInfo().getServicPackageName());
	
	return FreeMarkertUtil.generate("action.ftl", dataModel);
  }
}
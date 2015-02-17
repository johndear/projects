package org.slave4j.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slave4j.bean.JavaBean;
import org.slave4j.templates.FreeMarkertUtil;

public class JavaTemplateGenerator
{
  public String generate(Object argument){
	JavaBean args = (JavaBean)argument;
    
	Map<String, Object> dataModel = new HashMap<String, Object>();
	dataModel.put("packageName", args.getPackageName());
	dataModel.put("className", args.getClassName());
	dataModel.put("modeName", args.getModeName()!=null? args.getModeName() : "");
	dataModel.put("entityPackageName", args.getEntityPackageName());
	dataModel.put("entityClassName", args.getEntityClassName());
	dataModel.put("entityObjectName", StringUtils.uncapitalize(args.getEntityClassName()));
	dataModel.put("servicePackageName", args.getPackageName(1));
	dataModel.put("serviceClassName", args.getClassName(1));
	dataModel.put("serviceObjectName", StringUtils.uncapitalize(args.getClassName(1)));
	dataModel.put("daoObjectName", StringUtils.uncapitalize(args.getClassName(0)));
	
	return FreeMarkertUtil.generate(args.getType().getTplName(), dataModel);
  }
}
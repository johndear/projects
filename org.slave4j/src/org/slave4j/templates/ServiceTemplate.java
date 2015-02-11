package org.slave4j.templates;

import java.util.HashMap;
import java.util.Map;

import org.slave4j.bean.JavaTemplateArgs;
import org.slave4j.templates.util.FreeMarkertUtil;

public class ServiceTemplate {

	public String generate(Object argument) {
		JavaTemplateArgs args = (JavaTemplateArgs) argument;
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put("packageName", args.getPackageName());
		dataModel.put("entityPackageName", args.getPackageNameInfo().getEntityPackageName());
		dataModel.put("entityClassName", args.getClassNameInfo().getEntityClassName());
		dataModel.put("className", args.getClassName());
		dataModel.put("daoObjectName", args.getClassNameInfo().getDaoObjectName());

		return FreeMarkertUtil.generate("service.ftl", dataModel);
	}
}
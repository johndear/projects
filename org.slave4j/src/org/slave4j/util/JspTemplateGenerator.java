package org.slave4j.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slave4j.bean.FieldBean;
import org.slave4j.bean.JavaBean;
import org.slave4j.bean.JspBean;
import org.slave4j.templates.FreeMarkertUtil;

public class JspTemplateGenerator
{
  public String generateJsp(Object argument){
	JspBean args = (JspBean)argument;
	
	StringBuffer stringBuffer = new StringBuffer();
	StringBuffer stringBuffer2 = new StringBuffer();

	String modeName = args.getModeName();
	String entityObjectName = "";//args.getClassNameInfo().getEntityObjectName();

	List entityFieldInfoList = args.getEntityFieldInfoList();
//	for (Object object : entityFieldInfoList) {
//		FieldBean entityFieldInfo = (FieldBean) object;
//		String fieldName = entityFieldInfo.getFieldName();
//		// fields
//		stringBuffer.append("<th><a href=\"javascript:sort('')\" >${entity."+ fieldName +"}</a></th>");
//		stringBuffer2.append("<td>${entity.}"+ fieldName +"</td>");
//	}
    
	Map<String, Object> dataModel = new HashMap<String, Object>();
	dataModel.put("packageName", args.getPackageName());
	dataModel.put("entityPackageName", args.getEntityPackageName());
	dataModel.put("entityClassName", args.getEntityClassName());
	dataModel.put("className", args.getClassName());
	dataModel.put("modeName", args.getModeName()!=null? args.getModeName() : "");
	dataModel.put("entityObjectName", args.getEntityClassName());
	dataModel.put("serviceClassName", args.getEntityClassName());
	dataModel.put("serviceObjectName", args.getEntityClassName());
	dataModel.put("servicePackageName", args.getEntityClassName());
	dataModel.put("daoObjectName", args.getEntityClassName());
	dataModel.put("ctx", "${ctx }");
	dataModel.put("thHtml", stringBuffer.toString());
	dataModel.put("tdHtml", stringBuffer2.toString());
	dataModel.put("pageNo", "");
	dataModel.put("fieldName", "");
	dataModel.put("compositorType", "");
	dataModel.put("totalPage", "");
	dataModel.put("prevPage", "");
	dataModel.put("nextPage", "");
	dataModel.put("result", "");
	dataModel.put("visible", "");
	dataModel.put("id", "${aaa}");
	dataModel.put("insertTime", "");
	dataModel.put("lastUpdateTime", "");
	dataModel.put("hasPrevPage", "");
	dataModel.put("hasNextPage", "");
	dataModel.put("columnDatas", entityFieldInfoList);
	
	return FreeMarkertUtil.generate(args.getJspType().getTplName(), dataModel);
  }
  
  public String generateJs(Object argument){
		JspBean args = (JspBean)argument;
		
		StringBuffer stringBuffer = new StringBuffer();
		StringBuffer stringBuffer2 = new StringBuffer();

		String modeName = args.getModeName();
		String entityObjectName = "";//args.getClassNameInfo().getEntityObjectName();

		List entityFieldInfoList = args.getEntityFieldInfoList();
//		for (Object object : entityFieldInfoList) {
//			FieldBean entityFieldInfo = (FieldBean) object;
//			String fieldName = entityFieldInfo.getFieldName();
//			// fields
//			stringBuffer.append("<th><a href=\"javascript:sort('')\" >${entity."+ fieldName +"}</a></th>");
//			stringBuffer2.append("<td>${entity.}"+ fieldName +"</td>");
//		}
	    
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put("packageName", args.getPackageName());
		dataModel.put("entityPackageName", args.getEntityPackageName());
		dataModel.put("entityClassName", args.getEntityClassName());
		dataModel.put("className", args.getClassName());
		dataModel.put("modeName", args.getModeName()!=null? args.getModeName() : "");
		dataModel.put("entityObjectName", args.getEntityClassName());
		dataModel.put("serviceClassName", args.getEntityClassName());
		dataModel.put("serviceObjectName", args.getEntityClassName());
		dataModel.put("servicePackageName", args.getEntityClassName());
		dataModel.put("daoObjectName", args.getEntityClassName());
		dataModel.put("ctx", "${ctx }");
		dataModel.put("thHtml", stringBuffer.toString());
		dataModel.put("tdHtml", stringBuffer2.toString());
		dataModel.put("pageNo", "");
		dataModel.put("fieldName", "");
		dataModel.put("compositorType", "");
		dataModel.put("totalPage", "");
		dataModel.put("prevPage", "");
		dataModel.put("nextPage", "");
		dataModel.put("result", "");
		dataModel.put("visible", "");
		dataModel.put("id", "${aaa}");
		dataModel.put("insertTime", "");
		dataModel.put("lastUpdateTime", "");
		dataModel.put("hasPrevPage", "");
		dataModel.put("hasNextPage", "");
		dataModel.put("columnDatas", entityFieldInfoList);
		
		String jspTplName = args.getJspType().getTplName();
		String jsTplName = jspTplName.substring(0,jspTplName.indexOf("."))+".js"+".ftl";
		return FreeMarkertUtil.generate(jsTplName, dataModel);
	  }
}
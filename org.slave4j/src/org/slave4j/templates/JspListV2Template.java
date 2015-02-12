package org.slave4j.templates;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slave4j.bean.EntityFieldInfo;
import org.slave4j.bean.JspTemplateArgs;
import org.slave4j.templates.util.FreeMarkertUtil;

public class JspListV2Template {
	protected static String nl;
	public final String NL = nl == null ? System.getProperties().getProperty("line.separator") : nl;

	@SuppressWarnings("unchecked")
	public String generate(Object argument) {
		StringBuffer stringBuffer = new StringBuffer();
		StringBuffer stringBuffer2 = new StringBuffer();

		JspTemplateArgs args = (JspTemplateArgs) argument;
		String modeName = args.getModeName();
		String entityObjectName = "";//args.getClassNameInfo().getEntityObjectName();

		List entityFieldInfoList = args.getEntityFieldInfoList();
		for (Object object : entityFieldInfoList) {
			EntityFieldInfo entityFieldInfo = (EntityFieldInfo) object;
			List fieldNames = entityFieldInfo.getFieldNames();
			for (Iterator iterator2 = fieldNames.iterator(); iterator2.hasNext();) {
				String fieldName = (String) iterator2.next();
				// fields
				stringBuffer.append("<th><a href=\"javascript:sort('')\" >${entity."+ fieldName +"}</a></th>");
				stringBuffer2.append("<td>${entity.}"+ fieldName +"</td>");
			}
		}
		
		
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put("${ctx }", "${ctx }");
		dataModel.put("modeName", modeName!=null?modeName:"");
		dataModel.put("entityObjectName", entityObjectName);
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
		
		return FreeMarkertUtil.generate("jspList.ftl", dataModel);
	}
}
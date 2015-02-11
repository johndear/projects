package org.slave4j.templates.util;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.slave4j.templates.ActionTemplate;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkertUtil {
	private static Configuration config = new Configuration();

	public static String generate(String templateName, Map<String, Object> dataModel) {
		Template tpl;
		try {
			config.setClassForTemplateLoading(ActionTemplate.class, "");
			tpl = config.getTemplate(templateName);
			if (tpl == null) {
				throw new Exception("根据模板名称：" + templateName + "找不到模板文件");
			}
			Writer out = new StringWriter();
			tpl.process(dataModel, out);
			String htmlText = out.toString();
			return htmlText;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
}

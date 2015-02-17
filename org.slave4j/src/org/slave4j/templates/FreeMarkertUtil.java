package org.slave4j.templates;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.slave4j.util.JavaTemplateGenerator;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkertUtil {
	private static Configuration config = new Configuration();

	public static String generate(String templateName, Map<String, Object> dataModel) {
		Template tpl;
		try {
//			config.setClassForTemplateLoading(ActionTemplate.class, "");
//			FileTemplateLoader loader1 = new FileTemplateLoader(new File(ActionTemplate.class.getResource("../").toURI()));
			ClassTemplateLoader loader2 = new ClassTemplateLoader(FreeMarkertUtil.class,"");
			TemplateLoader[] loaders = {loader2};
			MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
			config.setTemplateLoader(mtl);
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

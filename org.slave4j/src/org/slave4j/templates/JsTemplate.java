package org.slave4j.templates;

import java.util.HashMap;
import java.util.Map;

import org.slave4j.templates.util.FreeMarkertUtil;

public class JsTemplate
{

	public final String NL = System.getProperties().getProperty("line.separator");

  public String generate(Object argument)
  {
//    JavaTemplateArgs args = (JavaTemplateArgs) argument;
	Map<String, Object> dataModel = new HashMap<String, Object>();

	return FreeMarkertUtil.generate("js.ftl", dataModel);
  }
}
package org.slave4j.templates;

import java.util.Iterator;
import java.util.List;

import org.slave4j.bean.EntityFieldInfo;
import org.slave4j.bean.JspTemplateArgs;

public class JspInputTemplate
{
  protected static String nl;
  public final String NL = nl == null ? System.getProperties().getProperty("line.separator") : nl;
  protected final String TEXT_1 = "<%@ page contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>" + this.NL + "<%@ include file=\"/WEB-INF/jsp/commons/taglibs.jsp\"%>" + this.NL + "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">" + this.NL + "<html>" + this.NL + "<head>" + this.NL + "<title>";
  protected final String TEXT_2 = "表单</title>" + this.NL + "<%@ include file=\"/WEB-INF/jsp/commons/meta.jsp\"%>" + this.NL + "<script>" + this.NL + "\t$(document).ready(function(){" + this.NL + "\t\t//聚焦第一个输入框" + this.NL + "\t\t$(\"#name\").focus();" + this.NL + "\t\t//为inputForm注册validate函数" + this.NL + "\t\t$(\"#inputForm\").validate({" + this.NL + "\t\t\trules: {" + this.NL + "\t\t\t\t";
  protected final String TEXT_3 = "\t" + this.NL + "\t\t\t}" + this.NL + "\t\t});" + this.NL + "\t});" + this.NL + "\t</script>" + this.NL + "</head>" + this.NL + "<body>" + this.NL + "<div id=\"container\" class=\"container_24\">" + this.NL + "  <!-- header -->" + this.NL + "  <div id=\"header\">" + this.NL + "    <%@ include file=\"/WEB-INF/jsp/commons/header.jsp\"%>" + this.NL + "  </div>" + this.NL + "  " + this.NL + "  <!-- content -->" + this.NL + "  <div id=\"content\" class=\"container_24\">" + this.NL + "    <div class=\" suffix_5 grid_14 prefix_5\">" + this.NL + "    \t<div class=\"area\">" + this.NL + "    \t\t<form id=\"inputForm\" action=\"${ctx}/";
  protected final String TEXT_4 = "/";
  protected final String TEXT_5 = "/save\" method=\"post\">" + this.NL + "\t\t      <input type=\"hidden\" name=\"id\" value=\"${";
  protected final String TEXT_6 = ".id}\" />" + this.NL + "\t\t      <table>" + this.NL + "\t\t      \t";
  protected final String TEXT_7 = this.NL + "\t\t        <tr>" + this.NL + "\t\t          <td>";
  protected final String TEXT_8 = ":</td>" + this.NL + "\t\t          <td><input id=\"";
  protected final String TEXT_9 = "\" name=\"";
  protected final String TEXT_10 = "\" type=\"text\" value=\"${";
  protected final String TEXT_11 = ".";
  protected final String TEXT_12 = "}\" size=\"30\" maxlength=\"80\" /></td>" + this.NL + "\t\t        </tr>" + this.NL + "\t\t        ";
  protected final String TEXT_13 = this.NL + "\t\t        <tr>" + this.NL + "\t\t          <td colspan=\"2\"><input class=\"submit\" type=\"submit\" value=\"提交\" />" + this.NL + "\t\t            &nbsp;" + this.NL + "\t\t            <input class=\"submit\" type=\"button\" value=\"返回\" onClick=\"history.back()\" /></td>" + this.NL + "\t\t        </tr>" + this.NL + "\t\t      </table>" + this.NL + "\t\t    </form>" + this.NL + "    \t</div>" + this.NL + "    \t" + this.NL + "    </div>" + this.NL + "  </div>  " + this.NL + "  " + this.NL + "  <!-- footer -->" + this.NL + "  <div id=\"footer\">" + this.NL + "    <%@ include file=\"/WEB-INF/jsp/commons/footer.jsp\"%>" + this.NL + "  </div>" + this.NL + "</div>" + this.NL + "</body>" + this.NL + "</html>";
  protected final String TEXT_14 = this.NL;

  public static synchronized JspInputTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    JspInputTemplate result = new JspInputTemplate();
    nl = null;
    return result;
  }

  @SuppressWarnings("unchecked")
public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();

    JspTemplateArgs args = (JspTemplateArgs)argument;

    String modeName = args.getModeName();
    args.getEntityName();
    args.getJspName();

    List entityFieldInfoList = args.getEntityFieldInfoList();

    args.getPackageNameInfo().getEntityPackageName();
    args.getPackageNameInfo().getDaoPackageName();
    args.getPackageNameInfo().getServicPackageName();
    args.getPackageNameInfo().getActionPackageName();

    args.getClassNameInfo().getEntityClassName();
    args.getClassNameInfo().getDaoClassName();
    args.getClassNameInfo().getServicClassName();
    args.getClassNameInfo().getActionClassName();

    String entityObjectName = args.getClassNameInfo().getEntityObjectName();
    args.getClassNameInfo().getDaoObjectName();
    args.getClassNameInfo().getServicObjectName();
    args.getClassNameInfo().getActionObjectName();
    String jsCode = "";
    StringBuffer temp = new StringBuffer();
	for (Iterator iterator = entityFieldInfoList.iterator(); iterator.hasNext();) {
		EntityFieldInfo entityFieldInfo = (EntityFieldInfo) iterator.next();
		List fieldNames = entityFieldInfo.getFieldNames();
		String fieldName;
		for (Iterator iterator2 = fieldNames.iterator(); iterator2.hasNext(); temp.append((new StringBuilder(String.valueOf(fieldName))).append(":\"required\",").toString()))
			fieldName = (String) iterator2.next();

	}
    jsCode = temp.substring(0, temp.length() - 1);

    stringBuffer.append(this.TEXT_1);
    stringBuffer.append(entityObjectName);
    stringBuffer.append(this.TEXT_2);
    stringBuffer.append(jsCode);
    stringBuffer.append(this.TEXT_3);
    stringBuffer.append(modeName);
    stringBuffer.append("/");
    stringBuffer.append(entityObjectName);
    stringBuffer.append(this.TEXT_5);
    stringBuffer.append(entityObjectName);
    stringBuffer.append(this.TEXT_6);

    for (Iterator iterator1 = entityFieldInfoList.iterator(); iterator1.hasNext();) {
		EntityFieldInfo entityFieldInfo = (EntityFieldInfo) iterator1.next();
		List fieldNames = entityFieldInfo.getFieldNames();
		for (Iterator iterator3 = fieldNames.iterator(); iterator3.hasNext(); stringBuffer.append(TEXT_12)) {
			String fieldName = (String) iterator3.next();
			stringBuffer.append(TEXT_7);
			stringBuffer.append(fieldName);
			stringBuffer.append(TEXT_8);
			stringBuffer.append(fieldName);
			stringBuffer.append("\" name=\"");
			stringBuffer.append(fieldName);
			stringBuffer.append("\" type=\"text\" value=\"${");
			stringBuffer.append(entityObjectName);
			stringBuffer.append(".");
			stringBuffer.append(fieldName);
		}

	}

    stringBuffer.append(this.TEXT_13);
    stringBuffer.append(this.TEXT_14);
    return stringBuffer.toString();
  }
}
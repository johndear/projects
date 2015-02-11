package org.slave4j.templates;

import java.util.Iterator;
import java.util.List;

import org.slave4j.bean.EntityFieldInfo;
import org.slave4j.bean.JspTemplateArgs;

public class JspListTemplate
{
  protected static String nl;
  public final String NL = nl == null ? System.getProperties().getProperty("line.separator") : nl;
  protected final String TEXT_1 = "<%@ page contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>" + this.NL + "<%@ include file=\"/WEB-INF/jsp/commons/taglibs.jsp\"%>" + this.NL + "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">" + this.NL + "<html>" + this.NL + "<head>" + this.NL + "<title>";
  protected final String TEXT_2 = "列表</title>" + this.NL + "<%@ include file=\"/WEB-INF/jsp/commons/meta.jsp\"%>" + this.NL + "</head>" + this.NL + "<body>" + this.NL + "<div id=\"container\" class=\"container_24\">" + this.NL + "  <!-- header -->" + this.NL + "  <%@ include file=\"/WEB-INF/jsp/commons/header.jsp\"%>" + this.NL + "  " + this.NL + "  <!-- content -->" + this.NL + "  <div id=\"content\" class=\"container_24\">" + this.NL + "    <div class=\"container_24\">" + this.NL + "      <div class=\"area2\">" + this.NL + "        <!-- 分页显示演示开始 -->" + this.NL + "        <form id=\"listForm\" action=\"${ctx}/";
  protected final String TEXT_3 = "/";
  protected final String TEXT_4 = "/list\" method=\"get\">" + this.NL + "          <input type=\"hidden\" name=\"pageNo\" id=\"pageNo\" value=\"${pageData.pagination.pageNo}\" />" + this.NL + "          <input type=\"hidden\" name=\"fieldName\" id=\"fieldName\" value=\"${pageData.compositor.fieldName}\" />" + this.NL + "          <input type=\"hidden\" name=\"compositorType\" id=\"compositorType\" value=\"${pageData.compositor.compositorType}\" />" + this.NL + "          <table>" + this.NL + "            <tr align=\"center\">" + this.NL + "              <th>id: <y:search fieldType=\"I\" fieldList=\"id\" matchType=\"EQ\" />" + this.NL + "                <input type=\"button\" value=\"搜索\" class=\"submit\" onclick=\"search()\"/>" + this.NL + "                <a href=\"${ctx}/";
  protected final String TEXT_5 = "/";
  protected final String TEXT_6 = "/new\">添加新";
  protected final String TEXT_7 = "</a>" + this.NL + "              </th>" + this.NL + "            </tr>" + this.NL + "          </table>" + this.NL + "        </form>" + this.NL + "      </div>" + this.NL + "      <div class=\"area\">   " + this.NL + "          <table  class=\"listtable\">" + this.NL + "            <tr class=\"line\">";
  protected final String TEXT_8 = this.NL + "\t\t\t\t<th><a href=\"javascript:sort('";
  protected final String TEXT_9 = "')\">";
  protected final String TEXT_10 = "</a></th>" + this.NL + "\t\t\t";
  protected final String TEXT_11 = this.NL + "\t\t\t\t<th>插入时间</th>" + this.NL + "              \t<th>修改时间</th>" + this.NL + "              \t<th>操作</th>" + this.NL + "            </tr>" + this.NL + "            <c:forEach var=\"entity\" items=\"${pageData.result}\">" + this.NL + "              <tr <c:if test=\"${!entity.visible}\">bgcolor=\"#AFEEEE\"</c:if>>" + this.NL + "              \t";
  protected final String TEXT_12 = this.NL + "\t\t\t\t\t<td>${entity.";
  protected final String TEXT_13 = "}</td>" + this.NL + "\t\t\t\t";
  protected final String TEXT_14 = this.NL + "\t\t\t\t\t<td>${entity.insertTime}</td>" + this.NL + "                \t<td>${entity.lastUpdateTime}</td>" + this.NL + "                <td>" + this.NL + "                \t<a href=\"${ctx}/";
  protected final String TEXT_15 = "/";
  protected final String TEXT_16 = "/edit/${entity.id}\">修改</a> |" + this.NL + "                \t<a href=\"${ctx}/";
  protected final String TEXT_17 = "/";
  protected final String TEXT_18 = "/delete/${entity.id}\" onClick=\"return confirm('删除后无法恢复,确定要删除吗?');\">刪除</a> |" + this.NL + "                  <c:choose>" + this.NL + "                    <c:when test=\"${entity.visible}\"> " + this.NL + "                    \t显示 |  <a href=\"${ctx}/";
  protected final String TEXT_19 = "/";
  protected final String TEXT_20 = "/unVisible/${entity.id}\">不显示</a>" + this.NL + "                    </c:when>" + this.NL + "                    <c:otherwise> " + this.NL + "                    \t<a href=\"${ctx}/";
  protected final String TEXT_21 = "/";
  protected final String TEXT_22 = "/visible/${entity.id}\">显示</a> | 不显示 " + this.NL + "                    </c:otherwise>" + this.NL + "                  </c:choose>" + this.NL + "                </td>" + this.NL + "              </tr>" + this.NL + "            </c:forEach>" + this.NL + "          </table>" + this.NL + "                           第${pageData.pagination.pageNo}页,共${pageData.pagination.totalPage}页 <a href=\"javascript:jumpPage(1)\">首页</a>" + this.NL + "          <c:if test=\"${pageData.pagination.hasPrevPage}\"> <a href=\"javascript:jumpPage(${pageData.pagination.prevPage})\">上一页</a> </c:if>" + this.NL + "          <c:if test=\"${pageData.pagination.hasNextPage}\"> <a href=\"javascript:jumpPage(${pageData.pagination.nextPage})\">下一页</a> </c:if>" + this.NL + "          <a href=\"javascript:jumpPage(${pageData.pagination.totalPage})\">末页</a>" + this.NL + "        " + this.NL + "        <!-- 分页显示演示结束 -->" + this.NL + "      </div>" + this.NL + "    </div>" + this.NL + "  </div>" + this.NL + "  " + this.NL + "  <!-- footer -->" + this.NL + "  <%@ include file=\"/WEB-INF/jsp/commons/footer.jsp\"%>" + this.NL + "  " + this.NL + "</div>" + this.NL + "</body>" + this.NL + "</html>";
  protected final String TEXT_23 = this.NL;

  public static synchronized JspListTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    JspListTemplate result = new JspListTemplate();
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

//    args.getPackageNameInfo().getEntityPackageName();
//    args.getPackageNameInfo().getDaoPackageName();
//    args.getPackageNameInfo().getServicPackageName();
//    args.getPackageNameInfo().getActionPackageName();
//
//    args.getClassNameInfo().getEntityClassName();
//    args.getClassNameInfo().getDaoClassName();
//    args.getClassNameInfo().getServicClassName();
//    args.getClassNameInfo().getActionClassName();

    String entityObjectName = "";//args.getClassNameInfo().getEntityObjectName();
//    args.getClassNameInfo().getDaoObjectName();
//    args.getClassNameInfo().getServicObjectName();
//    args.getClassNameInfo().getActionObjectName();

    stringBuffer.append(this.TEXT_1);
    stringBuffer.append(entityObjectName);
    stringBuffer.append(this.TEXT_2);
    stringBuffer.append(modeName);
    stringBuffer.append("/");
    stringBuffer.append(entityObjectName);
    stringBuffer.append(this.TEXT_4);
    stringBuffer.append(modeName);
    stringBuffer.append("/");
    stringBuffer.append(entityObjectName);
    stringBuffer.append("/new\">添加新");
    stringBuffer.append(entityObjectName);
    stringBuffer.append(this.TEXT_7);
    for (Iterator iterator = entityFieldInfoList.iterator(); iterator.hasNext();) {
		EntityFieldInfo entityFieldInfo = (EntityFieldInfo) iterator.next();
		List fieldNames = entityFieldInfo.getFieldNames();
		for (Iterator iterator2 = fieldNames.iterator(); iterator2.hasNext(); stringBuffer.append(TEXT_10)) {
			String fieldName = (String) iterator2.next();
			stringBuffer.append(TEXT_8);
			stringBuffer.append(fieldName);
			stringBuffer.append("')\">");
			stringBuffer.append(fieldName);
		}

	}

    stringBuffer.append(this.TEXT_11);

    for (Iterator iterator1 = entityFieldInfoList.iterator(); iterator1.hasNext();) {
		EntityFieldInfo entityFieldInfo = (EntityFieldInfo) iterator1.next();
		List fieldNames = entityFieldInfo.getFieldNames();
		for (Iterator iterator3 = fieldNames.iterator(); iterator3.hasNext(); stringBuffer.append(TEXT_13)) {
			String fieldName = (String) iterator3.next();
			stringBuffer.append(TEXT_12);
			stringBuffer.append(fieldName);
		}

	}

    stringBuffer.append(this.TEXT_14);
    stringBuffer.append(modeName);
    stringBuffer.append("/");
    stringBuffer.append(entityObjectName);
    stringBuffer.append(this.TEXT_16);
    stringBuffer.append(modeName);
    stringBuffer.append("/");
    stringBuffer.append(entityObjectName);
    stringBuffer.append(this.TEXT_18);
    stringBuffer.append(modeName);
    stringBuffer.append("/");
    stringBuffer.append(entityObjectName);
    stringBuffer.append(this.TEXT_20);
    stringBuffer.append(modeName);
    stringBuffer.append("/");
    stringBuffer.append(entityObjectName);
    stringBuffer.append(this.TEXT_22);
    stringBuffer.append(this.TEXT_23);
    return stringBuffer.toString();
  }
}
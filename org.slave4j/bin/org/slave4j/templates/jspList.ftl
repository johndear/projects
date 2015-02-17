<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${entityObjectName}列表</title>
<%@ include file="/commons/meta.jsp"%>
</head>
<body>
<div id="container" class="container_24">
  <!-- header -->
  <%@ include file="/commons/header.jsp"%>
  
  <!-- content -->
  <div id="content" class="container_24">
    <div class="container_24">
      <div class="area2">
        <!-- 分页显示演示开始 -->
        <form id="listForm" action="${ctx}/${modeName}/${entityObjectName}/list" method="get">
          <input type="hidden" name="pageNo" id="pageNo" value="${pageNo}" />
          <input type="hidden" name="fieldName" id="fieldName" value="${fieldName}" />
          <input type="hidden" name="compositorType" id="compositorType" value="${compositorType}" />
          <table>
            <tr align="center">
              <th>id: <y:search fieldType="I" fieldList="id" matchType="EQ" />
                <input type="button" value="搜索" class="submit" onclick="search()"/>
                <a href="${ctx}/${modeName}/${entityObjectName}/new">添加新${entityObjectName}</a>
              </th>
            </tr>
          </table>
        </form>
      </div>
      <div class="area">   
          <table  class="listtable">
            <tr class="line">
            	<!-- foreach -->
				${thHtml}
				<th>插入时间</th>
              	<th>修改时间</th>
              	<th>操作</th>
            </tr>
            <c:forEach var="entity" items="${result}">
              <tr <c:if test="${visible}">bgcolor="#AFEEEE"</c:if>>
              		${tdHtml}
					<td>${insertTime}</td>
                	<td>${lastUpdateTime}</td>
                <td>
                	<a href="${ctx}/${modeName}/${entityObjectName}/edit/${id}">修改</a> |
                	<a href="${ctx}/${modeName}/${entityObjectName}/delete/${id}" onClick="return confirm('删除后无法恢复,确定要删除吗?');">刪除</a> |
                  <c:choose>
                    <c:when test="${visible}"> 
                    	显示 |  <a href="${ctx}/${modeName}/${entityObjectName}/unVisible/${id}">不显示</a>
                    </c:when>
                    <c:otherwise> 
                    	<a href="${ctx}/${modeName}/${entityObjectName}/visible/${id}">显示</a> | 不显示 
                    </c:otherwise>
                  </c:choose>
                </td>
              </tr>
            </c:forEach>
          </table>
                           第${pageNo}页,共${totalPage}页 <a href="javascript:jumpPage(1)">首页</a>
          <c:if test="${hasPrevPage}"> <a href="javascript:jumpPage(${prevPage})">上一页</a> </c:if>
          <c:if test="${hasNextPage}"> <a href="javascript:jumpPage(${nextPage})">下一页</a> </c:if>
          <a href="javascript:jumpPage(${totalPage})">末页</a>
        
        <!-- 分页显示演示结束 -->
      </div>
    </div>
  </div>
  
  <!-- footer -->
  <%@ include file="/commons/footer.jsp"%>
  
</div>
</body>
</html>

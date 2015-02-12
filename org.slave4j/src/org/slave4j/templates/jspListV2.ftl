<%@page import="ibm.gdmcc.scm.base.utils.UserPerferenceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<head>
<title>日常请购</title>
<jsp:include page="/common/common.jsp"></jsp:include>
<script type="text/javascript" src="${ctx }/static/common/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx }/static/lib/requirement/dict.js"></script>
<script type="text/javascript" src="${ctx }/static/lib/requirement/purchaseorder_list.js"></script>
</head>
<body>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12">

					<!---基本信息--->
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"><i class="icon-th"></i></span>
							<h5>查询栏</h5>
						</div>
						<div class="widget-content-scroll">
							<form>
								<table class="input_table">
									<tbody>
										<tr>
											<td class="input_tt">请购单主题：</td>
											<td class="w30">
												<input id="planTopic"  type="text">
											</td>
											<td class="input_tt">请购单编号：</td>
											<td class="w30">
												<input id="planNo"  type="text" >
											</td>
										</tr>
										<tr>
											<td class="input_tt">项目属性：</td>
											<td colspan="">
												<select id="projectAttr" name="projectAttr">
													<option value="">请选择</option>
													<option value="1">成本类</option>
													<option value="2">投资类</option>
												</select>
											</td>
											<td class="input_tt">状态：</td>
											<td class="center">
												<div id="status"></div>
											</td>
										</tr>
										<tr>
											<td class="input_tt">创建日期从：</td>
											<td>
												<input id="startTime" type="text" >
											</td>
											<td class="input_tt">到：</td>
											<td class="selectTask">
												<input id="endTime" type="text" >
											</td>
										</tr>
										<tr>
											<td colspan="4" style="text-align:center;" >
												<input class="btn btn-info btn-mini"  type="button" value="查询" onclick="search()" />
												<button class="btn btn-info btn-mini" type='reset' onclick="resetFormData()" >清空</button>
											</td>
										</tr>
									</tbody>
								</table>
							</form>
						</div>
					</div>

					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-th"></i>
							</span>
							<h5>列表</h5>
						</div>
						<div class="widget-content-scroll paddingTop38">
							<div class="btnDiv">
								<button id="btn_detail" class="btn btn-info btn-mini" onclick="showWindow('新建请购单','PurchasingRequisition/create')">新建</button>
								<button class="btn btn-info btn-mini" onclick="deleteRow()">删除</button>
								<button class="btn btn-info btn-mini">作废</button>
								<button class="btn btn-info btn-mini" onclick="exportData()">导出已选项</button>
							</div>
						</div>
						<div data-options="region:'center'">
							<table id="datagrid"></table>
						</div>

					</div>

				</div>
			</div>
		</div>
		
</body>
</html>
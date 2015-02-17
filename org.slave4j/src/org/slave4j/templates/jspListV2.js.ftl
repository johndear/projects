(function($){
   
   function initGrid(){
	  	$('#datagrid').datagrid({
				title:'列表', 
		        height: 'auto',  
		        nowrap: false,  
		        striped: true,  
		        border: true,  
		        collapsible:false,//是否可折叠的  
		        fitColumns: true,//自动大小  
		        url: contextPath + 'PurchasingRequisition/myList',
		        remoteSort:false,   
		        idField:'id',  
		        singleSelect:false,//是否单选  
		        pagination:true,//分页控件  
		        pageSize: 10,//每页显示的记录条数，默认为10  
	            pageList: [5,10,15],//可以设置每页记录条数的列表  
		        rownumbers:true,//行号  
		        columns:[[
					{field:'id',checkbox:true},
					<#if columnDatas?size != 0>
						<#list columnDatas as list>
							<#if '${list.fieldName}' !='id' >
								{field:'${list.fieldName}',title:'${list.fieldDesc}',align:'center',sortable:true,
									formatter:function(value,row,index){
										return row.${list.fieldName};
									}
								},
							</#if>
						</#list>
					</#if>
				]]
		});  
	  	
	  	DataGridUtil.bindReload('datagrid',200,function(){
			return {};
	  	});
	}

   function doAjax(url,type,params,callback){
       $.ajax({
           	 type: type,
           	 url: url,
	   		 data: params,
	   		 success: callback
	   	});
   }
   
    /** examples: 
	   	doAjax('','GET',{},function(msg){
	       	alert('result: '+msg);
	 	});
 	*/
 });

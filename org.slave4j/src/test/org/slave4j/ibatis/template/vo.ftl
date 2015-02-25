package ${packageName};


public class ${className}Vo {

	<#if columnDatas?size != 0>
		<#list columnDatas as list>
		   	private String ${list.columnName};
		</#list>
	</#if>
	
	<#if columnDatas?size != 0>
		<#list columnDatas as list>
		   	private String get${list.columnName?cap_first}(){
		   		return this.${list.columnName};
		   	}
		   	
		   	private void set${list.columnName?cap_first}(String ${list.columnName}){
		   		this.${list.columnName} = ${list.columnName};
		   	}
		</#list>
	</#if>
	
	
}
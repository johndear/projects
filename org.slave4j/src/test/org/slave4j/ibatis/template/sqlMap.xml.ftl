<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE sqlMap PUBLIC "-//ibatis.apache.org//DTD SQL Map 2.0//EN" "http://ibatis.apache.org/dtd/sql-map-2.dtd" >
<sqlMap namespace="${tableName}" >

  <resultMap id="BaseResultMap" class="${fullClassName}" >
  	<#if columnDatas?size != 0>
		<#list columnDatas as list>
		   	<result column="${list.columnName}" property="${list.columnName}" jdbcType="${list.columnType}" />
		</#list>
	</#if>
  </resultMap>
  
  <select id="selectAll" resultMap="BaseResultMap" parameterClass="${fullClassName}" >
    select
    <#if columnDatas?size != 0>
		<#list columnDatas as list>
			${list.columnName},
		</#list>
	</#if>
	'0' as row 
    from ${tableName}
  </select>
  
</sqlMap>
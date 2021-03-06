package com.components.common.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.components.common.metatype.Dto;

/**
 * 数据读取�?br>
 * 基于iBatis实现,只有query权限,提供在Action中使�? * 
 * @author XiongChun
 * @since 2009-07-23
 */
public interface Reader {

	/**
	 * 查询�?��记录
	 * 
	 * @param SQL语句ID�?	 * @param parameterObject
	 *            查询条件对象(map javaBean)
	 */
	public Object queryForObject(String statementName, Object parameterObject);

	/**
	 * 查询�?��记录
	 * 
	 * @param SQL语句ID�?	 */
	public Object queryForObject(String statementName);

	/**
	 * 查询记录集合
	 * 
	 * @param SQL语句ID�?	 * @param parameterObject
	 *            查询条件对象(map javaBean)
	 */
	public List queryForList(String statementName, Object parameterObject);

	/**
	 * 查询记录集合
	 * 
	 * @param SQL语句ID�?	 */
	public List queryForList(String statementName);

	/**
	 * 按分页查�?	 * 
	 * @param SQL语句ID�?	 * @param parameterObject
	 *            查询条件对象(map javaBean)
	 */
	public List queryForPage(String statementName, Dto qDto)
			throws SQLException;

	/**
	 * 获取Connection对象<br>
	 * 说明：虽然向Dao消费端暴露了获取Connection对象的方法但不建议直接获取Connection对象进行JDBC操作
	 * 
	 * @return 返回Connection对象
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException;

	/**
	 * 获取DataSource对象<br>
	 * 说明：虽然向Dao消费端暴露了获取DataSource对象的方法但不建议直接获取DataSource对象进行JDBC操作
	 * 
	 * @return 返回DataSource对象
	 * @throws SQLException
	 */
	public DataSource getDataSourceFromSqlMap() throws SQLException;
}

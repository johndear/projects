package com.components.common.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.components.common.exception.PrcException;
import com.components.common.metatype.Dto;

/**
 * 数据访问接口(原生)<br>
 * 基于iBatis实现,支持自定义的数据操作
 * 
 * @author XiongChun
 * @since 2009-07-23
 */
public interface Dao {
	
	/**
	 * 插入�?��记录
	 * @param SQL语句ID�?	 * @param parameterObject 要插入的对象(map javaBean)
	 */
	public void insert(String statementName, Object parameterObject);
	
	/**
	 * 插入�?��记录
	 * @param SQL语句ID�?	 */
	public void insert(String statementName);
	
	/**
	 * 查询�?��记录
	 * @param SQL语句ID�?	 * @param parameterObject 查询条件对象(map javaBean)
	 */
	public Object queryForObject(String statementName, Object parameterObject);
	
	/**
	 * 查询�?��记录
	 * @param SQL语句ID�?	 */
	public Object queryForObject(String statementName);
	
	/**
	 * 查询记录集合
	 * @param SQL语句ID�?	 * @param parameterObject 查询条件对象(map javaBean)
	 */
	public List queryForList(String statementName, Object parameterObject);
	
	/**
	 * 查询记录集合
	 * @param SQL语句ID�?	 */
	public List queryForList(String statementName);
	
	/**
	 * 按分页查�?	 * 
	 * @param SQL语句ID�?	 * @param parameterObject
	 *            查询条件对象(map javaBean)
	 */
	public List queryForPage(String statementName, Dto qDto) throws SQLException;
	
	/**
	 * 更新记录
	 * @param SQL语句ID�?	 * @param parameterObject 更新对象(map javaBean)
	 */
	public int update(String statementName, Object parameterObject);
	
	/**
	 * 更新记录
	 * @param SQL语句ID�?	 */
	public int update(String statementName);
	
	/**
	 * 删除记录
	 * @param SQL语句ID�?	 * @param parameterObject 更新对象(map javaBean)
	 */
	public int delete(String statementName, Object parameterObject);
	
	/**
	 * 删除记录
	 * @param SQL语句ID�?	 */
	public int delete(String statementName);
	
	/**
	 * 调用存储过程<br>
	 * 存储过程成功返回标志缺省：appCode=1
	 * 
	 * @param prcName 存储过程ID�?	 * @param parameterObject 参数对象(入参、出�?
	 * @throws PrcException 存储过程调用异常
	 */
	public void callPrc(String prcName, Dto prcDto) throws PrcException;
	
	/**
	 * 调用存储过程<br>
	 * 存储过程成功返回标志自定义：appCode=successFlag(自定义的传入变量)
	 * 
	 * @param prcName 存储过程ID�?	 * @param parameterObject 参数对象(入参、出�?
	 * @param prcName 存储过程调用成功返回的成功代码�?
	 * @throws PrcException 存储过程调用异常
	 */
	public void callPrc(String prcName, Dto prcDto, String successFlag) throws PrcException;
	
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
	
	/**
	 * 获取SqlMapClientTemplate对象<br>
	 * 
	 * @return 返回SqlMapClientTemplate对象
	 */
	public SqlMapClientTemplate getSqlMapClientTpl();
	
}

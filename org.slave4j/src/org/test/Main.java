package org.test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slave4j.templates.JspListTemplate;
import org.slave4j.templates.util.FreeMarkertUtil;

public class Main {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		CreateBean bean = new CreateBean();
		bean.setMysqlInfo("jdbc:mysql://localhost:3306/jeecgmybatis?useUnicode=true&characterEncoding=UTF-8", "root", "root");
		List<ColumnData> list = bean.getColumnDatas("sys_user");
		// TODO Auto-generated method stub
		JspListTemplate jsp = new JspListTemplate();
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put("columnDatas", list);
		
		String jspPage= FreeMarkertUtil.generate("js.ftl", dataModel);
		System.out.println(jspPage);
	}

}

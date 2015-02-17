package test.org.slave4j;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slave4j.templates.FreeMarkertUtil;
import org.slave4j.util.JavaTemplateGenerator;

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
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put("ctx", "${ctx }");
		dataModel.put("columnDatas", list);
		dataModel.put("abc", list);
		dataModel.put("xxx", list);
		
		String jspPage= FreeMarkertUtil.generate("jspListV2.ftl", dataModel);
		System.out.println(jspPage);
	}

}

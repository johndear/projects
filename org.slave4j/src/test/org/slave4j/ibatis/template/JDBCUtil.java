package test.org.slave4j.ibatis.template;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slave4j.templates.FreeMarkertUtil;

import test.org.slave4j.ColumnData;
import test.org.slave4j.TableConvert;

public class JDBCUtil {
	private Connection connection = null;
	private static String url;
	private static String username;
	private static String password;

	public JDBCUtil(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	private static void write2File(List<ColumnData> list, String folder, String tableName) throws IOException {
		String packageName = folder.substring(folder.indexOf("src")+4,folder.length()-1).replace("/", ".");
		Map<String, Object> dataModel = new HashMap<String, Object>();
		dataModel.put("columnDatas", list);
		dataModel.put("tableName", tableName);
		dataModel.put("packageName", packageName);
		dataModel.put("fullClassName", packageName + "." + toInitialUpperCase(tableName));
		dataModel.put("className", toInitialUpperCase(tableName));
		dataModel.put("instanceName", toHumpUpperCase(tableName));
		String vo = FreeMarkertUtil.generate("vo.ftl", dataModel);
		String mapper = FreeMarkertUtil.generate("sqlMap.xml.ftl", dataModel);
		String dao = FreeMarkertUtil.generate("idao.ftl", dataModel);
		String service = FreeMarkertUtil.generate("iservice.ftl", dataModel);
		String serviceImpl = FreeMarkertUtil.generate("service.impl.ftl", dataModel);
		String controller = FreeMarkertUtil.generate("controller.ftl", dataModel);
		
		FileUtils.writeByteArrayToFile(new File(folder + toHumpUpperCase(tableName) + ".xml"), mapper.getBytes());
		FileUtils.writeByteArrayToFile(new File(folder + "I" + toInitialUpperCase(tableName) + "Dao" + ".java"), dao.getBytes());
		FileUtils.writeByteArrayToFile(new File(folder + "I" + toInitialUpperCase(tableName) + "Service" + ".java"), service.getBytes());
		FileUtils.writeByteArrayToFile(new File(folder + toInitialUpperCase(tableName) + "ServiceImpl.java"), serviceImpl.getBytes());
		FileUtils.writeByteArrayToFile(new File(folder + toInitialUpperCase(tableName) + "Controller.java"), controller.getBytes());
		FileUtils.writeByteArrayToFile(new File(folder + toInitialUpperCase(tableName) + "Vo.java"), vo.getBytes());
	}
	
	public static String toInitialUpperCase(String str){
		StringBuffer sb = new StringBuffer();
		String arr[] = str.split("_");
		for (String string : arr) {
			String part = string.substring(0, 1).toUpperCase() + string.substring(1);
			sb.append(part);
		}
		return sb.toString();
	}
	
	public static String toHumpUpperCase(String str){
		str = toInitialUpperCase(str);
		return str.substring(0,1).toLowerCase() + str.substring(1);
	}
	
	public List<ColumnData> getColumnDatas(String tableName)
			throws SQLException {
		String SQLColumns = "select column_name ,data_type,column_comment,0,0,character_maximum_length,is_nullable nullable from information_schema.columns where table_name =  '"
				+ tableName + "'";

		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(SQLColumns);
		List columnList = new ArrayList();
		ResultSet rs = ps.executeQuery();
		StringBuffer str = new StringBuffer();
		StringBuffer getset = new StringBuffer();
		while (rs.next()) {
			String name = rs.getString(1).toLowerCase();
			String type = rs.getString(2);
			String comment = rs.getString(3);
			String precision = rs.getString(4);
			String scale = rs.getString(5);
			String charmaxLength = rs.getString(6) == null ? "" : rs
					.getString(6);
			String nullable = TableConvert.getNullAble(rs.getString(7));
			type = getType(type, precision, scale);

			ColumnData cd = new ColumnData();
			cd.setColumnName(toHumpUpperCase(name));
			cd.setDataType(type);
			cd.setColumnType(rs.getString(2));
			cd.setColumnComment(comment);
			cd.setPrecision(precision);
			cd.setScale(scale);
			cd.setCharmaxLength(charmaxLength);
			cd.setNullable(nullable);
			columnList.add(cd);
		}
		rs.close();
		ps.close();
		con.close();
		return columnList;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public String getType(String dataType, String precision, String scale) {
		dataType = dataType.toLowerCase();
		if (dataType.contains("char"))
			dataType = "java.lang.String";
		else if (dataType.contains("int"))
			dataType = "java.lang.Integer";
		else if (dataType.contains("float"))
			dataType = "java.lang.Float";
		else if (dataType.contains("double"))
			dataType = "java.lang.Double";
		else if (dataType.contains("number")) {
			if ((StringUtils.isNotBlank(scale))
					&& (Integer.parseInt(scale) > 0))
				dataType = "java.math.BigDecimal";
			else if ((StringUtils.isNotBlank(precision))
					&& (Integer.parseInt(precision) > 6))
				dataType = "java.lang.Long";
			else
				dataType = "java.lang.Integer";
		} else if (dataType.contains("decimal"))
			dataType = "BigDecimal";
		else if (dataType.contains("date"))
			dataType = "java.util.Date";
		else if (dataType.contains("time"))
			dataType = "java.sql.Timestamp";
		else if (dataType.contains("clob"))
			dataType = "java.sql.Clob";
		else {
			dataType = "java.lang.Object";
		}
		return dataType;
	}
	

	public static void main(String[] args) throws SQLException, IOException {
		JDBCUtil jdbc = new JDBCUtil("jdbc:mysql://localhost:3306/jeecgmybatis?useUnicode=true&characterEncoding=UTF-8","root", "root");
		String tables[] = new String[]{"sys_menu", "sys_user"};
		for (String tableName : tables) {
			List<ColumnData> list = jdbc.getColumnDatas(tableName);
//			// debug
//			for (ColumnData columnData : list) {
//				System.out.println(columnData.getColumnName() + "," + columnData.getColumnType() + "," + columnData.getColumnComment());
//			}
			String folder = "E:/git/projects/org.slave4j/src/test/org/slave4j/ibatis/instance/";
			write2File(list, folder, tableName);
		}
	}

}

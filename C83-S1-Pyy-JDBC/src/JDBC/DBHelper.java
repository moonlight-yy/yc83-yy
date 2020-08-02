package JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DBHelper {
	/*
	 * 加载驱动
	 * 静态块 特点 只会在该类被记载到虚拟机是，执行一次
	 * 静态不允许抛出 编译期异常
	 * */
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			//注意：异常转型==》异常链==》原因异常
			throw new RuntimeException("数据库驱动加载失败!",e);
			}
	}
	
	/**
	 * 获取连接
	 * @throws SQLException 
	 */
	public Connection getConnection() throws SQLException {
		String url="jdbc:oracle:thin:@localhost:1521:orcl";
		String user="scott";
		String password="a";
		return DriverManager.getConnection(url,user,password);
		
	}
   /**
    * sql
    * args   Object...args可变参数数组
 * @throws SQLException 
    * 
    */
	public int update(String sql, Object...args) throws SQLException {
		Connection conn=getConnection();
		//创建语句
		PreparedStatement ps=conn.prepareStatement(sql);
		//执行语句
		//设置参数
		for (int i = 0; i < args.length; i++) {
			ps.setObject(i+1, args[i]);
		}
		System.out.println("SQL:"+sql);
		System.out.println("参数"+Arrays.toString(args));
		int rows=ps.executeUpdate();
		//关闭连接
		conn.close();
		return rows;
		
	}
	
	public List<Map<String,Object>> query(String sql,Object...args) throws SQLException{
		Connection conn=getConnection();
		//创建语句
		PreparedStatement ps=conn.prepareStatement(sql);
		//执行语句 设置参数
		for (int i = 0; i < args.length; i++) {
			ps.setObject(i+1, args[i]);
		}
		System.out.println("SQL:"+sql);
		System.out.println("参数"+Arrays.toString(args));
		ResultSet rs=ps.executeQuery();
		List<Map<String,Object>> ret=new ArrayList<>();
		ResultSetMetaData rsmd=rs.getMetaData();
		//获取列数
		int columnCount=rsmd.getColumnCount();
		while(rs.next()) {
			//Map无序键不重复==>linkedHashMap  有序键不重复
			Map<String,Object> row=new LinkedHashMap<>();
			for (int i = 0; i <columnCount; i++) {
				String columnName=rsmd.getColumnName(i+1);
				Object value=rs.getObject(i+1);
				row.put(columnName,value);
			}
			ret.add(row);
		}
		//关闭连接
		conn.close();
		return ret;
		
		
	}
	/**
	 * 查询一条记录
	 * @param args
	 * @throws SQLException
	 */
	public Map<String,Object> queryOne(String sql,Object...args) throws SQLException{
		List<Map<String,Object>> list=query(sql,args);
		if(list.size()==0) {
			return null;
		}else if(list.size()==1) {
			return list.get(0);
		}else {
			throw new SQLException("查询结果大于一");
		}
		
	}
	
	
	/**
	 * 用于查询某个sql结果集的数量
	 * @param args
	 * @throws SQLException
	 */
	public int count(String sql,Object...args) throws SQLException {
		sql="select * cnt from ("+sql+")";
		List<Map<String,Object>> list=query(sql,args);
		Object num=list.get(0).get("CNT");
		return Integer.valueOf(num.toString());
	}
	
	
	
	
	public static void main(String[] args) throws SQLException {
		DBHelper dbh=new DBHelper();
//		String sql="update dept set loc=? where deptno=?";
//		dbh.update(sql, "肥宅宅",20);
		
		List<Map<String,Object>> list;
		list=dbh.query("select * from dept");
		for (Map<String,Object>row:list) {
			System.out.println(row);
		}
	}
}

































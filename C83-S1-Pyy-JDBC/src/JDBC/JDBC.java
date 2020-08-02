package JDBC;

import java.sql.*;


public class JDBC {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//注册驱动
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//获取连接 DriverManager 驱动管理器
		String url="jdbc:oracle:thin:@localhost:1521:orcl";
		String user="scott";
		String password="a";
		Connection conn=DriverManager.getConnection(url,user,password);
		//System.out.println(conn);
		//3.创建语句
		Statement stat=conn.createStatement();
		//执行语句
		String sql="update dept set loc ='学公路' where deptno = 40 ";
		int rows =stat.executeUpdate(sql);
		
		System.out.println("更新了"+rows+"记录");
		
		//关闭连接
		conn.close();
		
	}
	

}

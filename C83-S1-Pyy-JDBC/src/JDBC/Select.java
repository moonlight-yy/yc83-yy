package JDBC;

import java.sql.*;

public class Select {
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
	  /*
	   * 注入攻击
	   */
	  System.out.println("===================");
	  select("' or '1'='1");
	  System.out.println("==================="); 
	  select("SALES");
	  System.out.println("===================");
	
}
   public static void select(String dname) throws ClassNotFoundException, SQLException {
	 //注册驱动
	 		Class.forName("oracle.jdbc.driver.OracleDriver");
	 		//获取连接 DriverManager 驱动管理器
	 		String url="jdbc:oracle:thin:@localhost:1521:orcl";
	 		String user="scott";
	 		String password="a";
	 		Connection conn=DriverManager.getConnection(url,user,password);
	 		//System.out.println(conn);
	 		
	 		String sql="select * from dept where dname=? ";
	 		//3.创建语句
	 		PreparedStatement ps=conn.prepareStatement(sql);
	 		//执行语句
	 		/*
	 		 * 设置查询参数，替换sql中的？（占位符）*/
	 	    ps.setString(1,dname);
	 	    ResultSet rs=ps.executeQuery();
	 	    
	 	    while(rs.next()) {
	 	    	System.out.print(rs.getInt(1));
	 	    	System.out.print("\t");
	 	    	System.out.print(rs.getString(2));
	 	    	System.out.print("\t");
	 	    	System.out.print(rs.getString("LOC"));
	 	    	System.out.println();
	 	    }	 		
	 		
	 		
	 		//关闭连接
	 	    rs.close();
	 	    ps.close();
	 		conn.close();
   }
   
}

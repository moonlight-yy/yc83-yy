package JDBC;

import java.sql.*;

public class Select {
  public static void main(String[] args) throws ClassNotFoundException, SQLException {
	  /*
	   * ע�빥��
	   */
	  System.out.println("===================");
	  select("' or '1'='1");
	  System.out.println("==================="); 
	  select("SALES");
	  System.out.println("===================");
	
}
   public static void select(String dname) throws ClassNotFoundException, SQLException {
	 //ע������
	 		Class.forName("oracle.jdbc.driver.OracleDriver");
	 		//��ȡ���� DriverManager ����������
	 		String url="jdbc:oracle:thin:@localhost:1521:orcl";
	 		String user="scott";
	 		String password="a";
	 		Connection conn=DriverManager.getConnection(url,user,password);
	 		//System.out.println(conn);
	 		
	 		String sql="select * from dept where dname=? ";
	 		//3.�������
	 		PreparedStatement ps=conn.prepareStatement(sql);
	 		//ִ�����
	 		/*
	 		 * ���ò�ѯ�������滻sql�еģ���ռλ����*/
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
	 		
	 		
	 		//�ر�����
	 	    rs.close();
	 	    ps.close();
	 		conn.close();
   }
   
}

package JDBC;

import java.sql.*;


public class JDBC {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//ע������
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//��ȡ���� DriverManager ����������
		String url="jdbc:oracle:thin:@localhost:1521:orcl";
		String user="scott";
		String password="a";
		Connection conn=DriverManager.getConnection(url,user,password);
		//System.out.println(conn);
		//3.�������
		Statement stat=conn.createStatement();
		//ִ�����
		String sql="update dept set loc ='ѧ��·' where deptno = 40 ";
		int rows =stat.executeUpdate(sql);
		
		System.out.println("������"+rows+"��¼");
		
		//�ر�����
		conn.close();
		
	}
	

}

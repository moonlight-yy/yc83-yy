package JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DBHelper {
	/*
	 * ��������
	 * ��̬�� �ص� ֻ���ڸ��౻���ص�������ǣ�ִ��һ��
	 * ��̬�������׳� �������쳣
	 * */
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			//ע�⣺�쳣ת��==���쳣��==��ԭ���쳣
			throw new RuntimeException("���ݿ���������ʧ��!",e);
			}
	}
	
	/**
	 * ��ȡ����
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
    * args   Object...args�ɱ��������
 * @throws SQLException 
    * 
    */
	public int update(String sql, Object...args) throws SQLException {
		Connection conn=getConnection();
		//�������
		PreparedStatement ps=conn.prepareStatement(sql);
		//ִ�����
		//���ò���
		for (int i = 0; i < args.length; i++) {
			ps.setObject(i+1, args[i]);
		}
		System.out.println("SQL:"+sql);
		System.out.println("����"+Arrays.toString(args));
		int rows=ps.executeUpdate();
		//�ر�����
		conn.close();
		return rows;
		
	}
	
	public List<Map<String,Object>> query(String sql,Object...args) throws SQLException{
		Connection conn=getConnection();
		//�������
		PreparedStatement ps=conn.prepareStatement(sql);
		//ִ����� ���ò���
		for (int i = 0; i < args.length; i++) {
			ps.setObject(i+1, args[i]);
		}
		System.out.println("SQL:"+sql);
		System.out.println("����"+Arrays.toString(args));
		ResultSet rs=ps.executeQuery();
		List<Map<String,Object>> ret=new ArrayList<>();
		ResultSetMetaData rsmd=rs.getMetaData();
		//��ȡ����
		int columnCount=rsmd.getColumnCount();
		while(rs.next()) {
			//Map��������ظ�==>linkedHashMap  ��������ظ�
			Map<String,Object> row=new LinkedHashMap<>();
			for (int i = 0; i <columnCount; i++) {
				String columnName=rsmd.getColumnName(i+1);
				Object value=rs.getObject(i+1);
				row.put(columnName,value);
			}
			ret.add(row);
		}
		//�ر�����
		conn.close();
		return ret;
		
		
	}
	/**
	 * ��ѯһ����¼
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
			throw new SQLException("��ѯ�������һ");
		}
		
	}
	
	
	/**
	 * ���ڲ�ѯĳ��sql�����������
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
//		dbh.update(sql, "��լլ",20);
		
		List<Map<String,Object>> list;
		list=dbh.query("select * from dept");
		for (Map<String,Object>row:list) {
			System.out.println(row);
		}
	}
}

































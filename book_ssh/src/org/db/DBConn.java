package org.db;
import java.sql.*;
public class DBConn{
	public static Connection conn;			//Connection�������ӣ�
	//��ȡ���ݿ�����
	public static Connection getConn(){
		try {
			/**���ز�ע�� SQLServer 2008 �� JDBC ����*/
			Class.forName("com.mysql.jdbc.Driver");
			/**��д�����ַ�������������ȡ����*/
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_bookmanage","root","941013");
			return conn;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	//�ر�����
	public static void CloseConn(){
		try{
			conn.close();					//�ر�����
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

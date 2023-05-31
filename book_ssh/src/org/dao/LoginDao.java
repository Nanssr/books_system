package org.dao;
import java.sql.*;
import java.util.List;

import org.model.*;
import org.util.HibernateUtil;
import org.db.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LoginDao extends BaseDao{
	Connection conn;											//���ݿ����Ӷ���
	public Login checkLogin(String name, String password){		//��֤��¼�û���������
		Transaction transaction =null;
	    String hql ="";
	    try {      
//	    	Session session = HibernateUtil.getSessionFactory().openSession(); 
	    	Session session = getSession(); 
	        transaction =session.beginTransaction();       
	        hql = "from Login where name=? and password =?";      
	        Query query = session.createQuery(hql);    
	        query.setParameter(0,name);
	        query.setParameter(1,password);
	       
	        List<Login> list =query.list();
	        System.out.println(list);    
	        transaction.commit();
	       
	        if(list.size()>0){
	            return list.get(0);
	        }else {
	            return  null;
	        }
	    }catch (Exception e){
	    	if(transaction!=null) transaction.rollback();
	    	e.printStackTrace();
	    }finally {
	       
	        if(transaction!=null){
	            transaction=null;
	        }
	    }
	   return null;
	}
		
		
//		try{
//			conn = DBConn.getConn();							//��ȡ���Ӷ���
//			PreparedStatement pstmt = conn.prepareStatement("select * from login where name=? " + "and password=?");
//			pstmt.setString(1, name);							//���� SQL ������1���û�����
//			pstmt.setString(2, password);						//���� SQL ������2�����룩
//			ResultSet rs = pstmt.executeQuery();				//ִ�в�ѯ�����ؽ����
//			if(rs.next()){										//���ؽ����Ϊ�գ���ʾ�д��û���Ϣ
//				Login login = new Login();						//ͨ��JavaBean���󱣴�ֵ
//				login.setId(rs.getInt(1));
//				login.setName(rs.getString(2));
//				login.setPassword(rs.getString(3));
//				login.setRole(rs.getInt(4));
//				return login;									//�����Ѿ���ֵ��JavaBean����
//			}
//			return null;										//�޴��û�����֤ʧ�ܣ�����null
//		}catch(Exception e){
//			e.printStackTrace();
//			return null;
//		}finally{
//			DBConn.CloseConn();									//�ر�����
//		}
//	}
	
	public Login checkLogin2(String name){		//��֤��¼�û���������
		try{
			conn = DBConn.getConn();							//��ȡ���Ӷ���
			PreparedStatement pstmt = conn.prepareStatement("select * from login where name=?");
			pstmt.setString(1, name);							//���� SQL ������1���û�����

			ResultSet rs = pstmt.executeQuery();				//ִ�в�ѯ�����ؽ����
			if(rs.next()){										//���ؽ����Ϊ�գ���ʾ�д��û���Ϣ
				Login login = new Login();						//ͨ��JavaBean���󱣴�ֵ
				login.setId(rs.getInt(1));
				login.setName(rs.getString(2));
				login.setPassword(rs.getString(3));
				login.setRole(rs.getInt(4));
				return login;									//�����Ѿ���ֵ��JavaBean����
			}
			return null;										//�޴��û�����֤ʧ�ܣ�����null
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			DBConn.CloseConn();									//�ر�����
		}
	}
}

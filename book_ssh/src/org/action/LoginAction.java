package org.action;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.dao.LoginDao;
import org.model.Login;
import org.service.LoginService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
public class LoginAction extends ActionSupport{
	private Login login;
	private String message;
	private LoginService loginService=null;
	
	public void init(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	    loginService = (LoginService) ctx.getBean("loginService");
	}
	
	//�����û������ execute ����
	public String execute() throws Exception{
		init();
		//����Ϊ��Ŀ�����ݵĽӿڣ�DAO�ӿڣ������ڴ������������ݿ���һЩ����
//		LoginDao loginDao = new LoginDao();
		Login l = loginService.checkLogin(login.getName(), login.getPassword());

			if(l.getName().equals(login.getName())&&l.getPassword().equals(login.getPassword())){

				Map session = ActionContext.getContext().getSession();	//��ûỰ���������浱ǰ��¼�û�����Ϣ
				 ServletActionContext.getRequest().getSession().setAttribute("User", login.getName());
	             ServletActionContext.getRequest().getSession().setAttribute("Pwd", login.getPassword());
				session.put("login", l);								//�ѻ�ȡ�Ķ��󱣴��� Session ��
				//return SUCCESS;											//��֤�ɹ������ַ���SUCCESS����ʱ Session ���Ѿ����û�����
				//��¼�ɹ����жϽ�ɫΪ����Ա����ѧ����true��ʾ����Ա��false��ʾѧ��
				if(l.getRole()==1){
					return "admin";			//����Ա��ݵ�¼
				}else if(l.getRole()!=1){
					return "student";		//ѧ����ݵ�¼
				}else{
					return "student";
				}
			}else{
			return ERROR;											//��֤ʧ�ܷ����ַ���ERROR
		}
	}
	//��֤��¼���������Ƿ�Ϊ��
	public void validate() {
		if(login.getName()==null||login.getName().equals("")){
			this.addFieldError("name", "�û�������Ϊ�գ�");
		}else if(login.getPassword()==null||login.getPassword().equals("")){
			this.addFieldError("password", "���벻��Ϊ�գ�");
		}
	}

	//���� login �� get/set ����
	public Login getLogin() {
		return login;
	}
	public void setLogin(Login login) {
		this.login = login;
	}
	
	//���� message �� get/set ����
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
}

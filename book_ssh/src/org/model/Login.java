package org.model;
public class Login {
	//����
	private Integer id;			//�û�ID
	private String name;		//�û���
	private String password;	//����
	private int role;		//��ɫ
	
	//���� id �� get/set ����
	public Integer getId(){
		return this.id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	//���� name �� get/set ����
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}

	//���� password �� get/set ����
	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		this.password = password;
	}
	
	//���� role �� get/set ����
	public int getRole(){
		return this.role;
	}
	public void setRole(int role){
		this.role = role;
	}
}

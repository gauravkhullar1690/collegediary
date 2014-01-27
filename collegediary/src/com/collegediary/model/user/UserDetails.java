package com.collegediary.model.user;

import java.util.Date;

public class UserDetails {
	private Long id;
	private MasterUser masterUser;
	private String firstName;
	private String lastName;
	private Date dateofbirth;
	private String sex;
	private String userType;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MasterUser getMasterUser() {
		return masterUser;
	}
	public void setMasterUser(MasterUser masterUser) {
		this.masterUser = masterUser;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
}

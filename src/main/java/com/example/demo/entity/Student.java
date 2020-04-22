package com.example.demo.entity;

public class Student {

	private int std_id;
	private String std_password;
	private String std_name;
	private String std_gender;
	private String std_department;
	private String std_phone;
	private String std_mail;
	private String std_image;
	private String old_std_password;

	public int getStd_id() {
		return std_id;
	}

	public void setStd_id(int std_id) {
		this.std_id = std_id;
	}

	public String getStd_password() {
		return std_password;
	}

	public void setStd_password(String std_password) {
		this.std_password = std_password;
	}

	public String getStd_name() {
		return std_name;
	}

	public void setStd_name(String std_name) {
		this.std_name = std_name;
	}

	public String getStd_gender() {
		return std_gender;
	}

	public void setStd_gender(String std_gender) {
		this.std_gender = std_gender;
	}

	public String getStd_department() {
		return std_department;
	}

	public void setStd_department(String std_department) {
		this.std_department = std_department;
	}

	public String getStd_phone() {
		return std_phone;
	}

	public void setStd_phone(String std_phone) {
		this.std_phone = std_phone;
	}

	public String getStd_mail() {
		return std_mail;
	}

	public void setStd_mail(String std_mail) {
		this.std_mail = std_mail;
	}

	public String getStd_image() {
		return std_image;
	}

	public void setStd_image(String std_image) {
		this.std_image = std_image;
	}




	public String getOld_std_password() {
		return this.old_std_password;
	}

	public void setOld_std_password(String old_std_password) {
		this.old_std_password = old_std_password;
	}

}

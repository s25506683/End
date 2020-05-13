package com.example.demo.entity;

public class Teacher {

	private int teacher_id;
	private String teacher_password;
	private String teacher_name;
	private String teacher_gender;
	private String teacher_department;
	private String teacher_phone;
	private String teacher_mail;
	private String teacher_image;
	private String old_teacher_password;
	private String cs_id;
	private String std_id;

	public String getStd_id() {
		return this.std_id;
	}

	public void setStd_id(String std_id) {
		this.std_id = std_id;
	}

	
	public String getCs_id() {
		return this.cs_id;
	}

	public void setCs_id(String cs_id) {
		this.cs_id = cs_id;
	}

	public String getOld_teacher_password() {
		return this.old_teacher_password;
	}

	public void setOld_teacher_password(String old_teacher_password) {
		this.old_teacher_password = old_teacher_password;
	}

	public int getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getTeacher_password() {
		return teacher_password;
	}

	public void setTeacher_password(String teacher_password) {
		this.teacher_password = teacher_password;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public String getTeacher_gender() {
		return teacher_gender;
	}

	public void setTeacher_gender(String teacher_gender) {
		this.teacher_gender = teacher_gender;
	}

	public String getTeacher_department() {
		return teacher_department;
	}

	public void setTeacher_department(String teacher_department) {
		this.teacher_department = teacher_department;
	}

	public String getTeacher_phone() {
		return teacher_phone;
	}

	public void setTeacher_phone(String teacher_phone) {
		this.teacher_phone = teacher_phone;
	}

	public String getTeacher_mail() {
		return teacher_mail;
	}

	public void setTeacher_mail(String teacher_mail) {
		this.teacher_mail = teacher_mail;
	}

	public String getTeacher_image() {
		return teacher_image;
	}

	public void setTeacher_image(String teacher_image) {
		this.teacher_image = teacher_image;
	}

	

}

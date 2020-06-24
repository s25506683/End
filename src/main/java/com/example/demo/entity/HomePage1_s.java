package com.example.demo.entity;


public class HomePage1_s {
    private String cs_id;
    private String cs_name;
    private String std_name;
    private String teacher_name;
    private String cs_photo;
    private int std_id;
    private int teacher_id;
    private int std_count;

    //user_role value '0' is student, '1' is teacher.
    private int user_role;



    public String getCs_id() {
        return this.cs_id;
    }

    public void setCs_id(String cs_id) {
        this.cs_id = cs_id;
    }

    public String getCs_name() {
        return this.cs_name;
    }

    public void setCs_name(String cs_name) {
        this.cs_name = cs_name;
    }

    public String getStd_name() {
        return this.std_name;
    }

    public void setStd_name(String std_name) {
        this.std_name = std_name;
    }

    public String getTeacher_name() {
        return this.teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getCs_photo() {
        return this.cs_photo;
    }

    public void setCs_photo(String cs_photo) {
        this.cs_photo = cs_photo;
    }

    public int getStd_id() {
        return this.std_id;
    }

    public void setStd_id(int std_id) {
        this.std_id = std_id;
    }
    
    public int getTeacher_id() {
        return this.teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public int getStd_count() {
        return this.std_count;
    }

    public void setStd_count(int std_count) {
        this.std_count = std_count;
    }

	public int getUser_role() {
		return this.user_role;
	}

	public void setUser_role(int user_role) {
		this.user_role = user_role;
	}
    
    

    
}






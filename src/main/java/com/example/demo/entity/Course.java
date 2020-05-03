package com.example.demo.entity;

public class Course {

    private String cs_id;
    private String cs_name;
    private String cs_photo;
    private String cs_qrcode;

    private int std_id;
    private String std_name;
    private String std_department;



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

    public String getCs_photo() {
        return this.cs_photo;
    }

    public void setCs_photo(String cs_photo) {
        this.cs_photo = cs_photo;
    }

    public String getCs_qrcode() {
        return this.cs_qrcode;
    }

    public void setCs_qrcode(String cs_qrcode) {
        this.cs_qrcode = cs_qrcode;
    }

    public int getStd_id() {
        return this.std_id;
    }

    public void setStd_id(int std_id) {
        this.std_id = std_id;
    }

    public String getStd_name() {
        return this.std_name;
    }

    public void setStd_name(String std_name) {
        this.std_name = std_name;
    }

    public String getStd_department() {
        return this.std_department;
    }

    public void setStd_department(String std_department) {
        this.std_department = std_department;
    }

}
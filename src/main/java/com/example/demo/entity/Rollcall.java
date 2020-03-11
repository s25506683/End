package com.example.demo.entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;


public class Rollcall {
    //private int cs_id;
    private int std_id;
    private String std_name;
    private String std_department;
    private String tl_type_name;
    private String cs_id;
    private Date rc_endtime;
    private String rc_inputsoure;
    private int rc_scoring;




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

    public String getTl_type_name() {
        return this.tl_type_name;
    }

    public void setTl_type_name(String tl_type_name) {
        this.tl_type_name = tl_type_name;
    }
    
    public String getCs_id() {
        return this.cs_id;
    }

    public void setCs_id(String cs_id) {
        this.cs_id = cs_id;
    }

    public Date getRc_endtime() {
        return this.rc_endtime;
    }

    public void setRc_endtime(Date rc_endtime) {
        this.rc_endtime = rc_endtime;
    }
    
    public String getRc_inputsoure() {
        return this.rc_inputsoure;
    }

    public void setRc_inputsoure(String rc_inputsoure) {
        this.rc_inputsoure = rc_inputsoure;
    }

    public int getRc_scoring() {
        return this.rc_scoring;
    }

    public void setRc_scoring(int rc_scoring) {
        this.rc_scoring = rc_scoring;
    }


    

    
}






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
    private int rc_id;
    private String rc_name;
    private String rc_starttime;
    private String rc_endtime;
    private int rc_scoring;
    private String rc_inputsource;
    private int qrcode;
    private String record_time;

    private int present;
    private int absent;
    private int otherwise;


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

    public int getRc_id() {
        return this.rc_id;
    }

    public void setRc_id(int rc_id) {
        this.rc_id = rc_id;
    }

    public String getRc_name() {
        return this.rc_name;
    }

    public void setRc_name(String rc_name) {
        this.rc_name = rc_name;
    }

    public String getRc_starttime() {
        return this.rc_starttime;
    }

    public void setRc_starttime(String rc_starttime) {
        this.rc_starttime = rc_starttime;
    }

    public String getRc_endtime() {
        return this.rc_endtime;
    }

    public void setRc_endtime(String rc_endtime) {
        this.rc_endtime = rc_endtime;
    }

    public int getRc_scoring() {
        return this.rc_scoring;
    }

    public void setRc_scoring(int rc_scoring) {
        this.rc_scoring = rc_scoring;
    }

    public String getRc_inputsource() {
        return this.rc_inputsource;
    }

    public void setRc_inputsource(String rc_inputsource) {
        this.rc_inputsource = rc_inputsource;
    }

    public int getQrcode() {
        return this.qrcode;
    }

    public void setQrcode(int qrcode) {
        this.qrcode = qrcode;
    }

    public String getRecord_time() {
        return this.record_time;
    }

    public void setRecord_time(String record_time) {
        this.record_time = record_time;
    }

    
    





    public int getPresent() {
        return this.present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public int getAbsent() {
        return this.absent;
    }

    public void setAbsent(int absent) {
        this.absent = absent;
    }

    public int getOtherwise() {
        return this.otherwise;
    }

    public void setOtherwise(int otherwise) {
        this.otherwise = otherwise;
    }
    

    
}






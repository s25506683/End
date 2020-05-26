package com.example.demo.entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Question {
    private int q_id;
    private int q_std_id;
    private String q_content;
    private String q_reply;
    private String cs_id;
    private String cs_name;
    private String q_asktime;
    private String q_replytime;
    //private Time q_time;
    private String q_solved;
    private String std_mail;
    private int pq_id;
    private String pc_content;
    private String pc_time;
    private boolean pc_role;
    private boolean q_type;

    public boolean isQ_type() {
        return this.q_type;
    }

    public void setQ_type(boolean q_type) {
        this.q_type = q_type;
    }

    public int getPq_id() {
        return this.pq_id;
    }

    public void setPq_id(int pq_id) {
        this.pq_id = pq_id;
    }

    public String getPc_content() {
        return this.pc_content;
    }

    public void setPc_content(String pc_content) {
        this.pc_content = pc_content;
    }

    public String getPc_time() {
        return this.pc_time;
    }

    public void setPc_time(String pc_time) {
        this.pc_time = pc_time;
    }

    public boolean isPc_role() {
        return this.pc_role;
    }

    public void setPc_role(boolean pc_role) {
        this.pc_role = pc_role;
    }


    public String getStd_mail() {
        return this.std_mail;
    }

    public void setStd_mail(String std_mail) {
        this.std_mail = std_mail;
    }

    public int getQ_id() {
        return this.q_id;
    }

    public void setQ_id(int q_id) {
        this.q_id = q_id;
    }

    public int getQ_std_id() {
        return this.q_std_id;
    }

    public void setQ_std_id(int i) {
        this.q_std_id = i;
    }

    public String getQ_content() {
        return this.q_content;
    }

    public void setQ_content(String q_content) {
        this.q_content = q_content;
    }

    public String getQ_reply() {
        return this.q_reply;
    }

    public void setQ_reply(String q_reply) {
        this.q_reply = q_reply;
    }

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
    
    // public Timestamp getQ_time() {
    //     return this.q_time;
    // }

    // public void setQ_time(Timestamp q_time) {
    //     this.q_time = q_time;
    // }

    // public String getQ_time() {
    //     return this.q_time;
    // }

    // public void setQ_time(String q_time) {
    //     this.q_time = q_time;
    // }


    public String getQ_asktime() {
        return this.q_asktime;
    }

    public void setQ_asktime(String q_asktime) {
        this.q_asktime = q_asktime;
    }

    public String getQ_replytime() {
        return this.q_replytime;
    }

    public void setQ_replytime(String q_replytime) {
        this.q_replytime = q_replytime;
    }

    public String getQ_solved() {
        return this.q_solved;
    }

    public void setQ_solved(String q_solved) {
        this.q_solved = q_solved;
    }


    
}






package com.example.demo.entity;

public class Question {
    private int q_id;
    private int q_std_id;
    private String q_content;
    private String cs_id;
    private String cs_name;
    private String q_asktime;
    private String q_solved;
    private String std_mail;
    private boolean q_type;
    private String cb_content;
    private String cb_time;
    private int count;
    private int std_id;
    private boolean cb_role;



    public boolean isCb_role() {
        return this.cb_role;
    }

    public void setCb_role(boolean cb_role) {
        this.cb_role = cb_role;
    }

    public int getStd_id() {
        return this.std_id;
    }

    public void setStd_id(int std_id) {
        this.std_id = std_id;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCb_content() {
        return this.cb_content;
    }

    public void setCb_content(String cb_content) {
        this.cb_content = cb_content;
    }

    public String getCb_time() {
        return this.cb_time;
    }

    public void setCb_time(String cb_time) {
        this.cb_time = cb_time;
    }

    public boolean isQ_type() {
        return this.q_type;
    }

    public void setQ_type(boolean q_type) {
        this.q_type = q_type;
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

    public String getQ_asktime() {
        return this.q_asktime;
    }

    public void setQ_asktime(String q_asktime) {
        this.q_asktime = q_asktime;
    }

    public String getQ_solved() {
        return this.q_solved;
    }

    public void setQ_solved(String q_solved) {
        this.q_solved = q_solved;
    }


    
}






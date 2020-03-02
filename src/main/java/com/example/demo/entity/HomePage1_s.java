package com.example.demo.entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class HomePage1_s {
    private int q_id;
    private int q_std_id;
    private String q_content;
    private String cs_id;
    private String cs_name;
    //private String q_time;
    private Time q_time;

    
    

    
    
    public int getQ_id() {
        return this.q_id;
    }

    public void setQ_id(int q_id) {
        this.q_id = q_id;
    }

    public int getQ_std_id() {
        return this.q_std_id;
    }

    public void setQ_std_id(int q_std_id) {
        this.q_std_id = q_std_id;
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


    public Time getQ_time() {
        return this.q_time;
    }

    public void setQ_time(Time q_time) {
        this.q_time = q_time;
    }



    
}






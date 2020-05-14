package com.example.demo.entity;

public class Announcement {
    
    private int at_id;
    private String cs_id;
    private String at_title;
    private String at_content;
    private String at_posttime;

    private String cs_name;


    public int getAt_id() {
        return this.at_id;
    }

    public void setAt_id(int at_id) {
        this.at_id = at_id;
    }

    public String getCs_id() {
        return this.cs_id;
    }

    public void setCs_id(String cs_id) {
        this.cs_id = cs_id;
    }

    public String getAt_title() {
        return this.at_title;
    }

    public void setAt_title(String at_title) {
        this.at_title = at_title;
    }

    public String getAt_content() {
        return this.at_content;
    }

    public void setAt_content(String at_content) {
        this.at_content = at_content;
    }

    public String getAt_posttime() {
        return this.at_posttime;
    }

    public void setAt_posttime(String at_posttime) {
        this.at_posttime = at_posttime;
    }

    public String getCs_name() {
        return this.cs_name;
    }

    public void setCs_name(String cs_name) {
        this.cs_name = cs_name;
    }



}
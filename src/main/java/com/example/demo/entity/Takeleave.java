package com.example.demo.entity;

public class Takeleave {

    private int tl_id;
    private int rc_id;
    private int std_id;
    private String tl_content;
    private int tl_state;
    private int tl_type_id;
    private String tl_teacher_reply;
    private String tl_createtime;
    private String record_time;
    private String rc_starttime;

    public String getRc_starttime() {
        return this.rc_starttime;
    }

    public void setRc_starttime(String rc_starttime) {
        this.rc_starttime = rc_starttime;
    }

    private String std_name;
    private String rc_inputsource;
    private String cs_id;

    public String getCs_id() {
        return this.cs_id;
    }

    public void setCs_id(String cs_id) {
        this.cs_id = cs_id;
    }

    public String getRc_inputsource() {
        return this.rc_inputsource;
    }

    public void setRc_inputsource(String rc_inputsource) {
        this.rc_inputsource = rc_inputsource;
    }

    public String getRecord_time() {
        return this.record_time;
    }

    public void setRecord_time(String record_time) {
        this.record_time = record_time;
    }

    public String getStd_name() {
        return this.std_name;
    }

    public void setStd_name(String std_name) {
        this.std_name = std_name;
    }




    public int getTl_state() {
        return this.tl_state;
    }

    public void setTl_state(int tl_state) {
        this.tl_state = tl_state;
    }


    public int getTl_id() {
        return this.tl_id;
    }

    public void setTl_id(int tl_id) {
        this.tl_id = tl_id;
    }

    public int getRc_id() {
        return this.rc_id;
    }

    public void setRc_id(int rc_id) {
        this.rc_id = rc_id;
    }

    public int getStd_id() {
        return this.std_id;
    }

    public void setStd_id(int std_id) {
        this.std_id = std_id;
    }

    public String getTl_content() {
        return this.tl_content;
    }

    public void setTl_content(String tl_content) {
        this.tl_content = tl_content;
    }

    public int getTl_type_id() {
        return this.tl_type_id;
    }

    public void setTl_type_id(int tl_type_id) {
        this.tl_type_id = tl_type_id;
    }

    public String getTl_teacher_reply() {
        return this.tl_teacher_reply;
    }

    public void setTl_teacher_reply(String tl_teacher_reply) {
        this.tl_teacher_reply = tl_teacher_reply;
    }

    public String getTl_createtime() {
        return this.tl_createtime;
    }

    public void setTl_createtime(String tl_createtime) {
        this.tl_createtime = tl_createtime;
    }

}
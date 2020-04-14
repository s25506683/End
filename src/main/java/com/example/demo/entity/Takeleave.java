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
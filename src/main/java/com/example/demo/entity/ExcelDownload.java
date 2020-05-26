package com.example.demo.entity;

public class ExcelDownload {
    private int std_id;
    private String std_name;
    private String std_department;

    private String cs_id;

    private String rc_inputsource;

    private String record_time;

    private String tl_type_name;

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

    public String getTl_type_name() {
        return this.tl_type_name;
    }

    public void setTl_type_name(String tl_type_name) {
        this.tl_type_name = tl_type_name;
    }

}
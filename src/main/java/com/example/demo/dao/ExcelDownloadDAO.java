package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.ExcelDownload;

public interface ExcelDownloadDAO {

    public int CsIdExist(String cs_id);

    public String queryRcStartTime(int rc_id);

    public String findCsName(String cs_id);

    public String findRcClassInfo(String rc_id);

    public String findAllRollcallStartTime(String cs_id);

    public List<ExcelDownload> findStudentList(String cs_id);

    public List<ExcelDownload> findRollcallRecord(String classinfo, String rc_starttime);

}
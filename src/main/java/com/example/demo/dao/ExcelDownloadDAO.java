package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.ExcelDownload;

public interface ExcelDownloadDAO {

    public int RcIdExist(int rc_id);

    public String queryRcStartTime(int rc_id);

    public String findCsId(int rc_id);

    public List<ExcelDownload> findOneRollcallRecord(int rc_id);

    public String findRcClassInfo(int rc_id);



}
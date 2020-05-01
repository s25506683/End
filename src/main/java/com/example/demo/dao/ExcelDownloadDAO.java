package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.ExcelDownload;

public interface ExcelDownloadDAO {

public List<ExcelDownload> findOneRollcallRecord(int rc_id);

public String findRcClassInfo(int rc_id);



}
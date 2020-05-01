package com.example.demo.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import com.example.demo.dao.ExcelDownloadDAO;
import com.example.demo.entity.ExcelDownload;
import com.example.demo.util.ExcelUtil;


@RestController
@RequestMapping("/downloadExcel")
public class ExcelDownloadController {

    @Autowired
    ExcelDownloadDAO dao;

    @Autowired
    ExcelUtil excelutil;



    //下載rollcall的record.
    @RequestMapping(value = "/Rollcall/{rc_id}/")
    public void downloadRollcallActions(@PathVariable("rc_id") int rc_id)throws Exception {
        //dao.findOneRollcallRecord(rc_id);

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        String classinfo[] = dao.findRcClassInfo(rc_id).split(",");
        String function = "Rollcall";


        
        excelutil.write(dao.findOneRollcallRecord(rc_id), classinfo, function, response);


    }

}
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
import org.springframework.http.ResponseEntity;
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
import com.example.demo.util.AuthenticationUtil;
import com.example.demo.util.Logfile;


@RestController
@RequestMapping("/teacher/downloadExcel")
public class ExcelDownloadController {

    @Autowired
    ExcelDownloadDAO dao;

    @Autowired
    ExcelUtil excelutil;

    @Autowired
    Logfile logfile;
    
    String writtenmessage = new String();
    String partition = "Rollcall";



    //下載rollcall的record.
    @RequestMapping(value = "/Rollcall/{rc_id}/")
    public ResponseEntity<String> downloadRollcallActions(@PathVariable("rc_id") int rc_id)throws Exception {
        //dao.findOneRollcallRecord(rc_id);
        AuthenticationUtil auth = new AuthenticationUtil();
        String teacher_id = auth.getCurrentUserName();

        if(dao.RcIdExist(rc_id) == 1){
            //if rc_id has exist.
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = requestAttributes.getResponse();
            String classinfo[] = dao.findRcClassInfo(rc_id).split(",");
            String function = "Rollcall";
    
            writtenmessage = "teacher "+ teacher_id + " download rollcall time \""+ dao.queryRcStartTime(rc_id) +"\" Excel file.";
            logfile.writeLog(writtenmessage, dao.findCsId(rc_id), partition);


            excelutil.write(dao.findOneRollcallRecord(rc_id), classinfo, function, response);
            return ResponseEntity.ok("request successful! Excel has already download!");
        }else{
            //rc_id not found.
            return ResponseEntity.badRequest().body("request failed. rc_id not found!");
        }


    }

}
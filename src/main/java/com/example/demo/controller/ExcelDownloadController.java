package com.example.demo.controller;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import com.example.demo.dao.ExcelDownloadDAO;
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
    @RequestMapping(value = "/Rollcall/{cs_id}/")
    public ResponseEntity<String> downloadRollcallActions(@PathVariable("cs_id") String cs_id)throws Exception {
        AuthenticationUtil auth = new AuthenticationUtil();
        String teacher_id = auth.getCurrentUserName();

        if(dao.CsIdExist(cs_id) == 1){
            //if cs_id has exist.
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = requestAttributes.getResponse();
            String classinfo[] = dao.findRcClassInfo(cs_id).split(",");
            String function = "Rollcalls";
    
            writtenmessage = "teacher "+ teacher_id + " download "+ dao.findCsName(cs_id) +" rollcalls Excel file.";
            logfile.writeLog(writtenmessage, cs_id, partition);


            excelutil.write(dao.findStudentList(cs_id), classinfo, function, response);
            return ResponseEntity.ok("request successful! Excel has already download!");
        }else{
            //cs_id not found.
            return ResponseEntity.badRequest().body("request failed. cs_id not found!");
        }


    }

}
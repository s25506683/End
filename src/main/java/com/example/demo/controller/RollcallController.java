package com.example.demo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
/////////////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.RollcallDAO;
import com.example.demo.entity.Rollcall;
import com.example.demo.util.AuthenticationUtil;
import com.example.demo.util.UserInTheClass;

@RestController
public class RollcallController {
  @Autowired
  RollcallDAO dao;

  @Autowired
  UserInTheClass userintheclass;

   //add rollcall and rollcall record.
   //you will inoput cs_id, rc_name, rc_endtime, rc_inputsource, rc_scoring, qrcode.
  @PostMapping(value = "/teacher/rollcall/addrollcall/")
     public ResponseEntity<String> processFormCreate(@RequestBody Rollcall rollcall) throws SQLException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();

        if(userintheclass.queryTeacherInTheClass(teacher_id, rollcall.getCs_id()) == 0){
            //if teacher not in this class.
            return ResponseEntity.badRequest().body("request failed. teacher not in this class!");
        }else if(dao.hasTheSameRollcallName(rollcall.getRc_name()) == 1){
            //if the rollcall name has been used.
           return ResponseEntity.badRequest().body("request failed.This rollcall name has already been used!");
        }else{
           //add rollcall.
           dao.addRollcall(rollcall);
           String[] studentInfoarr = dao.findClassStudent(rollcall.getCs_id());
           //calculate the array(String) of length.
           int size = studentInfoarr.length;
           //initialize array(int)'s size.
           int [] studentInfointarr = new int [size];
           //using for loop to convert array(String) to int, also input rollcall record every student in the class.
           for(int i=0 ; i<size ; i++) {
              //studentInfointarr[i] = Integer.parseInt(studentInfoarr[i]);
              dao.addRollcallRecord(rollcall.getRc_name(), Integer.parseInt(studentInfoarr[i]));
              System.out.println(studentInfointarr[i]+"\n");
            }
           return ResponseEntity.ok("request successful! the rollcall has already added!");
        }
         

     }
    //@POST
 @GetMapping(value = {"/rollcall/one/{rc_id}"})
    public List<Rollcall> retrieveOneRollcall(@PathVariable("rc_id") final int rc_id) throws SQLException {
       return dao.findOneRollcallRecord(rc_id);
    }
    
    //get all rollcall in this class
    //you will get rc_starttime(String), present(int), absent(int), otherwise(int), rc_scoring(int), rc_inputsource(String) returns.
 @GetMapping(value = {"/rollcall/all/{cs_id}"})
    public List<Rollcall> retrieveRollcall(@PathVariable("cs_id") final String cs_id) throws SQLException{
       return dao.findAllRollcallRecord(cs_id);
    }

 @GetMapping(value = {"/rollcall/{cs_id}"})
    public List<Rollcall> retrieveRollcall2(@PathVariable("cs_id") final String cs_id) throws SQLException{
       return dao.findClassList(cs_id);
    }
    
//  @PutMapping(value = "/rollcall")
//     public void processFormUpdate(@RequestBody HomePage1_s homepage1_s) throws SQLException {
//        dao.update(homepage1_s);
//     }

//  @DeleteMapping(value = "/rollcall/{id}")
//     public void deleteQuestion(@PathVariable("id") int id) {
//        dao.delete(id);
//     }
    
 

 //學生端Qrcode點名
 //教師端手動編輯點名
 //老師刪除點名紀錄
 //
}




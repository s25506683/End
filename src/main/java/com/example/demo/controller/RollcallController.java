package com.example.demo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.example.demo.util.CurrentTimeStamp;
import com.example.demo.util.UserInTheClass;

@RestController
public class RollcallController {
  @Autowired
  RollcallDAO dao;

  @Autowired
  UserInTheClass userintheclass;

   //add rollcall and rollcall record.
   //you will inoput cs_id, rc_inputsource, qrcode, (longitude), (latitude).
  @PostMapping(value = "/teacher/rollcall/addrollcall")
     public ResponseEntity<String> processFormCreate(@RequestBody Rollcall rollcall) throws SQLException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();

        if(userintheclass.queryTeacherInTheClass(teacher_id, rollcall.getCs_id()) == 0){
            //if teacher not in this class.
            return ResponseEntity.badRequest().body("request failed. teacher not in this class!");
        }else{

           CurrentTimeStamp ts = new CurrentTimeStamp();
           rollcall.setRc_starttime(ts.getCurrentTimeStamp());
           //add rollcall.
           dao.addRollcall(rollcall);
           rollcall.setRc_id(dao.findRcId(rollcall.getCs_id(), rollcall.getRc_starttime()));

           String[] studentInfoarr = dao.findClassStudent(rollcall.getCs_id());
           //calculate the array(String) of length.
           int size = studentInfoarr.length;
           //initialize array(int)'s size.
           int [] studentInfointarr = new int [size];
           //using for loop to convert array(String) to int, also input rollcall record every student in the class.
           for(int i=0 ; i<size ; i++) {
              //studentInfointarr[i] = Integer.parseInt(studentInfoarr[i]);
              dao.addRollcallRecord(rollcall.getRc_id(), Integer.parseInt(studentInfoarr[i]));
              System.out.println(studentInfointarr[i]+"\n");
            }
           return ResponseEntity.ok("request successful! the rollcall has already added!");
        }
         

     }
   
   //student get one rollcall's record.
   //you will get std_id, std_name, std_department, record_time, tl_type_name returns.
 @GetMapping(value = {"/student/rollcall/oneRollcall/{rc_id}"})
    public ResponseEntity<List<Rollcall>> retrieveOneRollcallFromStudent(@PathVariable("rc_id") final int rc_id) throws SQLException {

       if(dao.hasThisRollcallId(rc_id) == 0){
         //if this rollcall Id not found.
         return new ResponseEntity<List<Rollcall>>(HttpStatus.BAD_REQUEST);
       }else{
         return new ResponseEntity<List<Rollcall>>(dao.findOneRollcallRecord(rc_id), HttpStatus.OK);
       }
    }

   //student get one rollcall's record.
   //you will get std_id, std_name, std_department, record_time, tl_type_name returns.
 @GetMapping(value = {"/teacher/rollcall/oneRollcall/{rc_id}"})
 public ResponseEntity<List<Rollcall>> retrieveOneRollcallFromTeacher(@PathVariable("rc_id") final int rc_id) throws SQLException {

    if(dao.hasThisRollcallId(rc_id) == 0){
      //if this rollcall Id not found.
      return new ResponseEntity<List<Rollcall>>(HttpStatus.BAD_REQUEST);
    }else{
      return new ResponseEntity<List<Rollcall>>(dao.findOneRollcallRecord(rc_id), HttpStatus.OK);
    }
 }
    
    //student get all rollcall in this class.
    //you will get rc_starttime(String), present(int), absent(int), otherwise(int), rc_scoring(int), rc_inputsource(String) returns.
 @GetMapping(value = {"/student/rollcall/allRollcall/{cs_id}"})
    public ResponseEntity<List<Rollcall>> retrieveAllRollcallFromStudent(@PathVariable("cs_id") final String cs_id) throws SQLException{
      AuthenticationUtil auth = new AuthenticationUtil();
      String std_id = auth.getCurrentUserName();

       if(userintheclass.queryStudentInTheClass(std_id, cs_id) == 0){
          //if student not in this class.
         return new ResponseEntity<List<Rollcall>>(HttpStatus.BAD_REQUEST);
       }else{
         return new ResponseEntity<List<Rollcall>>(dao.findAllRollcallRecord(cs_id), HttpStatus.OK);
       }
    }

    //teacher get all rollcall in this class
    //you will get rc_starttime(String), present(int), absent(int), otherwise(int), rc_scoring(int), rc_inputsource(String) returns.
@GetMapping(value = {"/teacher/rollcall/allRollcall/{cs_id}"})
    public ResponseEntity<List<Rollcall>> retrieveAllRollcallFromTeacher(@PathVariable("cs_id") final String cs_id) throws SQLException{
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();

       if(userintheclass.queryTeacherInTheClass(teacher_id, cs_id) == 0){
          //if teacher not in this class.
         return new ResponseEntity<List<Rollcall>>(HttpStatus.BAD_REQUEST);
       }else{
         return new ResponseEntity<List<Rollcall>>(dao.findAllRollcallRecord(cs_id), HttpStatus.OK);
       }
    }

    //teacher watch student list in this class.
    //you will get std_id, std_name, std_department returns.
 @GetMapping(value = {"/teacher/rollcall/studentList/{cs_id}"})                        
    public ResponseEntity<List<Rollcall>> retrieveWhatStuentInTheClass(@PathVariable("cs_id") final String cs_id) throws SQLException{
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
      if(userintheclass.queryTeacherInTheClass(teacher_id, cs_id) == 0){
         //if teacher not in this class.
        return new ResponseEntity<List<Rollcall>>(HttpStatus.BAD_REQUEST);
      }else{
        return new ResponseEntity<List<Rollcall>>(dao.findClassStudentList(cs_id), HttpStatus.OK);
      }
    }
    
    //student QRcode rollcall.
    //you will input rc_id, qrcode.
 @PutMapping(value = "/student/rollcall/QRcodeRollcall")
    public ResponseEntity<String> processUpdateRollcallStudentWithQRcode(@RequestBody Rollcall rollcall) throws SQLException {
      AuthenticationUtil auth = new AuthenticationUtil();
      int std_id = Integer.parseInt(auth.getCurrentUserName());

      String qrcode = dao.findQRcodeInRollcallName(rollcall.getRc_id());


      if(dao.rollcallIsEnd(rollcall.getRc_id()) == 1){
        return ResponseEntity.badRequest().body("request failed. This rollcall was closed!");

      }else if(rollcall.getQrcode().equals(qrcode)){
        //if input qrcode equals rollcall's qrcode.
        dao.updateRollcallRecord(std_id, rollcall.getRc_id());
        return ResponseEntity.ok("request successful! the QRcode rollcall record has already added!");

      }else{
        return ResponseEntity.badRequest().body("request failed. QRcode was round in this class!");
      }
       
    }

    //teacher renew a QRcode.
    //you have to input rc_id, qrcode.
    @PutMapping(value = "/teacher/rollcall/updateQRcode")
    public ResponseEntity<String> teacherUpdateQRcode(@RequestBody Rollcall rollcall) throws SQLException {

      if(dao.rollcallByHand(rollcall.getRc_id()).equals("手動點名")){
        return ResponseEntity.badRequest().body("request failed. This rollcall only update by hand!");
      }else{
        dao.updateQRcode(rollcall.getRc_id(), rollcall.getQrcode());
        return ResponseEntity.ok("request successful! the QRcode has already changed!");
      }

    }


   //teacher delete rollcall and rollcall record in the class.
   //you have to input rc_id, cs_id in json.
 @DeleteMapping(value = "/teacher/rollcall/deleteRollcall")
    public ResponseEntity<String> deleteQuestion(@RequestBody Rollcall rollcall) {
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();

       if(userintheclass.queryTeacherInTheClass(teacher_id, rollcall.getCs_id()) == 0){
          //if teacher not in this class.
          return ResponseEntity.badRequest().body("request failed. teacher not in this class!");
       }else{
         dao.deleteRollcall(rollcall.getRc_id());
         return ResponseEntity.ok("request successful! the rollcall has already deleted!");
       }
    }






 

 //學生端Qrcode點名insert(done)
 //教師端手動update編輯點名
 //老師刪除點名紀錄(done)
 //老師端更新QRcode
 //學生看到所有點名
 //student QRcode rollcall. 的input rc_name 改成 rc_id.
}




package com.example.demo.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.validation.constraints.Null;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AcceptAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.HttpRequestMethodNotSupportedException;

//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;

//import org.springframework.web.servlet.ModelAndView;
import com.example.demo.dao.AcceptanceDAO;
import com.example.demo.entity.Acceptance;
import com.example.demo.util.AuthenticationUtil;
import com.example.demo.util.Logfile;
import com.example.demo.util.UserInTheClass;

@RestController
public class AcceptanceController {
   @Autowired
   AcceptanceDAO dao;

   @Autowired
   UserInTheClass userintheclass;

   @Autowired
   Logfile logfile;

   String writtenmessage = new String();
   String partition = "Acceptance";


   //student post there acceptance to db.
   //you will input std_id, accept_hw_id.
   @PostMapping(value = "/student/acceptance")
   public ResponseEntity<String> processFormCreate(@RequestBody final Acceptance acceptance)
         throws SQLException, IOException {
       
         AuthenticationUtil auth = new AuthenticationUtil();
         acceptance.setStd_id(Integer.parseInt(auth.getCurrentUserName())); //將取出的值轉Int
         acceptance.setAccept_hw_id(dao.findHomeworkID(acceptance.getHw_name()));
         acceptance.setCs_id(dao.findCsID(acceptance.getAccept_hw_id()));

         if(dao.queryStudentInTheAcceptance(acceptance.getStd_id(), acceptance.getAccept_hw_id()) == 0){
   
            dao.insertAcceptance(acceptance);
            acceptance.getAccept_hw_id();

            writtenmessage = "student \"" + acceptance.getStd_id() + "\" writing acceptance in homework \"" + acceptance.getAccept_hw_id() + "\".";
            logfile.writeLog(writtenmessage, acceptance.getCs_id(), partition);
            return ResponseEntity.ok("登記驗收成功");
   
         }else{
            return ResponseEntity.badRequest().body("您已驗收過");
         }

            //新增驗收
   } 
    //@POST

//teacher post there homework to db.
//you wii input hw_name, hw_content, hw_cs_id.
@PostMapping(value = "/teacher/acceptance/homework")
   public ResponseEntity<String> processFormCreate2(@RequestBody final Acceptance acceptance) throws SQLException,
         IOException {

   
      if(acceptance.getHw_content() == "" || acceptance.getHw_name() == ""){
         return ResponseEntity.badRequest().body("作業名稱與內容不得為空");
      }else if(dao.queryHomeworkInTheClass(acceptance.getHw_name(), acceptance.getHw_cs_id()) == 1){
         return ResponseEntity.badRequest().body("這堂課已有此作業，請更改作業名稱"); //資料庫內已有相同名稱作業
      }else{
         dao.insertHomework(acceptance);
         AuthenticationUtil auth = new AuthenticationUtil();
         String teacher_id= auth.getCurrentUserName();
         acceptance.getHw_cs_id();
         writtenmessage = "teacher \"" + teacher_id + "\" create new homework in class \"" + acceptance.getHw_cs_id() + "\".";
			logfile.writeLog(writtenmessage, acceptance.getHw_cs_id(), partition);
         return ResponseEntity.ok("新增作業成功");
      }
      //新增作業
   }


//student get homework about concent in this class.
//You will get hw_name, hw_createtime, hw_content.
 @GetMapping(value = {"/student/acceptance/{hw_cs_id}"})
    public ResponseEntity<List<Acceptance>> retrieveOneAcceptance(@PathVariable("hw_cs_id") final String hw_cs_id) throws SQLException,
          IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String std_id = auth.getCurrentUserName();
     
      if(dao.queryStudentInTheClass(hw_cs_id,std_id) == 0){
         //如果學生不屬於這個課堂
         return new ResponseEntity<List<Acceptance>>(HttpStatus.BAD_REQUEST);
      }else{
         writtenmessage = "student \"" + std_id + "\" watching homework in class \"" + hw_cs_id + "\".";
         logfile.writeLog(writtenmessage, hw_cs_id, partition);
         return new ResponseEntity<List<Acceptance>>(dao.findCourseHomework(hw_cs_id), HttpStatus.OK);
      }

    } //第一頁驗收中學生可以看到作業的相關內容

   
   //teacher get homework about concent in this class.
   //You will get hw_name, hw_createtime, hw_content, hw_id.
    @GetMapping(value = {"/teacher/acceptance/{hw_cs_id}"})
    public ResponseEntity<List<Acceptance>> retrieveOneAcceptanceTeacher(@PathVariable("hw_cs_id") final String hw_cs_id) throws SQLException,
          IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName(); 
     
      if(dao.queryTeacherInTheClass(hw_cs_id,teacher_id) == 0){
         //如果學生不屬於這個課堂
         return new ResponseEntity<List<Acceptance>>(HttpStatus.BAD_REQUEST);
      }else{
         writtenmessage = "teacher \"" + teacher_id + "\" watching homework in class \"" + hw_cs_id + "\".";
         logfile.writeLog(writtenmessage, hw_cs_id, partition);
         return new ResponseEntity<List<Acceptance>>(dao.findCourseHomeworkformTeacher(hw_cs_id), HttpStatus.OK);
      }

    } //第一頁驗收中教師可以看到作業的相關內容

 //student get acceptance list about concent in this class.
 //You will get accept_id, accept_std_id, accept_hw_id, accept_time, accept_score, accept_done, hw_name, hw_content
 @GetMapping(value = {"/student/acceptance/hw/{cs_id}/{hw_name}"})
    public ResponseEntity<List<Acceptance>> retrieveAcceptance(@PathVariable("cs_id") final String cs_id, @PathVariable("hw_name") final String hw_name) throws SQLException,
          IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String std_id = auth.getCurrentUserName();
      if(dao.queryStudentInTheClass(cs_id,std_id) == 0){
         //如果學生不屬於這個課堂
         return new ResponseEntity<List<Acceptance>>(HttpStatus.BAD_REQUEST);
      }else{
         writtenmessage = "student \"" + std_id + "\" watching acceptancelist in class \"" + cs_id + "\".";
         logfile.writeLog(writtenmessage, cs_id, partition);
          return new ResponseEntity<List<Acceptance>>(dao.findHomeworkDetail(cs_id,hw_name),HttpStatus.OK); //
      }
     
    }

    
 //teacher get acceptance list about concent in this class.
 //You will get accept_id, accept_std_id, accept_hw_id, accept_time, accept_score, accept_done, hw_name, hw_content
    @GetMapping(value = {"/teacher/acceptance/hw/{cs_id}/{hw_name}"})
    public ResponseEntity<List<Acceptance>> retrieveAcceptanceTeacher(@PathVariable("cs_id") final String cs_id, @PathVariable("hw_name") final String hw_name) throws SQLException,
          IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
      if(dao.queryTeacherInTheClass(cs_id,teacher_id) == 0){
         //如果學生不屬於這個課堂
         return new ResponseEntity<List<Acceptance>>(HttpStatus.BAD_REQUEST);
      }else{
         writtenmessage = "teacher \"" + teacher_id + "\" watching acceptancelist in class \"" + cs_id + "\".";
         logfile.writeLog(writtenmessage, cs_id, partition);
          return new ResponseEntity<List<Acceptance>>(dao.findHomeworkDetailformTeacher(cs_id,hw_name),HttpStatus.OK); //
      }
     
    }
   

 //get Who is in this acceptance.
 //You will get login std_id.
    @GetMapping(value = {"/student/acceptance/std_id"})
    public ResponseEntity<Acceptance> findStudentInTheAccept() throws SQLException{
      AuthenticationUtil auth = new AuthenticationUtil();
      int std_id = Integer.parseInt(auth.getCurrentUserName());
      Acceptance acceptance = new Acceptance();
      acceptance.setStd_id(std_id);
      return new ResponseEntity<Acceptance>(acceptance,HttpStatus.OK);

   }

    
 //teacher update student score in this homework.
 //You have input std_id, accept_hw_id, accept_score.
 @PutMapping(value = "/teacher/updateScore")
    public ResponseEntity<String> processFormUpdate(@RequestBody final Acceptance acceptance) throws SQLException,
          IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
      acceptance.setCs_id(dao.findCsID(acceptance.getAccept_hw_id()));

      if(Integer.toString(acceptance.getAccept_score()).equals("")){
         //if score not exist.
         return ResponseEntity.badRequest().body("請輸入分數，分數不得為空值");
      }else if(dao.queryStudentInTheAcceptance(acceptance.getStd_id(),acceptance.getAccept_hw_id()) == 1){
         dao.updateScore(acceptance);
         writtenmessage = "teacher \"" + teacher_id + "\" update score in homework \"" + acceptance.getAccept_hw_id() +"\".";
         logfile.writeLog(writtenmessage, acceptance.getCs_id(), partition);
         return ResponseEntity.ok("已修改成績");
      }else{
         return ResponseEntity.badRequest().body("此學生尚未排隊");
      } 

    }

 //teacher update homework in this homework.
 //You have input hw_name, hw_content, hw_id.
 @PutMapping(value = "/teacher/updateContent")
    public ResponseEntity<String> processFormUpdate2(@RequestBody final Acceptance acceptance) throws SQLException,
          IOException {
       
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
      acceptance.setCs_id(dao.findCsID(acceptance.getHw_id()));
      

      if(acceptance.getHw_content() == "" || acceptance.getHw_name() == ""){
          //if hw_concent or hw_name is null.
         return ResponseEntity.badRequest().body("作業內容或名稱不得為空");
      }else if(dao.queryHomeworkID(acceptance.getHw_id()) == 0){
          //if hw_id not found.
         return ResponseEntity.badRequest().body("無此作業，請先新增作業");
      }else if(userintheclass.queryTeacherInTheClass(teacher_id, acceptance.getCs_id()) == 0){
         //if teacher not in this class.
         return ResponseEntity.badRequest().body("request failed. teacher not in this class!");
      }else{
         dao.updateContent(acceptance);
         writtenmessage = "teacher \"" + teacher_id + "\" update content in homework \"" + acceptance.getHw_name() + "\".";
         logfile.writeLog(writtenmessage, acceptance.getCs_id(), partition);
         return ResponseEntity.ok("修改成功");
      }
       
      
    }
   
 //student delete there acceptance.   
 //input std_id, hw_name.
 @DeleteMapping(value = "/student/acceptance/deleteAcceptance")
    public ResponseEntity<String> deleteAcceptance(@RequestBody final Acceptance acceptance) throws SQLException,
          IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      acceptance.setStd_id(Integer.parseInt(auth.getCurrentUserName()));
      acceptance.setAccept_hw_id(dao.findHomeworkID(acceptance.getHw_name()));
      acceptance.setCs_id(dao.findCsID(acceptance.getAccept_hw_id()));

      if(dao.queryStudentInTheAcceptance(acceptance.getStd_id(),acceptance.getAccept_hw_id()) == 0){
         return ResponseEntity.badRequest().body("此學生尚未點選驗收");
      }else if(dao.queryStudentAcceptDone(acceptance.getStd_id(), acceptance.isAccept_done(), acceptance.getAccept_hw_id()) == 0){
         return ResponseEntity.badRequest().body("老師已驗收完成無法取消驗收");

      }else{
         dao.deleteAcceptance(acceptance);
         writtenmessage = "student \"" + acceptance.getStd_id() + "\" deleted acceptance with homework ID \"" + acceptance.getAccept_hw_id() + "\".";
         logfile.writeLog(writtenmessage, acceptance.getCs_id(), partition);
         return ResponseEntity.ok("取消驗收成功");
      }      

    }  

 //teacher delete there homework.
 //input hw_name, hw_cs_id.
 @DeleteMapping(value = "/teacher/acceptance/deleteHomework")
    public ResponseEntity<String> deleteHomework(@RequestBody final Acceptance acceptance) throws SQLException,
          IOException {
      
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
     
      dao.deleteHomework(acceptance);
      writtenmessage = "teacher \"" + teacher_id + "\" deleted homework with homework \"" + acceptance.getHw_name() + "\".";
      logfile.writeLog(writtenmessage, acceptance.getHw_cs_id(), partition);
      return ResponseEntity.ok("刪除作業完成!");

    }
    
 
}
   
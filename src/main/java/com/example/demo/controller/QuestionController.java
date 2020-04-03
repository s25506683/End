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
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;

//import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.QuestionDAO;
import com.example.demo.entity.Question;
import com.example.demo.util.AuthenticationUtil;
import com.example.demo.util.Logfile;
//import com.example.demo.util.CurrentTimeStamp;

@RestController
public class QuestionController {
  @Autowired
  QuestionDAO dao;

  @Autowired
   Logfile logfile;
   
  String writtenmessage = new String();


  //student post there question to db.
  //you will input q_content, cs_id.
 @PostMapping(value = "/student/question")
    public ResponseEntity<String> proccessStudentQoestion(@RequestBody final Question question) throws SQLException {
       if(question.getQ_content() == ""){
          //if question content is null.
          return ResponseEntity.badRequest().body("request failed. input content is null!");
       }else if(dao.queryCs_id(question.getCs_id()) == 0){
          //if cs_id does not found.
          return ResponseEntity.badRequest().body("request failed. input ClassId not found!");
       }else{
          dao.studentinsert(question);
          AuthenticationUtil auth = new AuthenticationUtil();
          String std_id = auth.getCurrentUserName();
          question.getCs_id();
          writtenmessage = "student \"" + std_id + "\" writing question in class \"" + question.getCs_id() + "\".";
			 logfile.writeLog(writtenmessage);
          return ResponseEntity.ok("request successful!");
       }
    }
    //@POST

 /*@GetMapping(value = {"/student/question/one/{cs_id}/{std_id}"})
    public Question retrieveOneQuestion(@PathVariable("cs_id") final String cs_id, @PathVariable("std_id") final int std_id) throws SQLException{
       return dao.findOne(cs_id,std_id);
    }*/
    

  //student get all question in this class.
  //You will get q_id, q_std_id, q_content, q_reply, cs_id, cs_name, q_asktime, q_solved.
 @GetMapping(value = {"/student/question/all/{cs_id}"})
    public ResponseEntity<List<Question>> retrieveQuestionstudentview(@PathVariable("cs_id") final String cs_id) throws SQLException{
      AuthenticationUtil auth = new AuthenticationUtil();
      String std_id = auth.getCurrentUserName();

      if(dao.queryStudentInTheClass(std_id, cs_id) == 0){
         //if student does not belong to this class.
         return new ResponseEntity<List<Question>>(HttpStatus.BAD_REQUEST);
       }else{
         writtenmessage = "student \"" + std_id + "\" watching question in class \"" + cs_id + "\".";
         logfile.writeLog(writtenmessage);
         return new ResponseEntity<List<Question>>(dao.findQuestion(cs_id), HttpStatus.OK);
       }
       
    }

   //teacher get all question in this class.
   //You will get q_id, q_std_id, q_content, q_reply, cs_id, cs_name, q_asktime, q_solved.
 @GetMapping(value = {"/teacher/question/all/{cs_id}"})
    public ResponseEntity<List<Question>> retrieveQuestionteacherview(@PathVariable("cs_id") final String cs_id) throws SQLException{
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
       if(dao.queryTeacherInTheClass(teacher_id, cs_id) == 0){
         return new ResponseEntity<List<Question>>(HttpStatus.BAD_REQUEST);
       }else{
         writtenmessage = "teacher \"" + teacher_id + "\" watching question in class \"" + cs_id + "\".";
         logfile.writeLog(writtenmessage);
         return new ResponseEntity<List<Question>>(dao.findQuestion(cs_id), HttpStatus.OK);
       }
    }


   //update student's question in this class.
   //You have input q_asktime, q_content.
 @PutMapping(value = "/student/question")
    public ResponseEntity<String> UpdateStudentQuestionContent(@RequestBody final Question question) throws SQLException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String std_id = auth.getCurrentUserName();
       if(dao.hasBeenReply(question.getQ_std_id(), question.getQ_asktime()) == 1){
         return ResponseEntity.badRequest().body("request failed. your question has already replied from teacher!");
       }else{
         dao.updateStudentQuestionContent(question);
         writtenmessage = "student \"" + std_id + "\" update question in class \"" + question.getCs_id() + "\" with question's asktime \"" + question.getQ_asktime() + "\".";
         logfile.writeLog(writtenmessage);
         return ResponseEntity.ok("request successful! your question update completed!");
       }
    }
   

    //update teacher's reply in this class.
    //You have input q_reply, q_replytime, q_std_id, q_asktime.
 @PutMapping(value = "/teacher/question")
    public ResponseEntity<String> processFormUpdate(@RequestBody final Question question) throws SQLException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();

      if(dao.queryTeacherInTheClass(teacher_id, question.getCs_id()) == 0){
         //if teacher not in this class.
         return ResponseEntity.badRequest().body("request failed. teacher not in this class!");
      }else if(dao.queryStudentInTheClass(Integer.toString(question.getQ_std_id()), question.getCs_id()) == 0){
         //if input student not in this class.
         return ResponseEntity.badRequest().body("request failed. input student not in this class!");
      }else{
         dao.updateTeacherReply(question);
         writtenmessage = "teacher \"" + teacher_id + "\" reply question in class \"" + question.getCs_id() + "\" with question's asktime \"" + question.getQ_asktime() + "\", student \"" + question.getQ_std_id() + "\".";
         logfile.writeLog(writtenmessage);
         return ResponseEntity.ok("request successful! your reply update completed!");
      }
    }

   //student delete there question.
   //input std_id, q_asktime.
 @DeleteMapping(value = "/student/question/")
    public void StudentdeleteQuestion(@RequestBody final Question question) throws SQLException{
       dao.deleteQuestion(question);
    }

   //teacher delete student's question.
   //input std_id, q_asktime.
 @DeleteMapping(value = "/teacher/question/{id}")
    public void TeacherdeleteQuestion(@RequestBody final Question question) throws SQLException{
       dao.deleteQuestion(question);
    }
 
   
 
}




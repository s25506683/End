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
//import com.example.demo.util.AuthenticationUtil;
import com.example.demo.util.Logfile;

@RestController
public class QuestionController {
  @Autowired
  QuestionDAO dao;

  @Autowired
   Logfile logfile;
   
  String writtenmessage = new String();

 @PostMapping(value = "/student/question")
    public ResponseEntity<String> proccessStudentQoestion(@RequestBody final Question question) throws SQLException {
       if(question.getQ_content() == ""){
          return ResponseEntity.badRequest().body("request failed. input content is null!");
       }else if(dao.queryCs_id(question.getCs_id()) == 0){
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
    
 @GetMapping(value = {"/student/question/all/{cs_id}"})
    public ResponseEntity<List<Question>> retrieveQuestionstudentview(@PathVariable("cs_id") final String cs_id) throws SQLException{
      AuthenticationUtil auth = new AuthenticationUtil();
      String std_id = auth.getCurrentUserName();

      if(dao.queryStudentInTheClass(std_id, cs_id) == 0){
         return new ResponseEntity<List<Question>>(HttpStatus.BAD_REQUEST);
       }else{
         writtenmessage = "student \"" + std_id + "\" watching question in class \"" + cs_id + "\".";
         logfile.writeLog(writtenmessage);
         return new ResponseEntity<List<Question>>(dao.findQuestion(cs_id), HttpStatus.OK);
       }
       
    }

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

    
 @PutMapping(value = "/teacher/question")
    public void processFormUpdate(@RequestBody final Question question) throws SQLException {
       dao.update(question);
    }

 @DeleteMapping(value = "/question/{id}")
    public void deleteQuestion(@PathVariable("id") final int id) {
       dao.delete(id);
    }
 
}




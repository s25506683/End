package com.example.demo.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.example.demo.util.UserInTheClass;
import com.example.demo.util.AuthenticationUtil;
import com.example.demo.util.CurrentTimeStamp;
import com.example.demo.util.Logfile;
//import com.example.demo.util.CurrentTimeStamp;

@RestController
public class QuestionController {
   @Autowired
   QuestionDAO dao;

   @Autowired
   UserInTheClass userintheclass;

   @Autowired
   Logfile logfile;

   String writtenmessage = new String();
   String partition = "Question";



   // student post there question to db.
   // you will input q_content, cs_id, q_type.
   @PostMapping(value = "/student/question")
   public ResponseEntity<String> proccessStudentQuestion(@RequestBody final Question question)
         throws SQLException, IOException, ParseException {

      AuthenticationUtil auth = new AuthenticationUtil();
      String std_id = auth.getCurrentUserName();

      if(dao.hasThisStudentInQuestion(std_id, question.getCs_id()) >= 1){
         question.setQ_asktime(dao.findQuestionAsktime(std_id, question.getCs_id()));
         //抓取現在的時間
         Date timenow = new Date(); 
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         //抓取上一次發問的時間
         String oldAsktime = question.getQ_asktime();
         //將上次發問的時間轉換成毫秒
         long timeInMillis = sdf.parse(oldAsktime).getTime();
         long number = timenow.getTime() - timeInMillis;
         if(number < 300000){
            //if the time of the last question is too close now.
            return ResponseEntity.badRequest().body("request failed. Questions cannot be repeated within 5 minutes");
          }
      }
       if(question.getQ_content() == ""){
          //if question content is null.
          return ResponseEntity.badRequest().body("request failed. input content is null!");
       }else if(dao.queryCs_id(question.getCs_id()) == 0){
          //if cs_id does not found.
          return ResponseEntity.badRequest().body("request failed. input ClassId not found!");
       }else if(userintheclass.queryStudentInTheClass(std_id, question.getCs_id()) == 0){
          //if student does not in this class.
          return ResponseEntity.badRequest().body("request failed. student does not in this class");
       }else{
            if(question.isQ_type() == true){
               //if student select personal question in this class.
               dao.studentinsert(question);
               question.getCs_id();
               writtenmessage = "student "+ std_id + " writing personal question " + question.getQ_content() + " in class " + question.getCs_id() + " .";
               logfile.writeLog(writtenmessage, question.getCs_id(), partition);
               return ResponseEntity.ok("personal request successful!");
            }else{
                //if student select public question in this class.
               dao.studentinsert(question);
               question.getCs_id();
               writtenmessage = "student "+ std_id + " writing public question " + question.getQ_content() + " in class " + question.getCs_id() + " .";
               logfile.writeLog(writtenmessage, question.getCs_id(), partition);
               return ResponseEntity.ok("public request successful!");
            }
       }
    }


  //student get all question in this class.
  //You will get q_id, q_std_id, q_content, q_reply, cs_id, cs_name, q_asktime, q_solved.
 @GetMapping(value = {"/student/question/all/{cs_id}"})
    public ResponseEntity<List<Question>> retrieveQuestionstudentview(@PathVariable("cs_id") final String cs_id) throws SQLException,
          IOException {
      // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      // System.out.println("in Login2" + authentication.getPrincipal());

      AuthenticationUtil auth = new AuthenticationUtil();
      String std_id = auth.getCurrentUserName();

      if(userintheclass.queryStudentInTheClass(std_id, cs_id) == 0){
         //if student does not belong to this class.
         return new ResponseEntity<List<Question>>(HttpStatus.BAD_REQUEST);
       }else{
         writtenmessage = "student \"" + std_id + "\" watching question in class \"" + cs_id + "\".";
         logfile.writeLog(writtenmessage, cs_id, partition);
         return new ResponseEntity<List<Question>>(dao.findQuestion(cs_id), HttpStatus.OK);
       }

    }

   //teacher get all question in this class.
   //You will get q_id, q_std_id, q_content, q_reply, cs_id, cs_name, q_asktime, q_solved.
 @GetMapping(value = {"/teacher/question/all/{cs_id}"})
    public ResponseEntity<List<Question>> retrieveQuestionteacherview(@PathVariable("cs_id") final String cs_id) throws SQLException,
          IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
       if(userintheclass.queryTeacherInTheClass(teacher_id, cs_id) == 0){
         return new ResponseEntity<List<Question>>(HttpStatus.BAD_REQUEST);
       }else{
         writtenmessage = "teacher that you watching question in class \"" + cs_id + "\".";
         logfile.writeLog(writtenmessage, cs_id, partition);
         return new ResponseEntity<List<Question>>(dao.findQuestion(cs_id), HttpStatus.OK);
       }
    }


   //update student's question in this class.
   //You have input q_asktime, q_content, q_std_id, cs_id.
 @PutMapping(value = "/student/question")
    public ResponseEntity<String> UpdateStudentQuestionContent(@RequestBody final Question question) throws SQLException,
          IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String std_id = auth.getCurrentUserName();
       if(dao.hasBeenReply(question.getQ_std_id(), question.getQ_asktime()) == 1){
         return ResponseEntity.badRequest().body("request failed. your question has already replied from teacher!");
       }else{
         dao.updateStudentQuestionContent(question);
         writtenmessage = "student \"" + std_id + "\" update question " + question.getQ_content() + " in class \"" + question.getCs_id() + "\" with question's asktime \"" + question.getQ_asktime() + "\".";
         logfile.writeLog(writtenmessage, question.getCs_id(), partition);
         return ResponseEntity.ok("request successful! your question update completed!");
       }
    }
   

    //update teacher's reply in this class.
    //You have input q_reply, cs_id, q_std_id, q_asktime.
 @PutMapping(value = "/teacher/question")
    public ResponseEntity<String> processFormUpdate(@RequestBody final Question question) throws SQLException,
          IOException {
      AuthenticationUtil auth = new AuthenticationUtil();   
      String teacher_id = auth.getCurrentUserName();

      if(userintheclass.queryTeacherInTheClass(teacher_id, question.getCs_id()) == 0){
         //if teacher not in this class.
         return ResponseEntity.badRequest().body("request failed. teacher not in this class!");
      }else if(userintheclass.queryStudentInTheClass(Integer.toString(question.getQ_std_id()), question.getCs_id()) == 0){
         //if input student not in this class.
         return ResponseEntity.badRequest().body("request failed. input student not in this class!");
      }else{
         dao.updateTeacherReply(question);
         writtenmessage = "teacher \"" + teacher_id + "\" reply question in class \"" + question.getCs_id() + "\" with question's asktime \"" + question.getQ_asktime() + "\", student \"" + question.getQ_std_id() + "\".";
         logfile.writeLog(writtenmessage, question.getCs_id(), partition);
         return ResponseEntity.ok("request successful! your reply update completed!");
      }
    }

   //student delete there question.
   //input q_std_id, q_asktime.
 @DeleteMapping(value = "/student/deletequestioncontent/")
    public ResponseEntity<String> StudentdeleteQuestion(@RequestBody final Question question) throws SQLException,
          IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      int std_id = Integer.parseInt(auth.getCurrentUserName());

      
      System.out.println("\n\n\n\n\n\n\n");
      System.out.println(std_id);
      System.out.println(question.getQ_std_id());
      System.out.println("\n\n\n\n\n\n\n");

      if(question.getQ_std_id() != std_id){
         //if the student don't have sufficient permissions to delete question.
         writtenmessage = "student \"" + question.getQ_std_id() + "\" does not have permission to delete question with student \"" + std_id + "\", question's asktime \"" + question.getQ_asktime() + "\"!";
         logfile.writeLog(writtenmessage);
         return ResponseEntity.badRequest().body("request failed. you can not delete other student's question, because you are not the question creator!");
      }else if(dao.hasQuestion(question.getQ_std_id(), question.getQ_asktime()) == 0){
         //if the question not found.
         return ResponseEntity.badRequest().body("request failed. thw question with asktime was not found!");
      }else if(dao.hasBeenReply(question.getQ_std_id(), question.getQ_asktime()) == 1){
         //if the question has been replied from teacher.
         return ResponseEntity.badRequest().body("request failed. you can not delete your question, because the question has already replied from teacher!");
      }else{
         dao.deleteQuestion(question);
         writtenmessage = "student \"" + std_id + "\" deleted question with question's asktime \"" + question.getQ_asktime() + "\".";
         logfile.writeLog(writtenmessage);
         return ResponseEntity.ok("request successful! your question has been deleted!");
      }
    }

   //teacher delete student's question.
   //input std_id, q_asktime.
 @DeleteMapping(value = "/teacher/deletequestioncontent/")
    public ResponseEntity<String> TeacherdeleteQuestion(@RequestBody final Question question) throws SQLException, IOException{
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
      if(dao.hasQuestion(question.getQ_std_id(), question.getQ_asktime()) == 0){
         //if the question not found.
         return ResponseEntity.badRequest().body("request failed. the question with asktime was not found!");
      }else{
         dao.deleteQuestion(question);
         writtenmessage = "teacher \"" + teacher_id + "\" deleted question with student \"" + question.getQ_std_id() + "\", question's asktime \"" + question.getQ_asktime() + "\".";
         logfile.writeLog(writtenmessage);
         return ResponseEntity.ok("request successful! the student \"" + question.getQ_std_id() + "\"'s question has been deleted!");
      }
    }
 

   //teacher delete there question's reply.
   //input std_id, q_asktime.
 @DeleteMapping(value = "/teacher/deletequestionreply/")
 public ResponseEntity<String> TeacherdeleteQuestionReply(@RequestBody final Question question) throws SQLException,
       IOException {
   AuthenticationUtil auth = new AuthenticationUtil();
   String teacher_id = auth.getCurrentUserName();
   if(dao.hasQuestion(question.getQ_std_id(), question.getQ_asktime()) == 0){
      //if the question not found.
      return ResponseEntity.badRequest().body("request failed. thw question with asktime was not found!");
   }else{
      dao.deleteQuestionReply(question);
      writtenmessage = "teacher \"" + teacher_id + "\" deleted question reply with student \"" + question.getQ_std_id() + "\", question's asktime \"" + question.getQ_asktime() + "\".";
      logfile.writeLog(writtenmessage);
      return ResponseEntity.ok("request successful! the student's \"" + question.getQ_std_id() + "\" question reply has been deleted!");
   }
 }
   

}




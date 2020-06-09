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
import org.springframework.web.bind.annotation.ResponseBody;

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
   
   //teacher post new messages in commentbox.
   //you will input q_id, cb_content, cs_id.
   @PostMapping(value = "/teacher/AddNewMessages") 
   public ResponseEntity<String> Teacheraddnewmessage(@RequestBody final Question question)
         throws SQLException, IOException, ParseException {

      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
   
      if(question.getCb_content() == ""){
         //if comment_box content is null.
         return ResponseEntity.badRequest().body("request failed. input content is null!");
      }else if(dao.checkTheQuestionHasBeenSolved(question.getQ_id()) == 1){
         return ResponseEntity.badRequest().body("request failed. this question has been solved can't add new messages!");
      }else{
         dao.TeacherAddNewMessages(question);
         writtenmessage = "teacher \"" + teacher_id + "\" comment question " + question.getCb_content() + " in class \"" + question.getCs_id() + "\" with question's asktime \"" + question.getCb_time() + "\".";
         logfile.writeLog(writtenmessage, question.getCs_id(), partition);
         return ResponseEntity.ok("request successful! your message has been successfully sent!");
      }  
   }

   //student post new messages in commentbox.
   //you will input q_id, cb_content, cs_id.
   @PostMapping(value = "/student/AddNewMessages") 
   public ResponseEntity<String> Studentaddnewmessage(@RequestBody final Question question)
         throws SQLException, IOException, ParseException {

      AuthenticationUtil auth = new AuthenticationUtil();
      int std_id = Integer.parseInt(auth.getCurrentUserName());
   
      if(question.getCb_content() == ""){
         //if comment_box content is null.
         return ResponseEntity.badRequest().body("request failed. input content is null!");
      }else if(dao.checkTheQuestionHasBeenSolved(question.getQ_id()) == 1){
         return ResponseEntity.badRequest().body("request failed. this question has been solved can't add new messages!");
      }else{
         dao.StudentAddNewMessages(question);
         writtenmessage = "student \"" + std_id + "\" comment question " + question.getCb_content() + " in class \"" + question.getCs_id() + "\" with question's asktime \"" + question.getCb_time() + "\".";
         logfile.writeLog(writtenmessage, question.getCs_id(), partition);
         return ResponseEntity.ok("request successful! your message has been successfully sent!");
      }  

   }

   // teacher post there question to db.
   // you will input q_content, cs_id.
   @PostMapping(value = "/teacher/question")
   public ResponseEntity<String> proccessTeacherQuestion(@RequestBody final Question question)
         throws SQLException, IOException, ParseException {

      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();

       if(question.getQ_content() == ""){
          //if question content is null.
          return ResponseEntity.badRequest().body("request failed. input content is null!");
       }else if(dao.queryCs_id(question.getCs_id()) == 0){
          //if cs_id does not found.
          return ResponseEntity.badRequest().body("request failed. input ClassId not found!");
       }else if(userintheclass.queryTeacherInTheClass(teacher_id, question.getCs_id()) == 0){
          //if teacher does not in this class.
          return ResponseEntity.badRequest().body("request failed. teacher does not in this class");
       }else{
         dao.teacherinsert(question);
         question.getCs_id();
         writtenmessage = "student "+ teacher_id + " writing question " + question.getQ_content() + " in class " + question.getCs_id() + " .";
         logfile.writeLog(writtenmessage, question.getCs_id(), partition);
         return ResponseEntity.ok("public request successful!");
       }
    }
   

   // student post there question to db.
   // you will input q_content, cs_id.
   @PostMapping(value = "/student/question")
   public ResponseEntity<String> proccessStudentQuestion(@RequestBody final Question question)
         throws SQLException, IOException, ParseException {

      AuthenticationUtil auth = new AuthenticationUtil();
      String std_id = auth.getCurrentUserName();

      if(dao.hasThisStudentInQuestion(std_id, question.getCs_id()) >= 1){
         question.setQ_asktime(dao.findQuestionAsktime(std_id, question.getCs_id()));
         //get the current time
         Date timenow = new Date(); 
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         //get the time of the last question
         String oldAsktime = question.getQ_asktime();
         //Convert the time of the last question to milliseconds
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
         dao.studentinsert(question);
         question.getCs_id();
         writtenmessage = "student "+ std_id + " writing question " + question.getQ_content() + " in class " + question.getCs_id() + " .";
         logfile.writeLog(writtenmessage, question.getCs_id(), partition);
         return ResponseEntity.ok("public request successful!");
       }
    }


   //student get all solved question in this class.
   //You will get q_id, q_std_id, q_content, cs_id, cs_name, q_asktime, q_solved.
    @GetMapping(value = {"/student/solvedquestion/all/{cs_id}"})
    public ResponseEntity<List<Question>> solvedQuestionstudentview(@PathVariable("cs_id") final String cs_id) throws SQLException,
          IOException {

      AuthenticationUtil auth = new AuthenticationUtil();
      String std_id = auth.getCurrentUserName();

      if(userintheclass.queryStudentInTheClass(std_id, cs_id) == 0){
         //if student does not belong to this class.
         return new ResponseEntity<List<Question>>(HttpStatus.BAD_REQUEST);
       }else{
         writtenmessage = "student \"" + std_id + "\" watching solved question in class \"" + cs_id + "\".";
         logfile.writeLog(writtenmessage, cs_id, partition);
         return new ResponseEntity<List<Question>>(dao.findSolvedQuestion(cs_id), HttpStatus.OK);
       }

    }

   //student get all unresolved question in this class.
   //You will get q_id, q_std_id, q_content, cs_id, cs_name, q_asktime, q_solved.
   @GetMapping(value = {"/student/unresolvedquestion/all/{cs_id}"})
   public ResponseEntity<List<Question>> UnresolvedQuestionstudentview(@PathVariable("cs_id") final String cs_id) throws SQLException,
         IOException {

     AuthenticationUtil auth = new AuthenticationUtil();
     String std_id = auth.getCurrentUserName();

     if(userintheclass.queryStudentInTheClass(std_id, cs_id) == 0){
        //if student does not belong to this class.
        return new ResponseEntity<List<Question>>(HttpStatus.BAD_REQUEST);
      }else{
        writtenmessage = "student \"" + std_id + "\" watching unresolved question in class \"" + cs_id + "\".";
        logfile.writeLog(writtenmessage, cs_id, partition);
        return new ResponseEntity<List<Question>>(dao.findUnresolvedQuestion(cs_id), HttpStatus.OK);
      }

   }

   //teacher get all solved question in this class.
   //You will get q_id, q_std_id, q_content, cs_id, cs_name, q_asktime, q_solved.
 @GetMapping(value = {"/teacher/solvedquestion/all/{cs_id}"})
    public ResponseEntity<List<Question>> solvedQuestionteacherview(@PathVariable("cs_id") final String cs_id) throws SQLException,
          IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
       if(userintheclass.queryTeacherInTheClass(teacher_id, cs_id) == 0){
         return new ResponseEntity<List<Question>>(HttpStatus.BAD_REQUEST);
       }else{
         writtenmessage = "teacher that you watching solved question in class \"" + cs_id + "\".";
         logfile.writeLog(writtenmessage, cs_id, partition);
         return new ResponseEntity<List<Question>>(dao.findSolvedQuestion(cs_id), HttpStatus.OK);
       }
    }

   //teacher get all unresolved question in this class.
   //You will get q_id, q_std_id, q_content, cs_id, cs_name, q_asktime, q_solved.
 @GetMapping(value = {"/teacher/unresolvedquestion/all/{cs_id}"})
    public ResponseEntity<List<Question>> unresolvedQuestionteacherview(@PathVariable("cs_id") final String cs_id) throws SQLException,
          IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
       if(userintheclass.queryTeacherInTheClass(teacher_id, cs_id) == 0){
         return new ResponseEntity<List<Question>>(HttpStatus.BAD_REQUEST);
       }else{
         writtenmessage = "teacher that you watching unresolved question in class \"" + cs_id + "\".";
         logfile.writeLog(writtenmessage, cs_id, partition);
         return new ResponseEntity<List<Question>>(dao.findUnresolvedQuestion(cs_id), HttpStatus.OK);
       }
    }


 //student get his question in this class.
 //you will get q_content, q_asktime.
 @GetMapping(value = {"/student/question/CheckHisQuestion/{cs_id}"})
    public ResponseEntity<List<Question>> findAllQuestionsThisStudentAsked(@PathVariable("cs_id") final String cs_id) throws SQLException,
          IOException {
   
      AuthenticationUtil auth = new AuthenticationUtil();
      String std_id = auth.getCurrentUserName();
      if(userintheclass.queryStudentInTheClass(std_id, cs_id) == 0){
         //if student does not belong to this class.
         return new ResponseEntity<List<Question>>(HttpStatus.BAD_REQUEST);
       }else{
         writtenmessage = "student \"" + std_id + "\" watching his question in class \"" + cs_id + "\".";
         logfile.writeLog(writtenmessage, cs_id, partition);
         return new ResponseEntity<List<Question>>(dao.findAllQuestionsThisStudentAsked(std_id, cs_id), HttpStatus.OK);
       }

    }

   //student get all messege int this question.
   //You will get std_id, cb_content, cb_time, cb_role, q_content, q_asktime.
   @GetMapping(value = {"/student/findAllmessageIntheQuestion/{cs_id}/{q_id}"})
   public ResponseEntity<List<Question>> findAllmessageIntheQuestion(@PathVariable("cs_id") final String cs_id, @PathVariable("q_id") final int q_id) throws SQLException,
         IOException {

      AuthenticationUtil auth = new AuthenticationUtil();
      String std_id = auth.getCurrentUserName();  

      if(userintheclass.queryStudentInTheClass(std_id, cs_id) == 0){
         //if student does not belong to this class.
         return new ResponseEntity<List<Question>>(HttpStatus.BAD_REQUEST);
      }else{
       writtenmessage = "student that you watching all messages in the question \"" + q_id + "\".";
       logfile.writeLog(writtenmessage, cs_id, partition);
       return new ResponseEntity<List<Question>>(dao.findAllmessageIntheQuestion(q_id), HttpStatus.OK);
      }
   }

   //teacher get all messege int this question.
   //You will get std_id, cb_content, cb_time, cb_role, q_content, q_asktime.
   @GetMapping(value = {"/teacher/findAllmessageIntheQuestion/{cs_id}/{q_id}"})
   public ResponseEntity<List<Question>> teacherfindAllmessageIntheQuestion(@PathVariable("cs_id") final String cs_id, @PathVariable("q_id") final int q_id) throws SQLException,
         IOException {

      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();  

      if(userintheclass.queryTeacherInTheClass(teacher_id, cs_id) == 0){
         //if teacher does not belong to this class.
         return new ResponseEntity<List<Question>>(HttpStatus.BAD_REQUEST);
      }else{
       writtenmessage = "teacher that you watching all messages in the question \"" + q_id + "\".";
       logfile.writeLog(writtenmessage, cs_id, partition);
       return new ResponseEntity<List<Question>>(dao.findAllmessageIntheQuestion(q_id), HttpStatus.OK);
      }
   }


   //update teacher's question solved in this class.
   //You have input cs_id, q_asktime.
   @PutMapping(value = "/teacher/CompletionQuestion")
   public ResponseEntity<String> UpdateTeacherCompletionQuestion(@RequestBody final Question question) throws SQLException,
         IOException {

     AuthenticationUtil auth = new AuthenticationUtil();
     int teacher_id = Integer.parseInt(auth.getCurrentUserName());

      if(dao.findQuestionSolved(question.getCs_id(), question.getQ_asktime()) == 1){
        return ResponseEntity.badRequest().body("request failed. your question has been solved from teacher!");
      }else{
        dao.TeacherCompletionQuestion(question);
        writtenmessage = "student \"" + teacher_id + "\" completion question in class \"" + question.getCs_id() + "\" with question's asktime \"" + question.getQ_asktime() + "\".";
        logfile.writeLog(writtenmessage, question.getCs_id(), partition);
        return ResponseEntity.ok("request successful! your question has been solved!");
      }
   }


   //update student's question solved in this class.
   //You have input q_std_id, q_asktime, cs_id.
   @PutMapping(value = "/student/CompletionQuestion")
   public ResponseEntity<String> UpdateStudentCompletionQuestion(@RequestBody final Question question) throws SQLException,
         IOException {

     AuthenticationUtil auth = new AuthenticationUtil();
     int std_id = Integer.parseInt(auth.getCurrentUserName());

   if(dao.hasQuestion(std_id, question.getQ_asktime()) == 0){
      //if the question not found.
      return ResponseEntity.badRequest().body("request failed. thw question with asktime was not found!");
   }else if(dao.hasBeenReply(std_id, question.getQ_asktime()) == 1){
        return ResponseEntity.badRequest().body("request failed. your question has been solved from teacher or student!");
   }else{
        dao.StudentCompletionQuestion(question);
        writtenmessage = "student \"" + std_id + "\" completion question in class \"" + question.getCs_id() + "\" with question's asktime \"" + question.getQ_asktime() + "\".";
        logfile.writeLog(writtenmessage, question.getCs_id(), partition);
        return ResponseEntity.ok("request successful! your question has been solved!");
      }
   }

   //update student's comment message in this class.
   //You have input q_id, cb_content, std_id, cb_time, cs_id.
   @PutMapping(value = "/student/updateCommentMessages")
   public ResponseEntity<String> UpdateStudentCommentBoxnContent(@RequestBody final Question question) throws SQLException,
         IOException {
     AuthenticationUtil auth = new AuthenticationUtil();
     int std_id = Integer.parseInt(auth.getCurrentUserName());

      if(dao.checkTheQuestionHasBeenSolved(question.getQ_id()) == 1){
        return ResponseEntity.badRequest().body("request failed. you can't update message your question has already replied from teacher or student!");
      }else if(question.getCb_content() == ""){
        return ResponseEntity.badRequest().body("request failed. comment_box content can't be empty");
      }else{
        dao.updateStudentCommentBoxContent(question);
        writtenmessage = "student \"" + std_id + "\" update commentbox content " + question.getCb_content() + " in class \"" + question.getCs_id() + "\" with comment box time \"" + question.getCb_time() + "\".";
        logfile.writeLog(writtenmessage, question.getCs_id(), partition);
        return ResponseEntity.ok("request successful! your messages update completed!");
      }
   }

   //update teacher comment message in this class.
   //You have input q_id, cb_content, cs_id ,cb_time.
   @PutMapping(value = "/teacher/updateCommentMessages")
   public ResponseEntity<String> UpdateTeacherCommentBoxnContent(@RequestBody final Question question) throws SQLException,
         IOException {
     AuthenticationUtil auth = new AuthenticationUtil();
     int teacher_id = Integer.parseInt(auth.getCurrentUserName());

      if(dao.checkTheQuestionHasBeenSolved(question.getQ_id()) == 1){
        return ResponseEntity.badRequest().body("request failed. you can't update message your question has already replied from teacher or student!");
      }else if(question.getCb_content() == ""){
        return ResponseEntity.badRequest().body("request failed. comment_box content can't be empty");
      }else{
        dao.updateTeacherCommentBoxContent(question);
        writtenmessage = "teacher \"" + teacher_id + "\" update commentbox content " + question.getCb_content() + " in class \"" + question.getCs_id() + "\" with comment box time \"" + question.getCb_time() + "\".";
        logfile.writeLog(writtenmessage, question.getCs_id(), partition);
        return ResponseEntity.ok("request successful! your messages update completed!");
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
   
   //student delete there question.
   //input q_std_id, q_asktime.
 @DeleteMapping(value = "/student/deletequestioncontent/")
    public ResponseEntity<String> StudentdeleteQuestion(@RequestBody final Question question) throws SQLException,
          IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      int std_id = Integer.parseInt(auth.getCurrentUserName());

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
 
   //student delete his messages.
   //input q_id, cb_time.
   @DeleteMapping(value = "/student/deleteStudentMessages/")
   public ResponseEntity<String> StudentdeleteMessage(@RequestBody final Question question) throws SQLException, IOException{
     AuthenticationUtil auth = new AuthenticationUtil();
     String std_id = auth.getCurrentUserName();
     
     if(dao.checkTheQuestionHasBeenSolved(question.getQ_id()) == 1){
        return ResponseEntity.badRequest().body("request failed. the question has been solved. you can't delete this message");
     }else{
      dao.deleteStudentMessages(question);
      writtenmessage = "student \"" + std_id + "\" deleted message with question \"" + question.getQ_id() + "\".";
      logfile.writeLog(writtenmessage);
      return ResponseEntity.ok("request successful! student deleted this message");
     }
   }

   //teacher delete his messages.
   //input q_id, cb_time.
   @DeleteMapping(value = "/teacher/deleteTeacherMessages/")
   public ResponseEntity<String> TeacherdeleteMessage(@RequestBody final Question question) throws SQLException, IOException{
     AuthenticationUtil auth = new AuthenticationUtil();
     String teacher_id = auth.getCurrentUserName();
     
     if(dao.checkTheQuestionHasBeenSolved(question.getQ_id()) == 1){
        return ResponseEntity.badRequest().body("request failed. the question has been solved. you can't delete this message");
     }else{
      dao.deleteTeacherMessages(question);
      writtenmessage = "teacher \"" + teacher_id + "\" deleted message with question \"" + question.getQ_id() + "\".";
      logfile.writeLog(writtenmessage);
      return ResponseEntity.ok("request successful! student deleted this message");
     }
   }
   

}




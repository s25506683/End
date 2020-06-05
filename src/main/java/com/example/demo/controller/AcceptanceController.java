package com.example.demo.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestBody;

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
   //you will input cs_id, hw_name, accept_state.
   @PostMapping(value = "/student/acceptance/")
   public ResponseEntity<String> processFormCreate(@RequestBody final Acceptance acceptance)
         throws SQLException, IOException {

      AuthenticationUtil auth = new AuthenticationUtil();
      acceptance.setStd_id(Integer.parseInt(auth.getCurrentUserName())); //將取出的值轉Int

      if(userintheclass.queryStudentInTheClass(Integer.toString(acceptance.getStd_id()), acceptance.getCs_id()) == 1){
         //if student in this class.

         
         if(dao.hasThisHomework(acceptance.getCs_id(), acceptance.getHw_name()) == 1){
            //if homework and classId exist.
            acceptance.setAccept_hw_id(dao.findHomeworkID(acceptance.getHw_name()));

            if(dao.hasHomeworkClosed(acceptance.getAccept_hw_id()) == 1){
               //if homework has closed by teacher.
               return ResponseEntity.badRequest().body("老師已經關閉此作業，無法排隊驗收或發問囉！");
            }

            if(dao.hasInLine(acceptance.getCs_id(), acceptance.getHw_name(), acceptance.getStd_id()) == 1){
               //if student 在排隊列表中.
               if(dao.hasRejectByTeacher(acceptance.getCs_id(), acceptance.getHw_name(), acceptance.getStd_id()) == 1){
                  //if student state is 2(被老師退回的狀態).
                  Acceptance rejectAcceptDetail = dao.getRejectAcceptance(acceptance.getCs_id(), acceptance.getHw_name(), acceptance.getStd_id());
                  
                  //將接到的 rejectAcceptDetail 輸入到 acceptance 中（將老師之前的 content, tag, score 複製起來).
                  acceptance.setAccept_content(rejectAcceptDetail.getAccept_content());
                  acceptance.setAccept_tag(rejectAcceptDetail.getAccept_tag());
                  acceptance.setAccept_score(rejectAcceptDetail.getAccept_score());

                  //更新學生在列隊中的排序.
                  dao.updateAcceptanceLine(acceptance);

                  if(acceptance.getAccept_state() == 0){
                     //student 要發問.
                     writtenmessage = "student \"" + acceptance.getStd_id() + "\" want to ask question in homework \"" + acceptance.getAccept_hw_id() + "\".";
                     logfile.writeLog(writtenmessage, acceptance.getCs_id(), partition);
                     return ResponseEntity.ok("重新登記排隊發問成功！");
   
                  }else if(acceptance.getAccept_state() == 1){
                     //student 要驗收.
                     writtenmessage = "student \"" + acceptance.getStd_id() + "\" want to accept in homework \"" + acceptance.getAccept_hw_id() + "\".";
                     logfile.writeLog(writtenmessage, acceptance.getCs_id(), partition);
                     return ResponseEntity.ok("重新登記排隊驗收成功！");
   
                  }else{
                     //exception.
                     return ResponseEntity.badRequest().body("input's accept_state error!");
                  }
               }

               if(dao.hasAcceptDone(acceptance.getCs_id(), acceptance.getHw_name(), acceptance.getStd_id()) == 1){
                  //if student 已經驗收完成.
                  return ResponseEntity.badRequest().body("你已經驗收完畢，請至\"驗收完成\"中查看成績。");

               }else{
                  //student 尚未驗收完成.
                  if(acceptance.getAccept_state() == 1){
                     //if student 想要驗收.
                        if(dao.hasInQuestionLine(acceptance.getCs_id(), acceptance.getHw_name(), acceptance.getStd_id()) == 1){
                           //if student 在發問列隊中.
                           return ResponseEntity.badRequest().body("你已經在發問列隊中，無法驗收！請先取消您的發問。");
                           
                        }else{
                           //if student 在驗收列表中.
                           return ResponseEntity.badRequest().body("你已經在驗收列隊中，無法重複驗收！");

                        }
      
                  }else if(acceptance.getAccept_state() == 0){
                     //if student 想要發問.
                     if(dao.hasInQuestionLine(acceptance.getCs_id(), acceptance.getHw_name(), acceptance.getStd_id()) == 1){
                        //if student 在發問列隊中.
                        return ResponseEntity.badRequest().body("你已經在發問列隊中，無法重複發問！");

                     }else{
                        //if student 在驗收列表中.
                        return ResponseEntity.badRequest().body("你已經在驗收列隊中，無法發問！請先取消您的驗收。");

                     }

                  }else{
                     //exception.
                     return ResponseEntity.badRequest().body("input's accept_state error!");
                  }
               }

            }else{
               //student 不在任何排隊中.
               dao.insertAcceptance(acceptance);

               if(acceptance.getAccept_state() == 0){
                  //student 要發問.
                  writtenmessage = "student \"" + acceptance.getStd_id() + "\" want to ask question in homework \"" + acceptance.getAccept_hw_id() + "\".";
                  logfile.writeLog(writtenmessage, acceptance.getCs_id(), partition);
                  return ResponseEntity.ok("登記排隊發問成功！");

               }else if(acceptance.getAccept_state() == 1){
                  //student 要驗收.
                  writtenmessage = "student \"" + acceptance.getStd_id() + "\" want to accept in homework \"" + acceptance.getAccept_hw_id() + "\".";
                  logfile.writeLog(writtenmessage, acceptance.getCs_id(), partition);
                  return ResponseEntity.ok("登記排隊驗收成功！");

               }else{
                  //exception.
                  return ResponseEntity.badRequest().body("input's accept_state error!");
               }
            }

         }else{
            //homework name 或 cs_id 找不到.
            return ResponseEntity.badRequest().body("homework or classId not exist!");
         }

      }else{
         //student not in this class.
         return ResponseEntity.badRequest().body("studnet not in this class!");
      }


   } 


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


//student get homework about content in this class.
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

   
   //teacher get homework about content in this class.
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

 //student get acceptance list about content in this class.
 //input cs_id, hw_name.
 //You will get accept_id, accept_std_id, std_name, accept_hw_id, accept_state, accept_time, accept_score, accept_done, hw_name, hw_content.
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

    
 //teacher get acceptance list about content in this class.
 //input cs_id, hw_name.
 //You will get accept_id, accept_std_id, std_name, accept_hw_id, accept_state, accept_time, accept_score, accept_content, accept_tag, accept_done, hw_name, hw_content.
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
   

 //student get Who is in this acceptance.
 //You will get login std_id.
    @GetMapping(value = {"/student/acceptance/std_id"})
    public ResponseEntity<Acceptance> findStudentInTheAccept() throws SQLException{
      AuthenticationUtil auth = new AuthenticationUtil();
      int std_id = Integer.parseInt(auth.getCurrentUserName());
      Acceptance acceptance = new Acceptance();
      acceptance.setStd_id(std_id);
      return new ResponseEntity<Acceptance>(acceptance,HttpStatus.OK);

   }


 //teacher reject student acceptance in this homework.
 //You have input std_id, accept_hw_id, accept_score, accept_content, accept_tag.
 @PutMapping(value = "/teacher/rejectAcceptance")
    public ResponseEntity<String> rejectAcceptance(@RequestBody Acceptance acceptance) throws SQLException,
          IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
      acceptance.setCs_id(dao.findCsID(acceptance.getAccept_hw_id()));

      if(dao.queryStudentInTheAcceptance(acceptance.getStd_id(),acceptance.getAccept_hw_id()) == 1){
         //if student in this homework(acceptance line).
         dao.rejectAcceptance(acceptance);
         writtenmessage = "teacher \"" + teacher_id + "\" reject student " + acceptance.getStd_id() + "'s in homework \"" + acceptance.getAccept_hw_id() +"\".";
         logfile.writeLog(writtenmessage, acceptance.getCs_id(), partition);
         return ResponseEntity.ok("已退回學生驗收");
      }else{
         //student not in this homework(acceptance line).
         return ResponseEntity.badRequest().body("此學生尚未排隊");
      } 
 
    }

    
 //teacher update student score in this homework (finish acceptance).
 //You have input std_id, accept_hw_id, accept_score, accept_content, accept_tag.
 @PutMapping(value = "/teacher/updateScore")
    public ResponseEntity<String> processFormUpdate(@RequestBody Acceptance acceptance) throws SQLException,
          IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
      acceptance.setCs_id(dao.findCsID(acceptance.getAccept_hw_id()));

      if(dao.queryStudentInTheAcceptance(acceptance.getStd_id(),acceptance.getAccept_hw_id()) == 1){
         //if student in this homework(acceptance line).
         dao.updateScore(acceptance);
         writtenmessage = "teacher \"" + teacher_id + "\" update score in homework \"" + acceptance.getAccept_hw_id() +"\".";
         logfile.writeLog(writtenmessage, acceptance.getCs_id(), partition);
         return ResponseEntity.ok("已修改成績");
      }else{
         //student not in this homework(acceptance line).
         return ResponseEntity.badRequest().body("此學生尚未排隊");
      } 
 
    }

   //teacher update student tag in this homework.
   //You have input std_id, accept_hw_id, accept_tag.
   @PutMapping(value = "/teacher/updateTag")
   public ResponseEntity<String> processUpdateTag(@RequestBody Acceptance acceptance) throws SQLException,
         IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
      acceptance.setCs_id(dao.findCsID(acceptance.getAccept_hw_id()));

      if(dao.queryStudentInTheAcceptance(acceptance.getStd_id(),acceptance.getAccept_hw_id()) == 1){
         //if student in acceptance.
         if(acceptance.getAccept_tag() < 2 && acceptance.getAccept_tag() > -1){
            //if input's accept_tag right.
            dao.updateTag(acceptance);
            writtenmessage = "teacher \"" + teacher_id + "\" update score in homework \"" + acceptance.getAccept_hw_id() +"\".";
            logfile.writeLog(writtenmessage, acceptance.getCs_id(), partition);

            if(acceptance.getAccept_tag() == 1){
               return ResponseEntity.ok("新增標記成功！");
            }else{
               return ResponseEntity.ok("取消標記成功！");
            }

         }else{
            //if input's accept_tag error.
            return ResponseEntity.badRequest().body("input's accept_tag out od range! Please enter 0 or 1.");
         }
         
      }else{
         return ResponseEntity.badRequest().body("此學生尚未排隊");
      } 

   }


 //teacher update homework in this homework.
 //You have input hw_name, hw_content, hw_id.
 @PutMapping(value = "/teacher/updateContent")
    public ResponseEntity<String> processUpdateContent(@RequestBody Acceptance acceptance) throws SQLException,
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

 //teacher closed this homework (all student can not accept this homework after proccess this api).
 //You have input hw_id.
 @PutMapping(value = "/teacher/closedHomework")
   public ResponseEntity<String> processClosedHomework(@RequestBody Acceptance acceptance) throws SQLException,
         IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
      acceptance.setCs_id(dao.findCsID(acceptance.getHw_id()));

      if(userintheclass.queryTeacherInTheClass(teacher_id, acceptance.getCs_id()) == 1){
         //if teacher in this class.
         dao.updateClosedHomework(acceptance);
         //尋找被退回的學生但老師已經有註記或是評分.
         String unAcceptRejectStudent = dao.findUnAcceptRejectStudent(acceptance.getHw_id());
         if(!unAcceptRejectStudent.equals("")){
            if(unAcceptRejectStudent.contains(",")){
               String[] unAcceptRejectStudent_arr = unAcceptRejectStudent.split(",");
               //將所有學生的state更改成1.
               for(String student : unAcceptRejectStudent_arr){
                  dao.updateFromRejectStateToAcceptdone(Integer.parseInt(student), acceptance.getHw_id());
                  writtenmessage = "student \"" + student + "\" from reject state to accept done in homework \"" + acceptance.getAccept_hw_id() +"\", because teacher closed this homework.";
                  logfile.writeLog(writtenmessage, acceptance.getCs_id(), partition);
               }
            }else{
               //將此學生的state更改成1.
               dao.updateFromRejectStateToAcceptdone(Integer.parseInt(unAcceptRejectStudent), acceptance.getHw_id());
               writtenmessage = "student \"" + unAcceptRejectStudent + "\" from reject state to accept done in homework \"" + acceptance.getAccept_hw_id() +"\", because teacher closed this homework.";
               logfile.writeLog(writtenmessage, acceptance.getCs_id(), partition);
            }

         }


         writtenmessage = "teacher \"" + teacher_id + "\" closed homework \"" + acceptance.getAccept_hw_id() +"\".";
         logfile.writeLog(writtenmessage, acceptance.getCs_id(), partition);
         return ResponseEntity.ok("關閉作業成功！");
      }else{
         //teacher not in this class.
         return ResponseEntity.badRequest().body("teacher not in this class!");
      } 

   }


 //teacher reopen this homework (all student can accept again this homework after proccess this api).
 //You have input hw_id.
 @PutMapping(value = "/teacher/REopenHomework")
   public ResponseEntity<String> processReOpenHomework(@RequestBody Acceptance acceptance) throws SQLException,
         IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
      acceptance.setCs_id(dao.findCsID(acceptance.getHw_id()));

      if(userintheclass.queryTeacherInTheClass(teacher_id, acceptance.getCs_id()) == 1){
         //if teacher in this class.
         dao.updateReopenHomework(acceptance);
         writtenmessage = "teacher \"" + teacher_id + "\" reopen homework \"" + acceptance.getAccept_hw_id() +"\".";
         logfile.writeLog(writtenmessage, acceptance.getCs_id(), partition);
         return ResponseEntity.ok("重新開啟作業成功！");
      }else{
         //teacher not in this class.
         return ResponseEntity.badRequest().body("teacher not in this class!");
      } 

   }
   
 //student delete there acceptance.   
 //input std_id, hw_name.
 @DeleteMapping(value = "/student/acceptance/deleteAcceptance")
    public ResponseEntity<String> deleteAcceptance(@RequestBody Acceptance acceptance) throws SQLException,
          IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      acceptance.setStd_id(Integer.parseInt(auth.getCurrentUserName()));
      acceptance.setAccept_hw_id(dao.findHomeworkID(acceptance.getHw_name()));
      acceptance.setCs_id(dao.findCsID(acceptance.getAccept_hw_id()));

      if(dao.queryStudentInTheAcceptance(acceptance.getStd_id(),acceptance.getAccept_hw_id()) == 0){
         //if student not in acceptance.
         return ResponseEntity.badRequest().body("此學生尚未點選驗收");
      }else if(dao.queryStudentAcceptDone(acceptance.getStd_id(), acceptance.isAccept_done(), acceptance.getAccept_hw_id()) == 0){
         //if acceptance accept done by teacher.
         return ResponseEntity.badRequest().body("老師已驗收完成無法取消驗收");

      }else{
         dao.deleteAcceptance(acceptance);
         writtenmessage = "student \"" + acceptance.getStd_id() + "\" deleted acceptance with homework ID \"" + acceptance.getAccept_hw_id() + "\".";
         logfile.writeLog(writtenmessage, acceptance.getCs_id(), partition);
         return ResponseEntity.ok("取消驗收成功");
      }      

    } 
 //student delete there acceptance.   
 //input std_id, hw_name.
 @DeleteMapping(value = "/teacher/acceptance/deleteAcceptance")
 public ResponseEntity<String> deleteAcceptanceByTeacher(@RequestBody Acceptance acceptance) throws SQLException,
       IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      acceptance.setTeacher_id(Integer.parseInt(auth.getCurrentUserName()));
      acceptance.setAccept_hw_id(dao.findHomeworkID(acceptance.getHw_name()));
      acceptance.setCs_id(dao.findCsID(acceptance.getAccept_hw_id()));

      if(dao.queryStudentInTheAcceptance(acceptance.getStd_id(),acceptance.getAccept_hw_id()) == 0){
         //if student not in acceptance.
         return ResponseEntity.badRequest().body("此學生尚未點選驗收");
      }else{
         dao.deleteAcceptance(acceptance);
         writtenmessage = "teacher \"" + acceptance.getTeacher_id() + "\" deleted student \"" + acceptance.getStd_id() + "\" acceptance with homework ID \"" + acceptance.getAccept_hw_id() + "\".";
         logfile.writeLog(writtenmessage, acceptance.getCs_id(), partition);
         return ResponseEntity.ok("刪除學生驗收成功");
      }      

   } 


 //teacher delete there homework.
 //input hw_name, hw_cs_id.
 @DeleteMapping(value = "/teacher/acceptance/deleteHomework")
    public ResponseEntity<String> deleteHomework(@RequestBody Acceptance acceptance) throws SQLException,
          IOException {
      
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
     
      dao.deleteHomework(acceptance);
      writtenmessage = "teacher \"" + teacher_id + "\" deleted homework with homework \"" + acceptance.getHw_name() + "\".";
      logfile.writeLog(writtenmessage, acceptance.getHw_cs_id(), partition);
      return ResponseEntity.ok("刪除作業完成!");

    }
    
 
}
   
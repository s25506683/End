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
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.CourseDAO;

//import org.springframework.web.servlet.ModelAndView;
import com.example.demo.entity.Course;
import com.example.demo.util.AuthenticationUtil;
import com.example.demo.util.Logfile;
import com.example.demo.util.UserInTheClass;

@RestController
public class CourseController {

  @Autowired
  CourseDAO dao;

  @Autowired
  Logfile logfile;

  @Autowired
  UserInTheClass userintheclass;

  String writtenmessage = new String();
  String partition = "Course(Class)";

  // teacher create a new class.
  // you will input cs_id, cs_name.
  @PostMapping(value = "/teacher/course/addclass/")
  public ResponseEntity<String> processFormCreate(@RequestBody Course course) throws SQLException, IOException {

    AuthenticationUtil auth = new AuthenticationUtil();
    int teacher_id = Integer.parseInt(auth.getCurrentUserName());

    if(course.getCs_id() == "" || course.getCs_name() == ""){
      //if input's classId or className value is null.
      return ResponseEntity.badRequest().body("request failed! ClassID and ClassName can not be null!");
    }else if(dao.hasSameClassId(course.getCs_id()) == 1){
      //if the classId has same with db.
      return ResponseEntity.badRequest().body("request failed! ClassID has already exist in database, please change a new ClassID");
    }else{
      dao.TeacherCreateCourse(course);
      dao.TeacherAddToClass(teacher_id, course.getCs_id());
      writtenmessage = "teacher " + teacher_id + " is already create the class \"" + course.getCs_name() + "\" with classID \"" + course.getCs_id() + "\".";
      logfile.writeLog(writtenmessage, course.getCs_id(), partition);
      return ResponseEntity.ok("request successful! the class has already create!");
    }
  }


  //student join the class.
  //you will input cs_qrcode(Sting).
  @PostMapping(value = "/student/course/joinclass/")
  public ResponseEntity<String> StudentJoinTheClass(@RequestBody Course course) throws SQLException, IOException {

    AuthenticationUtil auth = new AuthenticationUtil();
    int std_id = Integer.parseInt(auth.getCurrentUserName());

    if(dao.hasThisQRcode(course.getCs_qrcode()) == 0){
      //if the cs_qrcode not exist.
      return ResponseEntity.badRequest().body("request failed! this class QRcode not exist!");
    }else{
      course.setCs_id(dao.findThisCsId(course.getCs_qrcode()));
      dao.StudentJoinClass(std_id, course.getCs_id());
      writtenmessage = "student " + std_id + " join the class class \"" + dao.findClassName(course.getCs_id()) + "\" with classID \"" + course.getCs_id() + "\".";
      logfile.writeLog(writtenmessage, course.getCs_id(), partition);
      return ResponseEntity.ok("request successful! the class now add to your class list!");
    }

  }


  //teacher open join this course.
  //you will input cs_id, cs_qrcode.
  @PutMapping(value = "/teacher/course/openToJoin/")
    public ResponseEntity<String> TeacherOpenToJoinThisClass(@RequestBody Course course) throws SQLException,
        IOException {
        AuthenticationUtil auth = new AuthenticationUtil();
        String teacher_id = auth.getCurrentUserName();

        if(dao.hasSameClassId(course.getCs_id()) == 1){
          //if the classId exist.
          if(userintheclass.queryTeacherInTheClass(teacher_id, course.getCs_id()) == 1){
            //if teacher in the class.
            dao.updateCsQRcode(course.getCs_id(), course.getCs_qrcode());
            writtenmessage = "teacher " + teacher_id + " is open QRcode(Join the class) in class \"" + dao.findClassName(course.getCs_id()) + "\" with classID \"" + course.getCs_id() + "\".";
            logfile.writeLog(writtenmessage, course.getCs_id(), partition);
            return ResponseEntity.ok("request successful! the class now can let student to join this class!");
          }else{
            return ResponseEntity.badRequest().body("request failed! teacher not in this class!");
          }
          
        }else{
          //the class not exist.
          return ResponseEntity.badRequest().body("request failed! this classId not exist!");
        }
    }

    
  //teacher close join this course.
  //you will input cs_id.
  @PutMapping(value = "/teacher/course/closeToJoin/")
    public ResponseEntity<String> TeacherCloseToJoinThisClass(@RequestBody Course course) throws SQLException,
        IOException {
        AuthenticationUtil auth = new AuthenticationUtil();
        String teacher_id = auth.getCurrentUserName();

        if(dao.hasSameClassId(course.getCs_id()) == 1){
          //if the classId exist.
          if(userintheclass.queryTeacherInTheClass(teacher_id, course.getCs_id()) == 1){
            //if teacher in the class.
            dao.closeJoinClass(course.getCs_id());
            writtenmessage = "teacher " + teacher_id + " is closed QRcode(Join the class) in class \"" + dao.findClassName(course.getCs_id()) + "\" with classID \"" + course.getCs_id() + "\".";
            logfile.writeLog(writtenmessage, course.getCs_id(), partition);
            return ResponseEntity.ok("request successful! the class now closed to join this class!");
          }else{
            return ResponseEntity.badRequest().body("request failed! teacher not in this class!");
          }
          
        }else{
          //the class not exist.
          return ResponseEntity.badRequest().body("request failed! this classId not exist!");
        }
    }


  //teacher watch student list in this class.
  //you will get std_id, std_name, std_department returns.
  @GetMapping(value = {"/teacher/Course/studentList/{cs_id}"})                        
  public ResponseEntity<List<Course>> TeacherretrieveWhatStuentInTheClass(@PathVariable("cs_id") final String cs_id) throws SQLException{
   AuthenticationUtil auth = new AuthenticationUtil();
   String teacher_id = auth.getCurrentUserName();
   if(userintheclass.queryTeacherInTheClass(teacher_id, cs_id) == 0){
      //if teacher not in this class.
     return new ResponseEntity<List<Course>>(HttpStatus.BAD_REQUEST);
   }else{
     return new ResponseEntity<List<Course>>(dao.findClassStudentList(cs_id), HttpStatus.OK);
   }
  }

  //student watch student list in this class.
  //you will get std_id, std_name, std_department returns.
  @GetMapping(value = {"/student/Course/studentList/{cs_id}"})                        
  public ResponseEntity<List<Course>> StudentretrieveWhatStuentInTheClass(@PathVariable("cs_id") final String cs_id) throws SQLException{
   AuthenticationUtil auth = new AuthenticationUtil();
   String std_id = auth.getCurrentUserName();
   if(userintheclass.queryStudentInTheClass(std_id, cs_id) == 0){
      //if student not in this class.
     return new ResponseEntity<List<Course>>(HttpStatus.BAD_REQUEST);
   }else{
     return new ResponseEntity<List<Course>>(dao.findClassStudentList(cs_id), HttpStatus.OK);
   }
  }



 //老師新增課程（輸入課程名稱、課程ID）POST (done)
 //老師開放加入課程（cs_id, cs_qrcode）PUT (done)
 //學生加入課程（cs_qrcode）POST (done)
 //老師關閉加入課程（cs_qrcode）PUT


}




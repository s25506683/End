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




  @PostMapping(value = "/teacher/Course/newclass")
  public ResponseEntity<String> processFormCreate(@RequestBody final Course course) throws SQLException{

    if(course.getCs_id() == "" || course.getCs_name() == ""){
      return ResponseEntity.badRequest().body("作業ID與名稱不能為空");
    }

    dao.TeacherNewCourse(course);
    return ResponseEntity.ok("新增課程成功!!");
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



 
}




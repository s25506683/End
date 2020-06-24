package com.example.demo.controller;

import java.sql.SQLException;
import java.util.List;

//import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dao.HomePage1_sDAO;
import com.example.demo.entity.HomePage1_s;
import com.example.demo.util.AuthenticationUtil;



@RestController
public class HomePage1_sController {
  @Autowired
  HomePage1_sDAO dao;
  
  //AuthenticationUtil auth = new AuthenticationUtil();
//  @PostMapping(value = "/question")
//     public void processFormCreate(@RequestBody HomePage1_s homepage1_s) throws SQLException {
//        dao.insert(homepage1_s);
//     }
    //@POST


  //check login user's role.
  //'1' is teacher, '0' is student.
 @GetMapping(value = {"/CheckUserRole/"})
    public ResponseEntity<String> checkTheUserRole() throws SQLException{

      AuthenticationUtil auth = new AuthenticationUtil();
      int user_id = Integer.parseInt(auth.getCurrentUserName());

      if(dao.queryUserRole(user_id) == 1){
        //1 is teacher.
        return ResponseEntity.ok("1");
      }else{
        //0 is student.
        return ResponseEntity.ok("0");
      }
      
    }

  //check login user's role(get JSON return).
  //'1' is teacher, '0' is student（JSON return）.
 @GetMapping(value = {"/CheckUserRoleInJSONReturn/"})
 public HomePage1_s checkTheUserRoleByJSON() throws SQLException{

   AuthenticationUtil auth = new AuthenticationUtil();
   int user_id = Integer.parseInt(auth.getCurrentUserName());

   return dao.queryUserRoleByJson(user_id);
   
 }

 //student get his all class.
 //you will get cs_id, cs_name, cs_photo, teacher_name, std_id, std_name.
 @GetMapping(value = {"/student/HomePage1_s/one/"})
    public List<HomePage1_s> retrieveStudentCourse() throws SQLException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String std_id = auth.getCurrentUserName();
      System.out.println(std_id);
      return dao.findStudentCourse(Integer.parseInt(std_id));
      
    }

    //teacher get his all class.
    //you will get cs_id, cs_name, cs_photo, teacher_name, teacher_id, std_count(這堂課所有的學生).
  @GetMapping(value = {"/teacher/HomePage1_s/one/"})
    public List<HomePage1_s> retrieveTeacherCourse() throws SQLException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
      return dao.findTeacherCourse(Integer.parseInt(teacher_id));
    }
    
 @GetMapping(value = {"/HomePage1_s/all"})   
    public List<HomePage1_s> retrieveAllCourse() throws SQLException{
       return dao.findAllCourse();
    }

    
 
}




package com.example.demo.controller;

import java.sql.SQLException;
import java.util.List;

//import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
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


 @GetMapping(value = {"/CheckUserRole"})
    public List<HomePage1_s> checkTheUserRole() throws SQLException{


      AuthenticationUtil auth = new AuthenticationUtil();
      int user_id = Integer.parseInt(auth.getCurrentUserName());

      return dao.queryUserRole(user_id);
      
    }
 

    
 @GetMapping(value = {"/student/HomePage1_s/one/"})
    public List<HomePage1_s> retrieveStudentCourse() throws SQLException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String std_id = auth.getCurrentUserName();
      System.out.println(std_id);
      return dao.findStudentCourse(Integer.parseInt(std_id));
      
    }
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


    
    
//  @PutMapping(value = "/question")
//     public void processFormUpdate(@RequestBody HomePage1_s homepage1_s) throws SQLException {
//        dao.update(homepage1_s);
//     }

//  @DeleteMapping(value = "/question/{id}")
//     public void deleteQuestion(@PathVariable("id") int id) {
//        dao.delete(id);
//     }
    
 
}




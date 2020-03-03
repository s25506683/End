package com.example.demo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
public class HomePage1_sController {
  @Autowired
  HomePage1_sDAO dao;

//  @PostMapping(value = "/question")
//     public void processFormCreate(@RequestBody HomePage1_s homepage1_s) throws SQLException {
//        dao.insert(homepage1_s);
//     }
    //@POST
 @GetMapping(value = {"/HomePage1_s/one/{std_id}"})
    public List<HomePage1_s> retrieveOneQuestion(@PathVariable("std_id") final int std_id) throws SQLException {
       return dao.findStudentCourse(std_id);
    }
    
 @GetMapping(value = {"/HomePage1_s/all"})
    public List<HomePage1_s> retrieveQuestion() throws SQLException{
      //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      //String password = passwordEncoder.encode("12345");
      //System.out.println(password.length() + "\n" + password);
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




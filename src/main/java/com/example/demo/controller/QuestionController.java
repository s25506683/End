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
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;

//import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.QuestionDAO;
import com.example.demo.entity.Question;

@RestController
public class QuestionController {
  @Autowired
  QuestionDAO dao;

 @PostMapping(value = "/question")
    public void processFormCreate(@RequestBody Question question) throws SQLException {
       dao.insert(question);
    }
    //@POST
 @GetMapping(value = {"/student/question/one/{cs_id}/{std_id}"})
    public Question retrieveOneQuestion(@PathVariable("cs_id") String cs_id, @PathVariable("std_id") int std_id) throws SQLException{
       return dao.findOne(cs_id,std_id);
    }
    
 @GetMapping(value = {"/student/question/all/{cs_id}"})
    public List<Question> retrieveQuestion(@PathVariable("cs_id") String cs_id) throws SQLException{
       return dao.findAll(cs_id);
    }
    
 @PutMapping(value = "/question")
    public void processFormUpdate(@RequestBody Question question) throws SQLException {
       dao.update(question);
    }

 @DeleteMapping(value = "/question/{id}")
    public void deleteQuestion(@PathVariable("id") int id) {
       dao.delete(id);
    }
 
}




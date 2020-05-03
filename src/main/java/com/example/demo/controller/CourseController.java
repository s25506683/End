package com.example.demo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
public class CourseController {
  @Autowired
  CourseDAO dao;


@PostMapping(value = "/teacher/Course/newclass")
  public ResponseEntity<String> processFormCreate(@RequestBody final Course course) throws SQLException{

    if(course.getCs_id() == "" || course.getCs_name() == ""){
      return ResponseEntity.badRequest().body("作業ID與名稱不能為空");
    }
    return ResponseEntity.ok("新增課程成功!!");
  }


 
}




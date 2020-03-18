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

import com.example.demo.dao.RollcallDAO;
import com.example.demo.entity.Rollcall;

@RestController
public class RollcallController {
  @Autowired
  RollcallDAO dao;

  @PostMapping(value = "/teacher/rollcall/addrollcall/")
     public void processFormCreate(@RequestBody Rollcall rollcall) throws SQLException {
        dao.addrollcall(rollcall);
     }
    //@POST
 @GetMapping(value = {"/rollcall/one/{rc_id}"})
    public List<Rollcall> retrieveOneRollcall(@PathVariable("rc_id") final String rc_id) throws SQLException {
       return dao.findOneRollcallRecord(rc_id);
    }
    
 @GetMapping(value = {"/rollcall/{cs_id}/all"})
    public List<Rollcall> retrieveRollcall(@PathVariable("cs_id") final String cs_id) throws SQLException{
       return dao.findAllRollcallRecord(cs_id);
    }
    
//  @PutMapping(value = "/rollcall")
//     public void processFormUpdate(@RequestBody HomePage1_s homepage1_s) throws SQLException {
//        dao.update(homepage1_s);
//     }

//  @DeleteMapping(value = "/rollcall/{id}")
//     public void deleteQuestion(@PathVariable("id") int id) {
//        dao.delete(id);
//     }
    
 
}




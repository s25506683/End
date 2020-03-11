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

import com.example.demo.dao.AcceptanceDAO;
import com.example.demo.entity.Acceptance;

@RestController
public class AcceptanceController {
  @Autowired
  AcceptanceDAO dao;	

 @PostMapping(value = "/acceptance")
    public void processFormCreate(@RequestBody Acceptance acceptance) throws SQLException {
       dao.insert(acceptance);
    }
    //@POST
 @GetMapping(value = {"/acceptance/{id}"})
    public Acceptance retrieveOneAcceptance(@PathVariable("id") int id) throws SQLException{
       return dao.findOne(id);
    }
    
 @GetMapping(value = {"/acceptance/hw/{cs_id}/{hw_name}"})
    public List<Acceptance> retrieveAcceptance(@PathVariable("cs_id") String cs_id, @PathVariable("hw_name") String hw_name) throws SQLException{
       return dao.findAll(cs_id,hw_name);
    }
    
 @PutMapping(value = "/acceptance")
    public void processFormUpdate(@RequestBody Acceptance acceptance) throws SQLException {
       dao.update(acceptance);
    }

 @DeleteMapping(value = "/acceptance/{id}")
    public void deleteAcceptance(@PathVariable("id") int id) {
       dao.delete(id);
    }
 
}




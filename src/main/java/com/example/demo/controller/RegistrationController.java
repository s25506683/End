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

import com.example.demo.dao.RegistrationDAO;
import com.example.demo.entity.Registration;	

@RestController
public class RegistrationController {
  @Autowired
  RegistrationDAO dao;

 @PostMapping(value = "/registration")
    public void processFormCreate(@RequestBody Registration registration) throws SQLException {
       dao.insert(registration);
    }
    //@POST
 @GetMapping(value = {"/registration/{std_id}"})
    public Registration retrieveOneRegsister(@PathVariable("std_id") int std_id) throws SQLException{
       return dao.findOne(std_id);
    }
    
 @GetMapping(value = {"/registration"})
    public List<Registration> retrieveRegistration() throws SQLException{
       return dao.findAll();
    }
    
 @PutMapping(value = "/registration")
    public void processFormUpdate(@RequestBody Registration registration) throws SQLException {
       dao.update(registration);
    }

 @DeleteMapping(value = "/registration/{std_id}")
    public void deleteRegistration(@PathVariable("std_id") int std_id) {
       dao.delete(std_id);
    }
 
}




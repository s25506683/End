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

import com.example.demo.dao.CustomerDAO;
import com.example.demo.entity.Customer;

@RestController
public class CustomerController {
  @Autowired
 CustomerDAO dao;

 @PostMapping(value = "/customer")
    public void processFormCreate(@RequestBody Customer customer) throws SQLException {
       dao.insert(customer);
    }
    //@POST
 @GetMapping(value = {"/customer/{id}"})
    public Customer retrieveOneCustomer(@PathVariable("id") Long id) throws SQLException{
       return dao.findOne(id);
    }
    
 @GetMapping(value = {"/customer"})
    public List<Customer> retrieveCustomers() throws SQLException{
       return dao.findAll();
    }
    
 @PutMapping(value = "/customer")
    public void processFormUpdate(@RequestBody Customer customer) throws SQLException {
       dao.update(customer);
    }

 @DeleteMapping(value = "/customer/{id}")
    public void deleteCustomer(@PathVariable("id") Long id) {
       dao.delete(id);
    }
 
}




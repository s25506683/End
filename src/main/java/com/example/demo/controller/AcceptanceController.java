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

//import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.AcceptanceDAO;
import com.example.demo.entity.Acceptance;

@RestController
public class AcceptanceController {
  @Autowired
  AcceptanceDAO dao;

 @PostMapping(value = "/student/acceptance")
    public ResponseEntity<String> processFormCreate(@RequestBody Acceptance acceptance) throws SQLException {

      if(dao.queryUser(acceptance.getAccept_std_id(),acceptance.getAccept_hw_id()) == 0){

         dao.insert(acceptance);
         return ResponseEntity.ok("登記驗收成功");

      }else{
         return ResponseEntity.badRequest().body("您已驗收過");
      }
      
    
   }
    //@POST
 @GetMapping(value = {"/acceptance/{accept_std_id}"})
    public Acceptance retrieveOneAcceptance(@PathVariable("accept_std_id") int accept_std_id) throws SQLException{
       return dao.findOne(accept_std_id);
    }
    
 @GetMapping(value = {"/acceptance/hw/{cs_id}/{hw_name}"})
    public List<Acceptance> retrieveAcceptance(@PathVariable("cs_id") String cs_id, @PathVariable("hw_name") String hw_name) throws SQLException{
       return dao.findAll(cs_id,hw_name);
    }
    
    @PutMapping(value = "/teacher/acceptance")
    public ResponseEntity<String> processFormUpdate(@RequestBody Acceptance acceptance) throws SQLException {
      
      

       if(dao.queryUser(acceptance.getAccept_std_id(),acceptance.getAccept_hw_id()) == 1){

         dao.update(acceptance);
         return ResponseEntity.ok("已修改成績");

      }else{
         return ResponseEntity.badRequest().body("此學生尚未驗收");
      }


      // Acceptance acceptDone = new Acceptance();
      // acceptDone = dao.findOne(acceptance.getAccept_std_id()); 
      // System.out.println(acceptDone.isAccept_done());

      // //isAccept_done()的 0是false,1是true.
      // if(!acceptDone.isAccept_done()){

      //    dao.update(acceptance);
      //    return ResponseEntity.ok("成功登記驗收"); 

      // }else{
      //    return ResponseEntity.ok("你已登記驗收"); 



    }
 



 @DeleteMapping(value = "/acceptance/{id}")
    public void deleteAcceptance(@PathVariable("id") int id) {
       dao.delete(id);
    }
 
}

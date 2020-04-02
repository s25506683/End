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
import org.springframework.web.HttpRequestMethodNotSupportedException;

//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;

//import org.springframework.web.servlet.ModelAndView;
import com.example.demo.dao.AcceptanceDAO;
import com.example.demo.entity.Acceptance;
import com.example.demo.util.AuthenticationUtil;

@RestController
public class AcceptanceController {
  @Autowired
  AcceptanceDAO dao;

 @PostMapping(value = "/student/acceptance")
    public ResponseEntity<String> processFormCreate(@RequestBody final Acceptance acceptance) throws SQLException {

         if(dao.queryUser(acceptance.getAccept_std_id(),acceptance.getAccept_hw_id()) == 0){

            dao.insertAcceptance(acceptance);
            return ResponseEntity.ok("登記驗收成功");
   
         }else{
            return ResponseEntity.badRequest().body("您已驗收過");
         }

            
   }
    //@POST

@PostMapping(value = "/teacher/acceptance/homework")
   public void processFormCreate2(@RequestBody final Acceptance acceptance) throws SQLException{

      // Acceptance homework = new Acceptance();
      // homework = dao.insertHomework(acceptance.getAccept_std_id()); 

      dao.insertHomework(acceptance);
   }


//  Acceptance acceptDone = new Acceptance();
//        acceptDone = dao.findOne(acceptance.getAccept_std_id()); 
      // System.out.println(acceptDone.isAccept_done());

      // //isAccept_done()的 0是false,1是true.
      // if(!acceptDone.isAccept_done()){

      //    dao.update(acceptance);
      //    return ResponseEntity.ok("成功登記驗收"); 

      // }else{
      //    return ResponseEntity.ok("你已登記驗收");



 @GetMapping(value = {"/student/acceptance/{hw_cs_id}"})
    public List<Acceptance> retrieveOneAcceptance(@PathVariable("hw_cs_id") final String hw_cs_id) throws SQLException{
      AuthenticationUtil auth = new AuthenticationUtil();
      String std_id = auth.getCurrentUserName(); 
      return dao.findCourseHomework(Integer.parseInt(std_id),hw_cs_id);
    }
    
 @GetMapping(value = {"/student/acceptance/hw/{cs_id}/{hw_name}"})
    public List<Acceptance> retrieveAcceptance(@PathVariable("cs_id") final String cs_id, @PathVariable("hw_name") final String hw_name) throws SQLException{
       return dao.findHomeworkDetail(cs_id,hw_name);
    }

    
 @PutMapping(value = "/teacher/updateScore")
    public ResponseEntity<String> processFormUpdate(@RequestBody final Acceptance acceptance) throws SQLException {
      
      if(dao.queryUser(acceptance.getAccept_std_id(),acceptance.getAccept_hw_id()) == 1){

         dao.updateScore(acceptance);
         return ResponseEntity.ok("已修改成績");
         
      }else{
         return ResponseEntity.badRequest().body("此學生尚未驗收");
      } 

    }

 @PutMapping(value = "/teacher/updateContent/")
    public ResponseEntity<String> processFormUpdate2(@RequestBody final Acceptance acceptance) throws SQLException{
       dao.updateContent(acceptance);
       return ResponseEntity.ok("修改成功");
    }

 @DeleteMapping(value = "/acceptance/{id}/{accept_hw_id}")
    public void deleteAcceptance(@PathVariable("id") final int id, @PathVariable("accept_hw_id") final int accept_hw_id) {
       dao.delete(id,accept_hw_id);
    }
 
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

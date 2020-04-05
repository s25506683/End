package com.example.demo.controller;

import java.sql.SQLException;
import java.util.List;

import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
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

         if(dao.queryStudentInTheAcceptance(acceptance.getAccept_std_id(),acceptance.getAccept_hw_id()) == 0){

            dao.insertAcceptance(acceptance);
            return ResponseEntity.ok("登記驗收成功");
   
         }else{
            return ResponseEntity.badRequest().body("您已驗收過");
         }

            //新增驗收
   } 
    //@POST

@PostMapping(value = "/teacher/acceptance/homework")
   public ResponseEntity<String> processFormCreate2(@RequestBody final Acceptance acceptance) throws SQLException{

      if(acceptance.getHw_content() == ""){
         return ResponseEntity.badRequest().body("作業內容不得為空");
      }else if(dao.queryHomeworkInTheClass(acceptance.getHw_name(), acceptance.getHw_cs_id()) == 1){
         return ResponseEntity.badRequest().body("這堂課已有此作業，請更改作業名稱");
      }else{
         dao.insertHomework(acceptance);
         return ResponseEntity.ok("新增作業成功");
      }


     

      //新增作業
   }



 @GetMapping(value = {"/student/acceptance/{hw_cs_id}"})
    public List<Acceptance> retrieveOneAcceptance(@PathVariable("hw_cs_id") final String hw_cs_id) throws SQLException{
      // AuthenticationUtil auth = new AuthenticationUtil();
      // String std_id = auth.getCurrentUserName(); 
      return dao.findCourseHomework(hw_cs_id); 
    }


 @GetMapping(value = {"/student/acceptance/hw/{cs_id}/{hw_name}"})
    public List<Acceptance> retrieveAcceptance(@PathVariable("cs_id") final String cs_id, @PathVariable("hw_name") final String hw_name) throws SQLException{
       return dao.findHomeworkDetail(cs_id,hw_name); //
    }

    
 @PutMapping(value = "/teacher/updateScore")
    public ResponseEntity<String> processFormUpdate(@RequestBody final Acceptance acceptance) throws SQLException {
      
      if(Integer.toString(acceptance.getAccept_score()) == ""){
         return ResponseEntity.badRequest().body("請輸入分數，分數不得為空值");
      }else if(dao.queryStudentInTheAcceptance(acceptance.getAccept_std_id(),acceptance.getAccept_hw_id()) == 1){

         dao.updateScore(acceptance);
         return ResponseEntity.ok("已修改成績");
         
      }else{
         return ResponseEntity.badRequest().body("此學生尚未驗收");
      } 

    }

 @PutMapping(value = "/teacher/updateContent")
    public ResponseEntity<String> processFormUpdate2(@RequestBody final Acceptance acceptance) throws SQLException{
       
      if(acceptance.getHw_content() == ""){
         return ResponseEntity.badRequest().body("作業內容不得為空");
      }else if(dao.queryHomeworkInTheClass(acceptance.getHw_name(), acceptance.getHw_cs_id()) == 0){
         return ResponseEntity.badRequest().body("無此作業，請先新增作業");
      }
       dao.updateContent(acceptance);
       return ResponseEntity.ok("修改成功");
      
    }

 @DeleteMapping(value = "/acceptance/{id}/{accept_hw_id}")
    public void deleteAcceptance(@PathVariable("id") final int id, @PathVariable("accept_hw_id") final int accept_hw_id) {
       dao.delete(id,accept_hw_id);
    }
 
}
   
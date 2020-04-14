package com.example.demo.controller;

import java.sql.SQLException;
import java.util.List;

import com.example.demo.dao.TakeleaveDAO;
import com.example.demo.entity.Takeleave;
import com.example.demo.util.AuthenticationUtil;
import com.example.demo.util.Logfile;

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



@RestController
public class TakeleaveController {

  @Autowired
  TakeleaveDAO dao;

  @Autowired
  Logfile logfile;

  String writtenmessage = new String();


  @GetMapping(value = {"/student/takeleave/all"})
    public List<Takeleave> retrieveTakeleave() throws SQLException{
       return dao.findAll();
    }


  @PostMapping(value = {"/student/takeleave"})
    public ResponseEntity<String> processFormCreate(@RequestBody final Takeleave takeleave) throws SQLException{

      AuthenticationUtil auth = new AuthenticationUtil();
      takeleave.setStd_id(Integer.parseInt(auth.getCurrentUserName()));
      //takeleave.setRc_id(takeleave.getRc_id());

      if(dao.queryStudentInTakeleave(takeleave.getRc_id(), takeleave.getStd_id()) == 0){

        //System.out.println(takeleave.getRc_id() + "\n" + takeleave.getStd_id());
        dao.Applyforleave(takeleave);
       
        takeleave.getRc_id();
        writtenmessage = "student \"" + takeleave.getStd_id() + "\" apply takeleave in rollcall \"" + takeleave.getRc_id() + "\".";
        logfile.writeLog(writtenmessage);
        return ResponseEntity.ok("申請成功");
       

      }else{
        return ResponseEntity.badRequest().body("你已申請過請假，請耐心等待老師的回覆");
         //學生申請請假
      }


    // @PutMapping(value = {"/teacher/takeleave"})
    //   public ResponseEntity<String> 




      
    }





 
}




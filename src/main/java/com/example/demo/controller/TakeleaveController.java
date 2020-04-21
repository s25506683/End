package com.example.demo.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import com.example.demo.dao.TakeleaveDAO;
import com.example.demo.entity.Takeleave;
import com.example.demo.util.AuthenticationUtil;
import com.example.demo.util.Logfile;
import com.example.demo.util.UserInTheClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
  UserInTheClass userintheclass;

  @Autowired
  TakeleaveDAO dao;

  @Autowired
  Logfile logfile;

  String writtenmessage = new String();
  String partition = "Takeleave";


  @GetMapping(value = {"/teacher/takeleave/AllStudent/{cs_id}"}) //教師看到所有學生的請假申請
    public ResponseEntity<List<Takeleave>> retrieveAllTakeleave(@PathVariable("cs_id") final String cs_id) throws SQLException{

      // AuthenticationUtil auth = new AuthenticationUtil();
      // String teacher_id = auth.getCurrentUserName();
      // writtenmessage = "teacher \"" + teacher_id + "\" watching student takeleave record in class \"";
      // logfile.writeLog(writtenmessage, Takeleave.getCs_id, partition);
      return new ResponseEntity<List<Takeleave>>(dao.findTakeleaveInTheClass(cs_id), HttpStatus.OK);

    }

  
  @GetMapping(value = {"/student/takeleave/TakeleaveRecord/{cs_id}"}) //get學生的全部請假紀錄
  public ResponseEntity<List<Takeleave>> retrieveTakeleaveRecord(@PathVariable("cs_id") final String cs_id) throws SQLException{

    AuthenticationUtil auth = new AuthenticationUtil();
    String std_id = auth.getCurrentUserName(); 
    return new ResponseEntity<List<Takeleave>>(dao.findStudentTakeleaveRecord(std_id,cs_id), HttpStatus.OK);

    }

  @GetMapping(value = {"/student/takeleave/StudentAbsence"}) //學生查看自己的缺課紀錄
  public ResponseEntity<List<Takeleave>> retrieveStudentAbsence() throws SQLException{

    AuthenticationUtil auth = new AuthenticationUtil();
    String std_id = auth.getCurrentUserName(); 
    return new ResponseEntity<List<Takeleave>>(dao.findStudentTakeleave(std_id), HttpStatus.OK);
  
  }


  

  // @GetMapping(value = {"/student/takeleave/all"})
  //   public List<Takeleave> retrieveTakeleave() throws SQLException{
  //      return dao.findAll();
  //   }


    
      //教師不允許請假後無法再次申請
      //學生請假過後 不可再修改請假內容
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
    }


  @PutMapping(value = "/teacher/takeleave")
     public ResponseEntity<String> processFormUpdate(@RequestBody final Takeleave takeleave) throws SQLException {


              takeleave.setTl_type_id(dao.findTltypeID(takeleave.getRc_id(),takeleave.getStd_id()));
             

              if(takeleave.getTl_state() == 2){ //教師不准許請假

                dao.UnAllowleave(takeleave);
                return ResponseEntity.badRequest().body("教師不准假");

              }else if(takeleave.getTl_state() == 1){ //教師准許請假

                dao.Allowleave(takeleave);
                dao.updateTltypeID(takeleave);
                return ResponseEntity.ok("請假審核成功");

              }else{

                return ResponseEntity.badRequest().body("此學生尚未申請請假");

              }

            }

  @PutMapping(value = "/student/takeleave/UpdateContent")
      public ResponseEntity<String> processFormUpdate2(@RequestBody final Takeleave takeleave) throws SQLException {

        // if(takeleave.getTl_content() == ""){
        //   return ResponseEntity.badRequest().body("請假內容不得為空");
        // }else if(){

        // }
        dao.updateContent(takeleave);
        return ResponseEntity.ok("已修改請假內容");


      }//內容不得為空
      //如果state==2 or 1 不可修改
        
    }
			
        

    



  

 





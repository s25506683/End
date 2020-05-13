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

import org.apache.tomcat.util.buf.UEncoder;
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

  //teacher get all student's takeleave record (by student).
  //you will get rc_id, rc_starttime, record_time, tl_createtime, std_id, std_name, tl_type_name, tl_type_id, tl_content, tl_state returns.
  @GetMapping(value = {"/teacher/takeleave/AllStudent/{cs_id}"}) //教師看到所有學生的請假申請
    public ResponseEntity<List<Takeleave>> retrieveAllTakeleave(@PathVariable("cs_id") final String cs_id) throws SQLException,
      IOException {

      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
      
      if(userintheclass.queryTeacherInTheClass(teacher_id, cs_id) == 0){
         //if teacher not in this class.
         return new ResponseEntity<List<Takeleave>>(HttpStatus.BAD_REQUEST);
      }else{

        return new ResponseEntity<List<Takeleave>>(dao.findTakeleaveInTheClass(cs_id), HttpStatus.OK);
      }
     

    }

   //student get all takeleave record in this class.
   //you will get rc_id, rc_starttime, record_time, tl_createtime, tl_type_id, tl_content, tl_state tl_type_name returns.
  @GetMapping(value = {"/student/takeleave/TakeleaveRecord/{cs_id}"}) //get學生的全部請假紀錄
  public ResponseEntity<List<Takeleave>> retrieveTakeleaveRecord(@PathVariable("cs_id") final String cs_id) throws SQLException{

    AuthenticationUtil auth = new AuthenticationUtil();
    String std_id = auth.getCurrentUserName(); 
    if(userintheclass.queryStudentInTheClass(std_id, cs_id) == 0){
      //if student not in this class.
      return new ResponseEntity<List<Takeleave>>(HttpStatus.BAD_REQUEST);
    }else{
      
      return new ResponseEntity<List<Takeleave>>(dao.findStudentTakeleaveRecord(std_id,cs_id), HttpStatus.OK);
      
    }

  }

  //student get absence record for himself(when state == 0).
  //you will get rc_id, rc_starttime, rc_inputsource
  @GetMapping(value = {"/student/takeleave/StudentAbsence/{cs_id}"}) //學生查看自己的缺課紀錄
  public ResponseEntity<List<Takeleave>> retrieveStudentAbsence(@PathVariable("cs_id") final String cs_id) throws SQLException{

    AuthenticationUtil auth = new AuthenticationUtil();
    String std_id = auth.getCurrentUserName();
    if(userintheclass.queryStudentInTheClass(std_id, cs_id) == 0){
      //if student not in this class.
      return new ResponseEntity<List<Takeleave>>(HttpStatus.BAD_REQUEST);
    }else{
    return new ResponseEntity<List<Takeleave>>(dao.findStudentTakeleave(std_id,cs_id), HttpStatus.OK);
    
    }

  }


  

  // @GetMapping(value = {"/student/takeleave/all"})
  //   public List<Takeleave> retrieveTakeleave() throws SQLException{
  //      return dao.findAll();
  //   }


    
      //教師不允許請假後無法再次申請
      //學生請假過後 不可再修改請假內容
  //student post new takeleave to db
  //you will input rc_id, std_id, tl_content, tl_type_id, tl_createtime.
  @PostMapping(value = {"/student/takeleave"})
    public ResponseEntity<String> processFormCreate(@RequestBody final Takeleave takeleave) throws SQLException,
        IOException {

          AuthenticationUtil auth = new AuthenticationUtil();
          takeleave.setStd_id(Integer.parseInt(auth.getCurrentUserName()));
          takeleave.setCs_id(dao.findCsID(takeleave.getRc_id()));
          //takeleave.setRc_id(takeleave.getRc_id());
    

          
            // if(dao.findStateInTheTakeleave(takeleave.getRc_id(), takeleave.getStd_id()) != 0){
            //   //教師已審核完成，不可再次申請
            //   return ResponseEntity.badRequest().body("教師已審核過該筆請假，請勿再次申請");     
            // }else      
          if(dao.queryStudentInTakeleave(takeleave.getRc_id(), takeleave.getStd_id()) == 0){

             dao.Applyforleave(takeleave);
             takeleave.getRc_id();
             writtenmessage = "student \"" + takeleave.getStd_id() + "\" apply takeleave in rollcall \"" + takeleave.getRc_id() + "\".";
             logfile.writeLog(writtenmessage, takeleave.getCs_id(), partition);
             return ResponseEntity.ok("申請成功");
       
            }else{
           return ResponseEntity.badRequest().body("你已申請過請假，請耐心等待老師的回覆");
         //已申請中，但教師尚未批改
          }

          
          // if(dao.queryStudentInTakeleave(takeleave.getRc_id(), takeleave.getStd_id()) == 1){
          //   //if student in the takeleave
          //   return ResponseEntity.badRequest().body("你已申請過請假，請耐心等待老師的回覆");

          // }else if(dao.findStateInTheTakeleave(takeleave.getRc_id(), takeleave.getStd_id()) != 0){
          //   //教師已審核完成，不可再次申請
          //   return ResponseEntity.badRequest().body("教師已審核完成該筆請假，請勿再次申請");     
          // }else{
          //   dao.Applyforleave(takeleave);
          //   takeleave.getRc_id();
          //   writtenmessage = "student \"" + takeleave.getStd_id() + "\" apply takeleave in rollcall \"" + takeleave.getRc_id() + "\".";
          //   logfile.writeLog(writtenmessage, takeleave.getCs_id(), partition);
          //   return ResponseEntity.ok("申請成功");
          //    //已申請中，但教師尚未批改
          

         
     
    }

 //state == 1 or 2 時不能再次修改
 //teacher update student state in this takeleave
 //you have input rc_id, std_id, tl_state.
  @PutMapping(value = "/teacher/takeleave")
     public ResponseEntity<String> processFormUpdate(@RequestBody final Takeleave takeleave) throws SQLException,
         IOException {

        takeleave.setTl_type_id(dao.findTltypeID(takeleave.getRc_id(),takeleave.getStd_id()));
        takeleave.setCs_id(dao.findCsID(takeleave.getRc_id()));
        AuthenticationUtil auth = new AuthenticationUtil();
        String teacher_id = auth.getCurrentUserName();

        if(dao.findStateInTheTakeleave(takeleave.getRc_id(), takeleave.getStd_id()) != 0){
          //學生的state已為1 or 2 無法再次更動
          return ResponseEntity.badRequest().body("已成功審核學生，無法再次修改");    

        }else if(takeleave.getTl_state() == 2){ //教師不准許請假
          
            writtenmessage = "teacher \"" + teacher_id + "  \" unallowleave takeleave in rollcall \"" + takeleave.getRc_id() + "\".";
            logfile.writeLog(writtenmessage, takeleave.getCs_id(), partition);
            dao.UnAllowleave(takeleave);
            return ResponseEntity.badRequest().body("教師不准假");

        }else if(takeleave.getTl_state() == 1){ //教師准許請假
            
            writtenmessage = "teacher \"" + teacher_id + "\" allowleave takeleave in rollcall \"" + takeleave.getRc_id() + "\".";
            logfile.writeLog(writtenmessage, takeleave.getCs_id(), partition);
            dao.Allowleave(takeleave);
            dao.updateTltypeID(takeleave);
            return ResponseEntity.ok("請假審核成功");
        }else{
            return ResponseEntity.badRequest().body("此學生尚未申請請假");
        }
  }

  //student update takeleave content
  //you wii input rc_id, tl_content
  @PutMapping(value = "/student/takeleave/UpdateContent")
      public ResponseEntity<String> processFormUpdate2(@RequestBody final Takeleave takeleave) throws SQLException,
          IOException {

        AuthenticationUtil auth = new AuthenticationUtil();
        int std_id = Integer.parseInt(auth.getCurrentUserName());
        takeleave.setCs_id(dao.findCsID(takeleave.getRc_id()));

        if(takeleave.getTl_content() == ""){
          return ResponseEntity.badRequest().body("請假內容不得為空");
        }else if(dao.findStateInTheTakeleave(takeleave.getRc_id(), std_id) != 0){
          return ResponseEntity.badRequest().body("教師已審核過該筆請假，無法更改內容");     
        }else{
          writtenmessage = "student \"" + std_id + "\" Update takeleaveContent in rollcall \"" + takeleave.getRc_id() + "\".";
          logfile.writeLog(writtenmessage, takeleave.getCs_id(), partition);
          dao.updateContent(takeleave);
          return ResponseEntity.ok("已修改請假內容");
        }        
      }//內容不得為空
      //如果state==2 or 1 不可修改
        
    }
			
        

    



  

 





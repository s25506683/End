package com.example.demo.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
/////////////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.RollcallDAO;
import com.example.demo.entity.Rollcall;
import com.example.demo.util.AuthenticationUtil;
import com.example.demo.util.CurrentTimeStamp;
import com.example.demo.util.Logfile;
import com.example.demo.util.UserInTheClass;
import com.example.demo.util.MapHelper;

@RestController
public class RollcallController {
  @Autowired
  RollcallDAO dao;

  @Autowired
  UserInTheClass userintheclass;

  @Autowired
  Logfile logfile;

  @Autowired
  MapHelper maphelper;
  
  String writtenmessage = new String();
  String partition = "Rollcall";


   //add rollcall and rollcall record(teacher).
   //you will input cs_id, rc_inputsource(QRcode點名、手動點名、GPS點名), qrcode, gps_point(String like "25.015369,121.427966") --- which is longitude and latitude.
  @PostMapping(value = "/teacher/rollcall/addrollcall")
     public ResponseEntity<String> processFormCreate(@RequestBody Rollcall rollcall) throws SQLException, IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();
      String[] gpsarr = rollcall.getGps_point().split(",");
      rollcall.setLongitude(gpsarr[0]);
      rollcall.setLatitude(gpsarr[1]);

        if(userintheclass.queryTeacherInTheClass(teacher_id, rollcall.getCs_id()) == 0){
            //if teacher not in this class.
            return ResponseEntity.badRequest().body("request failed. teacher not in this class!");
        }else{

           CurrentTimeStamp ts = new CurrentTimeStamp();
           rollcall.setRc_starttime(ts.getCurrentTimeStamp());
           //add rollcall.
           dao.addRollcall(rollcall);
           rollcall.setRc_id(dao.findRcId(rollcall.getCs_id(), rollcall.getRc_starttime()));

           String[] studentInfoarr = dao.findClassStudent(rollcall.getCs_id());
           //calculate the array(String) of length.
           int size = studentInfoarr.length;
           //initialize array(int)'s size.
           int [] studentInfointarr = new int [size];
           //using for loop to convert array(String) to int, also input rollcall record every student in the class.
           for(int i=0 ; i<size ; i++) {
              //studentInfointarr[i] = Integer.parseInt(studentInfoarr[i]);
              dao.addRollcallRecord(rollcall.getRc_id(), Integer.parseInt(studentInfoarr[i]));
              System.out.println(studentInfointarr[i]+"\n");
            }

          writtenmessage = "teacher "+ teacher_id + " adding rollcall at " + rollcall.getRc_starttime() + " in the class.";
          logfile.writeLog(writtenmessage, rollcall.getCs_id(), partition);
           return ResponseEntity.ok("request successful! the rollcall has already added!");
        }
         

     }

   //teacher find rc_id using qrcode.
   //you will get rc_id return.
 @GetMapping(value = {"/teacher/rollcall/findRCID/{qrcode}/"})
 public ResponseEntity<List<Rollcall>> retrieveOneRollcallFromTeacher(@PathVariable("qrcode") final String qrcode) throws SQLException {

    if(dao.hasThisQRcode(qrcode) == 0){
      //if this qrcode Id not found.
      return new ResponseEntity<List<Rollcall>>(HttpStatus.BAD_REQUEST);
    }else{
      return new ResponseEntity<List<Rollcall>>(dao.findRcIdWithQRcode(qrcode), HttpStatus.OK);
    }
 }

 //teacher get one rollcall summary.
 //you will get present, long_distance, takeleave, otherwise(takeleave).
 @GetMapping(value = {"/teacher/rollcall/oneRollcall/Summary/{rc_id}/"})
 public ResponseEntity<List<Rollcall>> retrieveOneRollcallSummaryFromTeacher(@PathVariable("rc_id") final int rc_id) throws SQLException {

    if(dao.hasThisRollcallId(rc_id) == 0){
      //if this rollcall Id not found.
      return new ResponseEntity<List<Rollcall>>(HttpStatus.BAD_REQUEST);
    }else{
      return new ResponseEntity<List<Rollcall>>(dao.findOneRollcallSummaryRecord(rc_id), HttpStatus.OK);
    }
 }


   //teacher get one rollcall's record(all student record).
   //you will get std_id, std_name, std_department, record_time, tl_type_name, tl_type_id returns.
 @GetMapping(value = {"/teacher/rollcall/oneRollcall/{rc_id}"})
 public ResponseEntity<List<Rollcall>> retrieveOneRollcallFromTeacher(@PathVariable("rc_id") final int rc_id) throws SQLException {

    if(dao.hasThisRollcallId(rc_id) == 0){
      //if this rollcall Id not found.
      return new ResponseEntity<List<Rollcall>>(HttpStatus.BAD_REQUEST);
    }else{
      return new ResponseEntity<List<Rollcall>>(dao.findOneRollcallRecord(rc_id), HttpStatus.OK);
    }
 }
    
    //student get all rollcall in this class.
    //you will get rc_starttime(String), present(int), absent(int), otherwise(int), rc_scoring(int), rc_inputsource(String) returns.
 @GetMapping(value = {"/student/rollcall/allRollcall/{cs_id}"})
    public ResponseEntity<List<Rollcall>> retrieveAllRollcallFromStudent(@PathVariable("cs_id") final String cs_id) throws SQLException{
      AuthenticationUtil auth = new AuthenticationUtil();
      String std_id = auth.getCurrentUserName();

       if(userintheclass.queryStudentInTheClass(std_id, cs_id) == 0){
          //if student not in this class.
         return new ResponseEntity<List<Rollcall>>(HttpStatus.BAD_REQUEST);
       }else{
         return new ResponseEntity<List<Rollcall>>(dao.findAllRollcallRecord(cs_id), HttpStatus.OK);
       }
    }

    //teacher get all rollcall in this class
    //you will get rc_id, rc_starttime(String), present(int), absent(int), otherwise(int), rc_scoring(int), rc_inputsource(String) returns.
@GetMapping(value = {"/teacher/rollcall/allRollcall/{cs_id}"})
    public ResponseEntity<List<Rollcall>> retrieveAllRollcallFromTeacher(@PathVariable("cs_id") final String cs_id) throws SQLException{
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();

       if(userintheclass.queryTeacherInTheClass(teacher_id, cs_id) == 0){
          //if teacher not in this class.
         return new ResponseEntity<List<Rollcall>>(HttpStatus.BAD_REQUEST);
       }else{
         return new ResponseEntity<List<Rollcall>>(dao.findAllRollcallRecord(cs_id), HttpStatus.OK);
       }
    }


   //student get own rollcall's record (one class).
   //you will get rc_id, record_id, rc_starttime, record_time, rc_inputsource, tl_type_name returns.
 @GetMapping(value = {"/student/rollcall/personalRecord/{cs_id}"})
 public ResponseEntity<List<Rollcall>> retrievepersonalRecord(@PathVariable("cs_id") final String cs_id) throws SQLException,
     IOException {

  AuthenticationUtil auth = new AuthenticationUtil();
  int std_id = Integer.parseInt(auth.getCurrentUserName());

  if(dao.hasThisCsId(cs_id) == 0){
    //if cs_id was round.
    return new ResponseEntity<List<Rollcall>>(HttpStatus.BAD_REQUEST);
  }else if(userintheclass.queryStudentInTheClass(Integer.toString(std_id), cs_id) == 0){
    //if student not in this class.
    return new ResponseEntity<List<Rollcall>>(HttpStatus.BAD_REQUEST);
  }else{
    writtenmessage = "student "+ std_id + " watching personal rollcall record in the class.";
    logfile.writeLog(writtenmessage, cs_id, partition);
    return new ResponseEntity<List<Rollcall>>(dao.findStudentOwnRollcallInClass(std_id, cs_id), HttpStatus.OK);
  }
 }

   //teacher get student's own rollcall's record (one class, all rollcall record).
   //you will get rc_id, record_id, rc_starttime, record_time, rc_inputsource, tl_type_name returns.
 @GetMapping(value = {"/teacher/rollcall/personalRecord/{cs_id}/{std_id}"})
 public ResponseEntity<List<Rollcall>> retrieveStudentPersonalRecord(@PathVariable("cs_id") final String cs_id, @PathVariable("std_id") final int std_id) throws SQLException,
     IOException {

  AuthenticationUtil auth = new AuthenticationUtil();
  int teacher_id = Integer.parseInt(auth.getCurrentUserName());

  if(dao.hasThisCsId(cs_id) == 0){
    //if cs_id was round.
    return new ResponseEntity<List<Rollcall>>(HttpStatus.BAD_REQUEST);
  }else if(userintheclass.queryTeacherInTheClass(Integer.toString(teacher_id), cs_id) == 0){
    //if teacher not in this class.
    return new ResponseEntity<List<Rollcall>>(HttpStatus.BAD_REQUEST);
  }else if(userintheclass.queryStudentInTheClass(Integer.toString(std_id), cs_id) == 0){
    //if student not in this class.
    return new ResponseEntity<List<Rollcall>>(HttpStatus.BAD_REQUEST);
  }else{
    writtenmessage = "teacher "+ teacher_id + " watching student " + std_id + "'s personal rollcall record in the class.";
    logfile.writeLog(writtenmessage, cs_id, partition);
    return new ResponseEntity<List<Rollcall>>(dao.findStudentOwnRollcallInClass(std_id, cs_id), HttpStatus.OK);
  }
 }

   //teacher get all student's rollcall record (by student).
   //you will get std_id, std_name, std_department, present, absent, otherwise returns.
   @GetMapping(value = {"/teacher/rollcall/RecordbyPerson/{cs_id}"})
   public ResponseEntity<List<Rollcall>> retrieveRollcallRecordByStudent(@PathVariable("cs_id") final String cs_id) throws SQLException,
       IOException {
  
    AuthenticationUtil auth = new AuthenticationUtil();
    int teacher_id = Integer.parseInt(auth.getCurrentUserName());
  
    if(dao.hasThisCsId(cs_id) == 0){
      //if cs_id was round.
      return new ResponseEntity<List<Rollcall>>(HttpStatus.BAD_REQUEST);
    }else if(userintheclass.queryTeacherInTheClass(Integer.toString(teacher_id), cs_id) == 0){
      //if teacher not in this class.
      return new ResponseEntity<List<Rollcall>>(HttpStatus.BAD_REQUEST);
    }else{
      writtenmessage = "teacher "+ teacher_id + " watching rollcall by person in the class.";
      logfile.writeLog(writtenmessage, cs_id, partition);
      return new ResponseEntity<List<Rollcall>>(dao.findRollcallByPerson(cs_id), HttpStatus.OK);
    }
   }
   
    //student QRcode rollcall.
    //you will input rc_id, qrcode.
 @PutMapping(value = "/student/rollcall/QRcodeRollcall/{qrcode}/")
    public ResponseEntity<String> processUpdateRollcallStudentWithQRcode(@PathVariable("qrcode") final String qrcode) throws SQLException,
        IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      int std_id = Integer.parseInt(auth.getCurrentUserName());

      if(dao.hasThisQRcode(qrcode) == 0){
        //if QRcode not exist.
        return ResponseEntity.badRequest().body("request failed. invalid QRcode!");
      }else{
        //use QRcode to find rc_id.
        int rc_id = dao.findRcIdWithQRcode2(qrcode);
        
        if(dao.rollcallIsEnd(rc_id) == 1){
          //if the rollcall was closed by teacher.
          writtenmessage = "student "+ std_id + " QRcode rollcall failed, because the rollcall was closed.(input's rc_id = " + rc_id + " , qrcode = " + qrcode + ")";
          logfile.writeLog(writtenmessage, dao.findCs_id(rc_id), partition);
          return ResponseEntity.badRequest().body("request failed. This rollcall was closed by teacher!");
        }else{
          //if input qrcode equals rollcall's qrcode.
          dao.updateRollcallRecord(std_id, rc_id);
          writtenmessage = "student "+ std_id + " QRcode rollcall update to present in rc_id = " + rc_id + ".";
          logfile.writeLog(writtenmessage, dao.findCs_id(rc_id), partition);
          return ResponseEntity.ok("request successful! the QRcode rollcall record has already added!");
        }

      }
       
    }


    //student GPS rollcall.
    //you will input rc_id, gps_point(String like "25.015369,121.427966").
 @PutMapping(value = "/student/rollcall/GPSRollcall")
 public ResponseEntity<String> processUpdateRollcallStudentWithGPS(@RequestBody Rollcall rollcall) throws SQLException, IOException {
   AuthenticationUtil auth = new AuthenticationUtil();
   int std_id = Integer.parseInt(auth.getCurrentUserName());

   double distance = 0;
   distance = maphelper.GetPointDistance(rollcall.getGps_point(), rollcall.getRc_id());

   if(dao.rollcallIsEnd(rollcall.getRc_id()) == 1){
     //if the rollcall was closed.
     writtenmessage = "student "+ std_id + " QRcode rollcall failed, because the rollcall was closed.(input's rc_id = " + rollcall.getRc_id() + " , qrcode = " + rollcall.getQrcode() + ")";
     logfile.writeLog(writtenmessage, dao.findCs_id(rollcall.getRc_id()), partition);
     return ResponseEntity.badRequest().body("request failed. This rollcall was closed by teacher!");
   }else if(distance < 0.5){
     //if input gps point distance less than 0.5 kilometer with rollcall's destination.
     dao.updateRollcallRecord(std_id, rollcall.getRc_id());
     writtenmessage = "student "+ std_id + " GPS rollcall update to present in rc_id = " + rollcall.getRc_id() + ".";
     logfile.writeLog(writtenmessage, dao.findCs_id(rollcall.getRc_id()), partition);
     return ResponseEntity.ok("request successful! the GPS rollcall record has already added!\ndistance: " + distance + ".");
   }else{
     //if the gps point distance more than 0.5 (include 0.5km) kilometer with rollcall's destination.
     return ResponseEntity.badRequest().body("request failed. GPS point distance too far!\ndistance: " + distance + ".");
   }
    
 }


    //teacher renew a QRcode.
    //you have to input rc_id, qrcode.
    @PutMapping(value = "/teacher/rollcall/updateQRcode")
    public ResponseEntity<String> teacherUpdateQRcode(@RequestBody Rollcall rollcall) throws SQLException, IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      int teacher_id = Integer.parseInt(auth.getCurrentUserName());

      if(dao.rollcallByHand(rollcall.getRc_id()).equals("手動點名")){
        //if the rollcall not by QRcode, but by hand.
        return ResponseEntity.badRequest().body("request failed. This rollcall only update by hand!");
      }else{
        dao.updateQRcode(rollcall.getRc_id(), rollcall.getQrcode());
        writtenmessage = "teacher "+ teacher_id + " renew the QRcode to " + rollcall.getQrcode() + " in rc_id = " + rollcall.getRc_id() + ".";
        logfile.writeLog(writtenmessage, dao.findCs_id(rollcall.getRc_id()), partition);
        return ResponseEntity.ok("request successful! the QRcode has already changed!");
      }

    }


    //teacher update student rollcall record.
    //you have to input rc_id, std_id, tl_type_id.
    @PutMapping(value = "/teacher/rollcall/updateRollcall")
    public ResponseEntity<String> teacherUpdateRollcall(@RequestBody Rollcall rollcall) throws SQLException,
        IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      int teacher_id = Integer.parseInt(auth.getCurrentUserName());
      if(dao.hasThisRcRecord(rollcall.getStd_id(), rollcall.getRc_id()) == 0){
        //if this student rollcall record not found.
        return ResponseEntity.badRequest().body("request failed. This rollcall record not found!");
      }else{
        dao.updateRollcall(rollcall.getRc_id(), rollcall.getStd_id(), rollcall.getTl_type_id());
        //find tl_type_name using rc_id.
        String tl_type_name = dao.findTlTypeName(rollcall.getTl_type_id());
        writtenmessage = "teacher "+ teacher_id + " update student rollcall record to " + tl_type_name + " in rc_id = " + rollcall.getRc_id() + ", std_id = " + rollcall.getStd_id() + ".";
        logfile.writeLog(writtenmessage, dao.findCs_id(rollcall.getRc_id()), partition);
        return ResponseEntity.ok("request successful! the student rollcall record update to " + tl_type_name + "!");
      }
    }

   //teacher delete rollcall and rollcall record in the class.
   //you have to input rc_id, cs_id in json.
 @DeleteMapping(value = "/teacher/rollcall/deleteRollcall")
    public ResponseEntity<String> deleteQuestion(@RequestBody Rollcall rollcall) throws IOException {
      AuthenticationUtil auth = new AuthenticationUtil();
      String teacher_id = auth.getCurrentUserName();

       if(userintheclass.queryTeacherInTheClass(teacher_id, rollcall.getCs_id()) == 0){
          //if teacher not in this class.
          return ResponseEntity.badRequest().body("request failed. teacher not in this class!");
       }else{
        writtenmessage = "teacher "+ teacher_id + " delete rollcall in class " + rollcall.getCs_id() + " with rc_id = " + rollcall.getRc_id() + ".";
        logfile.writeLog(writtenmessage, rollcall.getCs_id(), partition);
         dao.deleteRollcall(rollcall.getRc_id());
         return ResponseEntity.ok("request successful! the rollcall has already deleted!");
       }
    }






 

 //學生端Qrcode點名insert(done)
 //教師端手動update編輯點名(done)
 //老師刪除點名紀錄(done)
 //老師端更新QRcode(done)
 //學生在課堂點名中看到自己的所有點名紀錄(done)
 //student QRcode rollcall. 的input rc_name 改成 rc_id.(done)
}




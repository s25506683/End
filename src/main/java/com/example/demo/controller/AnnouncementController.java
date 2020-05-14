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
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.dao.AnnouncementDAO;
import com.example.demo.entity.Announcement;
import com.example.demo.util.AuthenticationUtil;
import com.example.demo.util.CurrentTimeStamp;
import com.example.demo.util.Logfile;
import com.example.demo.util.MailService;
import com.example.demo.util.UserInTheClass;

@RestController
public class AnnouncementController {
  @Autowired
  AnnouncementDAO dao;

  @Autowired
  UserInTheClass userintheclass;

  @Autowired
  Logfile logfile;

  @Autowired
  MailService mailservice;



  String writtenmessage = new String();
  String partition = "Announcement";


    //teacher add announcement in the class.
    //you have to input cs_id, at_title, at_content.
 @PostMapping(value = "/teacher/announcement/post/")
    public ResponseEntity<String> processFormCreate(@RequestBody Announcement announcement) throws SQLException,
            IOException {
        AuthenticationUtil auth = new AuthenticationUtil();
        String teacher_id = auth.getCurrentUserName();
        int teacher_idMail = Integer.parseInt(auth.getCurrentUserName());

        if(dao.queryClassHasExists(announcement.getCs_id()) == 0){
            //if this class not exist.
            return ResponseEntity.badRequest().body("request failed. Class does not exist!");
        }else if(userintheclass.queryTeacherInTheClass(teacher_id, announcement.getCs_id()) == 0){
            //if teacher not in this class.
            return ResponseEntity.badRequest().body("request failed. teacher not in this class!");
        }else{
            CurrentTimeStamp ts = new CurrentTimeStamp();
            announcement.setAt_posttime(ts.getCurrentTimeStamp());
            dao.postAnnouncement(announcement);

            String at_title = announcement.getAt_title();
            String at_content = announcement.getAt_content();
            String cs_name = dao.findClassName(announcement.getCs_id());

            String[] studentMailList = dao.findStudentEmail(announcement.getCs_id());
             for(int i = 0; i < studentMailList.length; i++){
                mailservice.sendAnnouncementToStudent(studentMailList[i], teacher_idMail, at_title, at_content, cs_name, announcement);
             }
             
            writtenmessage = "teacher "+ teacher_id + " adding announcement at in the class.";
            logfile.writeLog(writtenmessage, announcement.getCs_id(), partition);
            return ResponseEntity.ok("request successful! the announcement has already added!");
        }
    }


    //teacher get all announcement in the class.
    //you will get at_id, at_title, at_content, at_posttime returns.
 @GetMapping(value = {"/teacher/announcement/{cs_id}/get/"})
    public ResponseEntity<List<Announcement>> teacherRetrieveAnnouncement(@PathVariable("cs_id") String cs_id) throws SQLException{
        AuthenticationUtil auth = new AuthenticationUtil();
        String teacher_id = auth.getCurrentUserName();

        if(userintheclass.queryTeacherInTheClass(teacher_id, cs_id) == 0){
            //if teacher not in this class.
            return new ResponseEntity<List<Announcement>>(HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<List<Announcement>>(dao.findAllAnnouncementInTheClass(cs_id), HttpStatus.OK);
        }
    }

    //student get all announcement in the class.
    //you will get at_id, at_title, at_content, at_posttime returns.
 @GetMapping(value = {"/student/announcement/{cs_id}/get/"})
    public ResponseEntity<List<Announcement>> studentRetrieveAnnouncement(HttpServletRequest request, @PathVariable("cs_id") String cs_id) throws Exception {
        AuthenticationUtil auth = new AuthenticationUtil();
        String std_id = auth.getCurrentUserName();

        if(userintheclass.queryStudentInTheClass(std_id, cs_id) == 0){
            //if student not in this class.
            return new ResponseEntity<List<Announcement>>(HttpStatus.BAD_REQUEST);
        }else{
            writtenmessage = "student "+ std_id + " watching announcement at in the class.";
            logfile.writeLog(writtenmessage, cs_id, partition);
            return new ResponseEntity<List<Announcement>>(dao.findAllAnnouncementInTheClass(cs_id), HttpStatus.OK);
        }
    }


    //teacher edit announcement in this class.
    //you have to input at_id, cs_id, at_title, at_content.
 @PutMapping(value = "/teacher/announcement/update/")
    public ResponseEntity<String> updateAnnouncement(@RequestBody Announcement announcement) throws SQLException,
            IOException {
        AuthenticationUtil auth = new AuthenticationUtil();
        String teacher_id = auth.getCurrentUserName();
        int teacher_idMail = Integer.parseInt(auth.getCurrentUserName());

        String at_title = announcement.getAt_title();
        String at_content = announcement.getAt_content();
        String cs_name = dao.findClassName(announcement.getCs_id());

        if(dao.hasAtIdExists(announcement.getAt_id()) == 0){
            //if at_id not found.
            return ResponseEntity.badRequest().body("request failed. at_id not found!");
        }else if(dao.queryClassHasExists(announcement.getCs_id()) == 0){
            //if this class not exist.
            return ResponseEntity.badRequest().body("request failed. ClassID not found!");
        }else if(userintheclass.queryTeacherInTheClass(teacher_id, announcement.getCs_id()) == 0){
            //if teacher not in this class.
            return ResponseEntity.badRequest().body("request failed. teacher not in this class!");
        }else{
            dao.updateAnnouncement(announcement);
            String[] studentMailList = dao.findStudentEmail(announcement.getCs_id());
             for(int i = 0; i < studentMailList.length; i++){
                mailservice.sendAnnouncementToStudent(studentMailList[i], teacher_idMail, at_title, at_content, cs_name, announcement);
             }
            writtenmessage = "teacher "+ teacher_id + " edit announcement at in the class.";
            logfile.writeLog(writtenmessage, announcement.getCs_id(), partition);
            return ResponseEntity.ok("announcement update successful!");
        }
    }

    //teacher delete announcement.
    //you have to input rc_id, cs_id.
 @DeleteMapping(value = "/teacher/announcement/delete/")
    public ResponseEntity<String> deleteCustomer(@RequestBody Announcement announcement) throws IOException {
        AuthenticationUtil auth = new AuthenticationUtil();
        String teacher_id = auth.getCurrentUserName();

        if(dao.hasAtIdExists(announcement.getAt_id()) == 0){
            //if at_id not found.
            return ResponseEntity.badRequest().body("request failed. at_id not found!");
        }else if(dao.queryClassHasExists(announcement.getCs_id()) == 0){
            //if this class not exist.
            return ResponseEntity.badRequest().body("request failed. ClassID does not exist!");
        }else if(userintheclass.queryTeacherInTheClass(teacher_id, announcement.getCs_id()) == 0){
            //if teacher not in this class.
            return ResponseEntity.badRequest().body("request failed. teacher not in this class!");
        }else{
            dao.deleteAnnouncement(announcement);
            writtenmessage = "teacher "+ teacher_id + " delete announcement at in the class.";
            logfile.writeLog(writtenmessage, announcement.getCs_id(), partition);
            return ResponseEntity.ok("announcement delete completed!");
        }
    }







}
package com.example.demo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.object.SqlCall;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.example.demo.entity.Student;
import com.example.demo.dao.StudentDAO;
import com.example.demo.util.AuthenticationUtil;
import com.example.demo.util.Logfile;


import com.example.demo.util.MailService;

@RestController
public class StudentController {
	@Autowired
	StudentDAO dao;

	@Autowired
	Logfile logfile;
	
	@Autowired
	MailService mailservice;

	String writtenmessage = new String();

	@PostMapping(value = "/student_re")

	public ResponseEntity<String> processFormCreate(@RequestBody final Student student)
			throws SQLException, IOException {
		
		//if has userAccount in DB
		if(dao.queryUser(student.getStd_id()) == 0){
			//if input's std_id out of range(or less than) 9
			if(Integer.toString(student.getStd_id()).length() == 9){
				//if input email's format is alright
				if(student.getStd_mail().contains("@")){
					dao.insert(student);
					writtenmessage = Integer.toString(student.getStd_id()) + " now has already registered!" ;
					logfile.writeLog(writtenmessage);
					return ResponseEntity.ok("request successful!");
				}else{
					return ResponseEntity.badRequest().body("request failed. Email format error!");
				}
			}else{
				return ResponseEntity.badRequest().body("request failed. Id too long/smail!");
			}

			
		}else{
			return ResponseEntity.badRequest().body("This account has already exist!");
			
		}

	}

	
	@GetMapping(value = { "/student/information/" })
	public Student retrieveOneStudent() throws SQLException {
		AuthenticationUtil auth = new AuthenticationUtil();
		int std_id = Integer.parseInt(auth.getCurrentUserName());
		return dao.findOne(std_id);
	}

	@GetMapping(value = { "/student/" })
	public List<Student> retrieveStudent() throws SQLException {
		return dao.findAll();
	}

	//student change own password after login.
	//you have to input old_std_password, std_password.
	@PutMapping(value = "/student/resetPassword/")
	public ResponseEntity<String> processFormUpdate(@RequestBody final Student student) throws SQLException {
		AuthenticationUtil auth = new AuthenticationUtil();
		int std_id = Integer.parseInt(auth.getCurrentUserName());

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		//macth old password in database.
		if(passwordEncoder.matches(student.getOld_std_password(), dao.getPassword(std_id))){
			//if student input old password is right.
			dao.updateStudentPassword(std_id, student.getStd_password());	//new password encode.
			return ResponseEntity.ok("password update successful!");
		}else{
			return ResponseEntity.badRequest().body("request failed. old password was round!");
		}
		
	}

	//send new uuid password to student's email address.
	//you have to  input std_id, std_email, std_password.
	@PutMapping(value = "/sendStudentEmailWithNewPassword/")
	public ResponseEntity<String> sendNewPasswordToStudentEmail(@RequestBody final Student student) throws SQLException {

		UUID uuid  =  UUID.randomUUID();
		String[] idarr = uuid.toString().split("-");
		String id = idarr[0];

		if(dao.resetPasswordVerify(student.getStd_id(), student.getStd_mail(), student.getStd_phone()) == 0){
			//if student verify failed.
			return ResponseEntity.badRequest().body("request failed. Email or Phone Number has round!");
		}else{

			dao.updateStudentPassword(student.getStd_id(), id);
			String user_email = student.getStd_mail();
			String newpassword = id;
			mailservice.prepareAndSend(user_email, newpassword, student.getStd_id());
			return ResponseEntity.ok("email send(NewPassword) successful!");
		}
		
		
	}	

	@DeleteMapping(value = "/student/{std_id}")
	public void deleteStudent(@PathVariable("std_id") final int std_id) {
		dao.delete(std_id);
	}

}

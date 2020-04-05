package com.example.demo.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
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

import com.example.demo.entity.Teacher;
import com.example.demo.util.Logfile;
import com.example.demo.dao.TeacherDAO;

@RestController
public class TeacherController {
	@Autowired
	TeacherDAO dao;

	@Autowired
	Logfile logfile;

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();

	@PostMapping(value = "/teacher_re")
	public ResponseEntity<String> processFormCreate(@RequestBody Teacher teacher) throws SQLException, IOException {
		
		// if(newTeacher.getTeacher_name().equals(null)){ //透過name判斷是否重複

		if(dao.queryUser(teacher.getTeacher_id()) == 0){

			if(Integer.toString(teacher.getTeacher_id()).length() == 9){
			
				if(teacher.getTeacher_mail().contains("@")){
					dao.insert(teacher);
					final String writtenmessage = dtf.format(now) + "\t" + Integer.toString((teacher.getTeacher_id())) + " now has a realdy registered!";      
					logfile.writeLog(writtenmessage);
					return ResponseEntity.ok("Response Success!");
				}
				else{
					return ResponseEntity.badRequest().body("email格式錯誤");
				}
			}
			else{
				System.out.println("HELLO");
				return ResponseEntity.badRequest().body("帳號長度不符");
				
			}	

		}else{
			return ResponseEntity.badRequest().body("此帳號已存在");
		}
		
		
		
		
		
	}

	//@POST
	@GetMapping(value = { "/teacher/{teacher_id}" })
	public Teacher retrieveOneStudent(@PathVariable("teacher_id") int teacher_id) throws SQLException {
		return dao.findOne(teacher_id);
	}

	@GetMapping(value = { "/teacher/" })
	public List<Teacher> retrieveTeacher() throws SQLException {
		return dao.findAll();
	}

//	@PutMapping(value = "/student")
//	public void processFormUpdate(@RequestBody Student student) throws SQLException {
//		dao.update(student);
//	}
//
//	@DeleteMapping(value = "/student/{std_id}")
//	public void deleteStudent(@PathVariable("std_id") int std_id) {
//		dao.delete(std_id);
//	}

}

package com.example.demo.controller;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.object.SqlCall;
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
import com.example.demo.dao.LogfileDAO;

@RestController
public class StudentController {
	@Autowired
	StudentDAO dao;

	@Autowired
	LogfileDAO logfiledao;

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	LocalDateTime now = LocalDateTime.now();
	   
	@PostMapping(value = "/student_re")

	public ResponseEntity<String> processFormCreate(@RequestBody final Student student) throws SQLException {
		
		//if has userAccount in DB
		if(dao.queryUser(student.getStd_id()) == 0){
			//if input's std_id out of range(or less than) 9
			if(Integer.toString(student.getStd_id()).length() == 9){
				//if input email's format is alright
				if(student.getStd_mail().contains("@")){
					dao.insert(student);
					final String writtenmessage = dtf.format(now) + "\t" + Integer.toString(student.getStd_id()) + " now has already registered!" ;
					logfiledao.writeLog(writtenmessage);
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

	// @POST
	@GetMapping(value = { "/student/{std_id}" })
	public Student retrieveOneStudent(@PathVariable("std_id") final int std_id) throws SQLException {
		return dao.findOne(std_id);
	}

	@GetMapping(value = { "/student" })
	public List<Student> retrieveStudent() throws SQLException {
		return dao.findAll();
	}

	@PutMapping(value = "/student")
	public void processFormUpdate(@RequestBody final Student student) throws SQLException {
		dao.update(student);
	}

	@DeleteMapping(value = "/student/{std_id}")
	public void deleteStudent(@PathVariable("std_id") final int std_id) {
		dao.delete(std_id);
	}

}

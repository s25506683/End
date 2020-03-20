package com.example.demo.controller;

import java.sql.SQLException;
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

import com.example.demo.dao.StudentDAODB;
import com.example.demo.entity.Student;
import com.example.demo.dao.StudentDAO;

@RestController
public class StudentController {
	@Autowired
	StudentDAO dao;


	@PostMapping(value = "/student")

	public ResponseEntity<String> processFormCreate(@RequestBody Student student) throws SQLException {
		//Student newStudent = new Student();
		//newStudent = dao.findOne(student.getStd_id());
		//newStudent.getStd_name().equals(null)
		
		//if has userAccount in DB
		if(dao.queryUser(student.getStd_id()) == 0){

			//if input's std_id out of range(or less than) 9
			if(Integer.toString(student.getStd_id()).length() == 9){
				//if input email's format is alright
				if(student.getStd_mail().contains("@") && student.getStd_mail().contains(".com")){
					dao.insert(student);
					return ResponseEntity.ok("request successful!");
				}else{
					return ResponseEntity.badRequest().body("request failed.\nEmail format error!");
				}
			}else{
				return ResponseEntity.badRequest().body("request failed.\nId too long/smail!");
			}

			
		}else{
			return ResponseEntity.badRequest().body("This account has already exist!");
			
		}

	}

	// @POST
	@GetMapping(value = { "/student/{std_id}" })
	public Student retrieveOneStudent(@PathVariable("std_id") int std_id) throws SQLException {
		return dao.findOne(std_id);
	}

	@GetMapping(value = { "/student" })
	public List<Student> retrieveStudent() throws SQLException {
		return dao.findAll();
	}

	@PutMapping(value = "/student")
	public void processFormUpdate(@RequestBody Student student) throws SQLException {
		dao.update(student);
	}

	@DeleteMapping(value = "/student/{std_id}")
	public void deleteStudent(@PathVariable("std_id") int std_id) {
		dao.delete(std_id);
	}

}

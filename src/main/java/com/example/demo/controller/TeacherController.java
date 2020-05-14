package com.example.demo.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.example.demo.util.AuthenticationUtil;
import com.example.demo.util.Logfile;
import com.example.demo.util.MailService;
import com.example.demo.util.UserInTheClass;
import com.example.demo.dao.TeacherDAO;

@RestController
public class TeacherController {
	@Autowired
	TeacherDAO dao;

	@Autowired
	Logfile logfile;

	@Autowired
	MailService mailservice;

	@Autowired
    UserInTheClass userintheclass;

	String writtenmessage = new String();

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();

	//teacher register and enter basic information.
	//you will input teacher_id, teacher_password, teacher_name, teacher_gender, teacher_department, teacher_phone, teacher_mail, teacher_image.
	@PostMapping(value = "/teacher_re")
	public ResponseEntity<String> processFormCreate(@RequestBody Teacher teacher)
	throws SQLException, IOException {
		
		//if has userAccount inDB
		if(dao.queryUser(teacher.getTeacher_id()) == 0){
			//if input's std_id out of range(or less than) 9
			if(Integer.toString(teacher.getTeacher_id()).length() == 9){
				//if input email's format is alright
				if(teacher.getTeacher_mail().contains("@")){
					dao.insert(teacher);
					String writtenmessage = dtf.format(now) + "\t" + Integer.toString((teacher.getTeacher_id())) + " now has a realdy registered!";      
					logfile.writeLog(writtenmessage);
					return ResponseEntity.ok("Response Success!");
				}
				else{
					return ResponseEntity.badRequest().body("email格式錯誤");
				}
			}
			else{
				return ResponseEntity.badRequest().body("帳號長度不符");
			}	
		}else{
			return ResponseEntity.badRequest().body("此帳號已存在");
		}
		
		
		
		
		
	}

	//teacher get imformation about this teacher.
	//you will get teacher_id, teacher_password, teacher_name, teacher_gender, teacher_department, teacher_phone, teacher_mail, teacher_image.
	@GetMapping(value = { "/teacher/information/" })
	public Teacher retrieveOneStudent() throws SQLException {
		AuthenticationUtil auth = new AuthenticationUtil();
		int teacher_id = Integer.parseInt(auth.getCurrentUserName());
		return dao.findOne(teacher_id);
	}

	//teacher get all teacher information in DB
	//you will get teacher_id, teacher_password, teacher_name, teacher_gender, teacher_department, teacher_phone, teacher_mail, teacher_image.
	@GetMapping(value = { "/teacher/" })
	public List<Teacher> retrieveTeacher() throws SQLException {
		return dao.findAll();
	}


	//teacher

	@GetMapping(value = {"/teacher/findStudentInformation/{std_id}"})
	public ResponseEntity<List<Teacher>> retrieveTeacherfindstudent(@PathVariable("std_id") String std_id) throws SQLException{
		return new ResponseEntity<List<Teacher>>(dao.findStudentInformation(std_id), HttpStatus.OK);

	}

	//teacher change own password after login.
	//you have to input old_teacher_password, teacher_password.
	@PutMapping(value = "/teacher/resetPassword")
	public ResponseEntity<String> processFormUpdate(@RequestBody final Teacher teacher)throws SQLException {
		AuthenticationUtil auth = new AuthenticationUtil();
		int teacher_id = Integer.parseInt(auth.getCurrentUserName());

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		//macth old password in database.
		if(passwordEncoder.matches(teacher.getOld_teacher_password(), dao.getPassword(teacher_id))){
			//if teacher input old password is right.
			dao.updateTeacherPassword(teacher_id, teacher.getTeacher_password());
			return ResponseEntity.ok("修改密碼成功");
		}else{	
			return ResponseEntity.badRequest().body("修改密碼失敗，請輸入正確的舊密碼");
		}
	}


	//teacher change own Email after login.
	//you have to input old teacher_mail, teacher_mail.
	@PutMapping(value = "/teacher/resetEmail")
	public ResponseEntity<String> processForUpdateMail(@RequestBody final Teacher teacher)throws SQLException{
		AuthenticationUtil auth = new AuthenticationUtil();
		int teacher_id = Integer.parseInt(auth.getCurrentUserName());

		if(dao.queryUser(teacher.getTeacher_id()) == 0){
			//if input's std_id out of range(or less than) 9

				//if input email's format is alright
				if(teacher.getTeacher_mail().contains("@")){
					dao.updateTeacherMail(teacher_id, teacher.getTeacher_mail());
					writtenmessage = "teacher \"" + teacher_id + "\" update new Email \"";    
					logfile.writeLog(writtenmessage);
					return ResponseEntity.ok("修改Email成功!");
				}
				else{
					return ResponseEntity.badRequest().body("email格式錯誤");
				}
			
		}else{
			return ResponseEntity.badRequest().body("此帳號已存在");
		}
	}

	//teacher change owm Phone after login.
	//you have to input old teacher_phone, teacher_phone
	@PutMapping(value = "/teacher/resetPhone")
	public ResponseEntity<String> processUpdatePhone(@RequestBody final Teacher teacher)throws SQLException{
		AuthenticationUtil auth = new AuthenticationUtil();
		int teacher_id = Integer.parseInt(auth.getCurrentUserName());

		if(teacher.getTeacher_phone().length() == 10){
			dao.updateTeacherPhone(teacher_id, teacher.getTeacher_phone());
			return ResponseEntity.ok("修改Phone成功!");
		}else{
			return ResponseEntity.badRequest().body("phone格式不正確，請輸入10位數字的電話號碼");
		}
		
	}


	//send new uuid password to teacher's email address.
	//you have to  input teacher_id, teacher_email, teacher_phone.
	@PutMapping(value = "/sendTeacherEmailWithNewPassword/")
	public ResponseEntity<String> sendNewPasswordToStudentEmail(@RequestBody final Teacher teacher) throws SQLException{

		UUID uuid  =  UUID.randomUUID();
		String[] idarr = uuid.toString().split("-");
		String id = idarr[0];

		if(dao.resetPasswordVerify(teacher.getTeacher_id(), teacher.getTeacher_mail(), teacher.getTeacher_phone()) == 0){
			//if student verify failed.
			return ResponseEntity.badRequest().body("請求失敗，Email或phone沒有找到");
		}else{
			dao.updateTeacherPassword(teacher.getTeacher_id(), id);
			String user_email = teacher.getTeacher_mail();
			String newpassword = id;
			mailservice.prepareAndSendwithTeacher(user_email, newpassword, teacher.getTeacher_id());
			return ResponseEntity.ok("寄新密碼成功!!");
		}

	}

	//teacher delete there student's in this class.
	//input std_id, cs_id.
	@DeleteMapping(value = "/teacher/Deletestudent")
	public ResponseEntity<String> deleteCustomer(@RequestBody Teacher teacher) throws IOException {

		if(userintheclass.queryStudentInTheClass(teacher.getStd_id(), teacher.getCs_id()) == 0){
			//if student does not belong to this class.
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		  }else{
			dao.DeletestudentManually(teacher);
			return ResponseEntity.ok("刪除此學生成功");
		  }
		
	}

	//teacher post there student's in this class.
	//input std_id, cs_id.
	@PostMapping(value = "/teacher/Addstudent")
	public ResponseEntity<String> StudentJoinTheClass(@RequestBody Teacher teacher) throws SQLException, IOException {

		if(userintheclass.queryStudentInTheClass(teacher.getStd_id(), teacher.getCs_id()) == 1){
			//if student does belong to this class.
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		  }else{
			  dao.AddstudentManually(teacher.getStd_id(), teacher.getCs_id());
			  return ResponseEntity.ok("新增此學生成功");
		  }

	}


}



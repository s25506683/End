package com.example.demo.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;
import java.io.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.StudentDAO;
import com.example.demo.entity.Student;

@Repository
public class StudentDAODB implements StudentDAO {

	@Autowired
	private DataSource dataSource;
	@Autowired
	JdbcTemplate jdbcTemplate;

//jdbcTemplate 

	// public void writeLog(String writtenmessage){
	// 	String directory = System.getProperty("user.dir");
	// 	String fileName = "src/main/java/com/example/demo/util/logfile.txt";
	// 	String absolutePath = directory + File.separator + fileName;
	// 	System.out.println(absolutePath);
		
	// 	try (FileWriter filewriter = new FileWriter(absolutePath,true)) {
	// 		String fileContent = writtenmessage + "\n";
	// 		filewriter.write(fileContent);
	// 		filewriter.flush();
	// 		filewriter.close();
	// 	} catch (IOException e) {
	// 		System.err.print("Something went wrong");
	// 	}
	// }



	public int queryUser(int std_id) {
		String sql = "select count(std_id) as count from student where std_id = ?";
		int count = this.jdbcTemplate.queryForObject(sql,Integer.class,std_id);
		return count;
	}

	public String getPassword(int std_id){
		String sql = "select std_password from student where std_id = ?";
		String std_password = this.jdbcTemplate.queryForObject(sql,String.class,std_id);
		return std_password;
	}

	//public int passwordHasRound(int std_id, String old_std_password){
		//String sql = "select count(std_id) as count from student where std_id = ? and std_password = ?";
		//int count = this.jdbcTemplate.queryForObject(sql,Integer.class,std_id, old_std_password);
		//return count;
	//}

	public int resetPasswordVerify(int std_id, String std_mail, String std_phone){
		String sql = "select count(std_id) as count from student where std_id = ? and std_mail = ? and std_phone = ?";
		int count = this.jdbcTemplate.queryForObject(sql,Integer.class,std_id, std_mail, std_phone);
		System.out.println(count);
		return count;
	}

	public int insert(Student student) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode(student.getStd_password());
		return jdbcTemplate.update(
				"insert into student (std_id , std_password, std_name, std_gender, std_department , std_phone, std_mail, std_image) values(?, ?, ?, ?, ?, ?, ?, ?)",
				student.getStd_id(), password, student.getStd_name(), student.getStd_gender(),
				student.getStd_department(), student.getStd_phone(), student.getStd_mail(), student.getStd_image());
	
	}

	public Student findOne(int std_id) {
		return this.jdbcTemplate.queryForObject("select * from student where std_id = ?",
				new Object[] { std_id }, new StudentMapper());
	}

	public List<Student> findAll() {
		return this.jdbcTemplate.query(
				"select std_id, std_password, std_name, std_gender, std_department, std_phone, std_mail, std_image from student",
				new StudentMapper());
	}

	private static final class StudentMapper implements RowMapper<Student> {

		public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
			Student student = new Student();
			student.setStd_id(rs.getInt("std_id"));
			student.setStd_password(rs.getString("std_password"));
			student.setStd_name(rs.getString("std_name"));
			student.setStd_gender(rs.getString("std_gender"));
			student.setStd_department(rs.getString("std_department"));
			student.setStd_phone(rs.getString("std_phone"));
			student.setStd_mail(rs.getString("std_mail"));
			student.setStd_image(rs.getString("std_image"));
			return student;
		}
	}


	public int update(Student student) {
    	return jdbcTemplate.update("update student set std_password=?,  std_name=?, std_gender=?, std_department=? , std_phone=?, std_mail=?, std_image=? where std_id =?",
		student.getStd_password(),student.getStd_name(), student.getStd_gender(), student.getStd_department(), student.getStd_phone(), student.getStd_mail(), student.getStd_image(), student.getStd_id());
 	}

 	public int updateStudentPassword(int std_id, String std_password){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode(std_password);
		return jdbcTemplate.update("update student set std_password = ? where std_id = ?",
		password, std_id);
	 }

	
	public int updateStudentMail(int std_id, String std_mail){
		return jdbcTemplate.update("update student set std_mail = ? where std_id = ?",
		std_mail,std_id);
	}


	public int delete(int std_id) {
		return jdbcTemplate.update("delete from registration where std_id =?", std_id);
	}

}

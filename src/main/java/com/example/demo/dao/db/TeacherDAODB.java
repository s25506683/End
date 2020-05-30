package com.example.demo.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.TeacherDAO;
import com.example.demo.entity.Teacher;
import com.example.demo.util.AuthenticationUtil;

@Repository
public class TeacherDAODB implements TeacherDAO {

	@Autowired
	private DataSource dataSource;
	@Autowired
	JdbcTemplate jdbcTemplate;

//jdbcTemplate 

	public int AddstudentManually(String std_id, String cs_id){
	return jdbcTemplate.update(
      "insert into class_student (std_id, cs_id) values(?, ?)",
      std_id, cs_id);
	}

	public int DeletestudentManually(Teacher teacher){
		return jdbcTemplate.update("delete from class_student where std_id = ? and cs_id = ?",
		teacher.getStd_id(), teacher.getCs_id());
	}

	public int resetPasswordVerify(int teacher_id, String teacher_mail, String teacher_phone){
		String sql = "select count(teacher_id) as count from teacher where teacher_id = ? and teacher_mail = ? and teacher_phone = ?";
		int count = this.jdbcTemplate.queryForObject(sql,Integer.class,teacher_id, teacher_mail, teacher_phone);
		return count;
	}

	public int queryUser(int teacher_id) {
		String sql = "select count(teacher_id) as count from teacher where teacher_id = ?";
		int count = this.jdbcTemplate.queryForObject(sql, Integer.class, teacher_id);
		return count;
	}

	public String getPassword(int teacher_id) {
		String sql = "select teacher_password from teacher where teacher_id = ?";
		String teacher_password = this.jdbcTemplate.queryForObject(sql, String.class, teacher_id);
		return teacher_password;
	}
	
	public int updateTeacherPassword(int teacher_id,String teacher_password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode(teacher_password);
		return jdbcTemplate.update("update teacher set teacher_password = ? where teacher_id = ?",
		password, teacher_id);
	}

	public int updateTeacherMail(int teacher_id, String teacher_mail){
		return jdbcTemplate.update("update teacher set teacher_mail = ? where teacher_id = ?",
		teacher_mail,teacher_id);
	}

	public int updateTeacherOffice(int teacher_id, String teacher_office){
		return jdbcTemplate.update("update teacher set teacher_office = ? where teacher_id = ?",
		teacher_office,teacher_id);
	}

	public int updateTeacherPhone(int teacher_id, String teacher_phone){
		return jdbcTemplate.update("update teacher set teacher_phone = ? where teacher_id = ?",
		teacher_phone,teacher_id);
	}

	public int insert(Teacher teacher) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode(teacher.getTeacher_password());
		return jdbcTemplate.update(
				"insert into teacher (teacher_id, teacher_password, teacher_name, teacher_gender, teacher_department, teacher_phone, teacher_mail, teacher_office, teacher_image) values(?, ?, ?, ?, ?, ?, ?, ?, ?)",
				teacher.getTeacher_id(), password, teacher.getTeacher_name(), teacher.getTeacher_gender(),
				teacher.getTeacher_department(), teacher.getTeacher_phone(), teacher.getTeacher_mail(),
				teacher.getTeacher_office(), teacher.getTeacher_image());
	}

	public Teacher findOne(int teacher_id) {
		return this.jdbcTemplate.queryForObject("select * from teacher where teacher_id = ?",
				new Object[] { teacher_id }, new TeacherMapper());
	}

	public List<Teacher> findAll() {
		return this.jdbcTemplate.query(
				"select teacher_id, teacher_password, teacher_name, teacher_gender, teacher_department, teacher_phone, teacher_mail, teacher_image from teacher",
				new TeacherMapper());
	}

	public List<Teacher> findStudentInformation(String std_id){
		return this.jdbcTemplate.query("select std_id, std_name, std_gender, std_department from student where std_id = ?"
		, new Object[]{std_id}, new TeacherMapper2());
	}

	private static final class TeacherMapper implements RowMapper<Teacher> {

		public Teacher mapRow(final ResultSet rs, final int rowNum) throws SQLException {
			final Teacher teacher = new Teacher();
			teacher.setTeacher_id(rs.getInt("teacher_id"));
			teacher.setTeacher_password(rs.getString("teacher_password"));
			teacher.setTeacher_name(rs.getString("teacher_name"));
			teacher.setTeacher_gender(rs.getString("teacher_gender"));
			teacher.setTeacher_department(rs.getString("teacher_department"));
			teacher.setTeacher_phone(rs.getString("teacher_phone"));
			teacher.setTeacher_mail(rs.getString("teacher_mail"));
			teacher.setTeacher_office(rs.getString("teacher_office"));
			teacher.setTeacher_image(rs.getString("teacher_image"));
			return teacher;
		}
	}


	private static final class TeacherMapper2 implements RowMapper<Teacher> {

		public Teacher mapRow(final ResultSet rs, final int rowNum) throws SQLException {
			final Teacher teacher = new Teacher();
			teacher.setStd_id(rs.getString("std_id"));
			teacher.setStd_name(rs.getString("std_name"));
			teacher.setStd_gender(rs.getString("std_gender"));
			teacher.setStd_department(rs.getString("std_department"));
			return teacher;
		}
	}



//	public int update(Student student) {
//		return jdbcTemplate.update("update registration set std_id=?, std_password=? where std_id =?",
//				student.getStd_id(), student.getStd_password());
//	}
//
//	public int delete(int std_id) {
//		return jdbcTemplate.update("delete from registration where std_id =?", std_id);
//	}

}

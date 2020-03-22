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

@Repository
public class TeacherDAODB implements TeacherDAO {

	@Autowired
	private DataSource dataSource;
	@Autowired
	JdbcTemplate jdbcTemplate;

//jdbcTemplate 

	public int queryUser(int teacher_id){
		String sql = "select count(teacher_id) as count from teacher where teacher_id = ?";
		int count = this.jdbcTemplate.queryForObject(sql,Integer.class,teacher_id);
		return count;
	}


	public int insert(Teacher teacher) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode(teacher.getTeacher_password());
		return jdbcTemplate.update(
				"insert into teacher (teacher_id, teacher_password, teacher_name, teacher_gender, teacher_department, teacher_phone, teacher_mail, teacher_image) values(?, ?, ?, ?, ?, ?, ?, ?)",
				teacher.getTeacher_id(), password, teacher.getTeacher_name(), teacher.getTeacher_gender(),
				teacher.getTeacher_department(), teacher.getTeacher_phone(), teacher.getTeacher_mail(), teacher.getTeacher_image());
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

	private static final class TeacherMapper implements RowMapper<Teacher> {

		public Teacher mapRow(ResultSet rs, int rowNum) throws SQLException {
			Teacher teacher = new Teacher();
			teacher.setTeacher_id(rs.getInt("teacher_id"));
			teacher.setTeacher_password(rs.getString("teacher_password"));
			teacher.setTeacher_name(rs.getString("teacher_name"));
			teacher.setTeacher_gender(rs.getString("teacher_gender"));
			teacher.setTeacher_department(rs.getString("teacher_department"));
			teacher.setTeacher_phone(rs.getString("teacher_phone"));
			teacher.setTeacher_mail(rs.getString("teacher_mail"));
			teacher.setTeacher_image(rs.getString("teacher_image"));
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

package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Student;

@Repository
public class StudentDAODB implements StudentDAO {

	@Autowired
	private DataSource dataSource;
	@Autowired
	JdbcTemplate jdbcTemplate;

//jdbcTemplate 

	public int insert(Student student) {
		return jdbcTemplate.update(
				"insert into student (std_id , std_password, std_name, std_gender, std_department , std_phone, std_mail, std_image) values(?, ?, ?, ?, ?, ?, ?, ?)",
				student.getStd_id(), student.getStd_password(), student.getStd_name(), student.getStd_gender(),
				student.getStd_department(), student.getStd_phone(), student.getStd_mail(), student.getStd_image());
	}

	public Student findOne(int std_id) {
		return this.jdbcTemplate.queryForObject("select std_id, std_password from registration where std_id = ?",
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
		return jdbcTemplate.update("update registration set std_id=?, std_password=? where std_id =?",
				student.getStd_id(), student.getStd_password());
	}

	public int delete(int std_id) {
		return jdbcTemplate.update("delete from registration where std_id =?", std_id);
	}

}

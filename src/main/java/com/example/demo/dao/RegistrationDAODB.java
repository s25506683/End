package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Registration;

@Repository
public class RegistrationDAODB implements RegistrationDAO {

	@Autowired
	private DataSource dataSource;
	@Autowired
	JdbcTemplate jdbcTemplate;

//jdbcTemplate 

	public int insert(Registration registration) {
		return jdbcTemplate.update("insert into registration (std_id , std_password) values(?, ?)",
				registration.getStd_id(), registration.getStd_password());
	}

	public Registration findOne(int std_id) {
		return this.jdbcTemplate.queryForObject("select std_id, std_password from registration where std_id = ?",
				new Object[] { std_id }, new RegistrationMapper());
	}

	public List<Registration> findAll() {
		return this.jdbcTemplate.query("select std_id, std_password from registration", new RegistrationMapper());
	}

	private static final class RegistrationMapper implements RowMapper<Registration> {

		public Registration mapRow(ResultSet rs, int rowNum) throws SQLException {
			Registration registration = new Registration();
			registration.setStd_id(rs.getInt("std_id"));
			registration.setStd_password(rs.getInt("std_password"));
			return registration;
		}
	}

	public int update(Registration registration) {
		return jdbcTemplate.update(
				"update registration set std_id=?, std_password=? where std_id =?",
				registration.getStd_id(), registration.getStd_password());
	}

	public int delete(int std_id) {
		return jdbcTemplate.update("delete from registration where std_id =?", std_id);
	}

}

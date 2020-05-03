package com.example.demo.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.CourseDAO;
import com.example.demo.entity.Course;

@Repository
public class CourseDAODB implements CourseDAO {

 @Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;

//jdbcTemplate 


public int TeacherNewCourse(Course course) {
    return jdbcTemplate.update(
      "insert into class (cs_id, cs_name, cs_photo) values(?, ?, ?)",
      course.getCs_id(), course.getCs_name(), course.getCs_photo());
      
 }

 
 
}



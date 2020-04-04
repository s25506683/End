package com.example.demo.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.util.AuthenticationUtil;
import com.example.demo.util.CurrentTimeStamp;

@Repository
public class UserInTheClass {
 
 
 
 @Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;
//jdbcTemplate 


public int queryStudentInTheClass(String std_id, String cs_id) {
    String sql = "select count(std_id) as count from class_student where std_id = ? and cs_id = ?";
    int count = this.jdbcTemplate.queryForObject(sql,Integer.class,std_id,cs_id);
    return count;
  }
  
  public int queryTeacherInTheClass(String teacher_id, String cs_id) {
    String sql = "select count(teacher_id) as count from class_teacher where teacher_id = ? and cs_id = ?";
    int count = this.jdbcTemplate.queryForObject(sql,Integer.class,teacher_id,cs_id);
    return count;
  }

}
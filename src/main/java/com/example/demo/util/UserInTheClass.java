package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserInTheClass {

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
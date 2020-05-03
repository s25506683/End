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

  public List<Course> findClassStudentList(final String cs_id){
    return this.jdbcTemplate.query("select s.std_id, s.std_name, s.std_department from student s inner join class_student cs on cs.std_id = s.std_id where cs.cs_id = ?"
    , new Object[]{cs_id}, new RollcallMapper());
  }

  private static final class RollcallMapper implements RowMapper<Course> {
    public Course mapRow(final ResultSet rs, final int rowNum) throws SQLException {
       final Course course = new Course();
        course.setStd_id(rs.getInt("std_id"));
        course.setStd_name(rs.getString("std_name"));
        course.setStd_department(rs.getString("std_department"));
        return course;
    }
  }


 
}



package com.example.demo.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.CourseDAO;
import com.example.demo.entity.Course;

@Repository
public class CourseDAODB implements CourseDAO {

 @Autowired
 JdbcTemplate jdbcTemplate;

//jdbcTemplate 


  public String findClassName(String cs_id){
    String sql = "select cs_name from class where cs_id = ?";
    String cs_name = this.jdbcTemplate.queryForObject(sql,String.class,cs_id);
    return cs_name;
  }

  public int TeacherCreateCourse(Course course) {
    return jdbcTemplate.update(
      "insert into class (cs_id, cs_name) values(?, ?)",
      course.getCs_id(), course.getCs_name());
  }

  public int TeacherAddToClass(int teacher_id, String cs_id){
    return jdbcTemplate.update(
      "insert into class_teacher (teacher_id, cs_id) values(?, ?)",
      teacher_id, cs_id);
  }

  public int StudentJoinClass(int std_id, String cs_id){
    return jdbcTemplate.update(
      "insert into class_student (std_id, cs_id) values(?, ?)",
      std_id, cs_id);
  }

  public int hasSameClassId(String cs_id){
    String sql = "select count(cs_id) as count from class where cs_id = ?";
    int count = this.jdbcTemplate.queryForObject(sql,Integer.class,cs_id);
    return count;
  }

  public int hasThisQRcode(String cs_qrcode){
    String sql = "select count(cs_qrcode) as count from class where cs_qrcode = ?";
    int count = this.jdbcTemplate.queryForObject(sql,Integer.class,cs_qrcode);
    return count;
  }

  public String findThisCsId(String cs_qrcode){
    String sql = "select cs_id as count from class where cs_qrcode = ?";
    String cs_id = this.jdbcTemplate.queryForObject(sql,String.class,cs_qrcode);
    return cs_id;
  }

  public Course findTeacherInformation(String cs_id){
    return this.jdbcTemplate.queryForObject( "select t.* from teacher t join class_teacher ct on ct.teacher_id = t.teacher_id where cs_id = ?"
    , new Object[]{cs_id}, new RollcallMapper2());
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

  private static final class RollcallMapper2 implements RowMapper<Course> {
    public Course mapRow(final ResultSet rs, final int rowNum) throws SQLException {
       final Course course = new Course();
        course.setTeacher_name(rs.getString("teacher_name"));
        course.setTeacher_department(rs.getString("teacher_department"));
        course.setTeacher_phone(rs.getString("teacher_phone"));
        course.setTeacher_mail(rs.getString("teacher_mail"));
        course.setTeacher_office(rs.getString("teacher_office"));
        return course;
    }
  }

  public int updateCsQRcode(String cs_id, String cs_qrcode){
    return jdbcTemplate.update(
        "update class set cs_qrcode = ? where cs_id = ?",
        cs_qrcode, cs_id);
  }

  public int closeJoinClass(String cs_id){
    return jdbcTemplate.update(
        "update class set cs_qrcode = null where cs_id = ?",
        cs_id);
  }


 
}



package com.example.demo.dao.db;

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

import com.example.demo.dao.HomePage1_sDAO;
import com.example.demo.entity.HomePage1_s;

@Repository
public class HomePage1_sDAODB implements HomePage1_sDAO {

 @Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;

String sql = new String();

/* public int queryCourseCount(int std_id){
    String sql = "select count(c.cs_id) as count from class c inner join class_student cs on c.cs_id = cs.cs_id where cs.std_id = ? order by c.cs_id";
    int count = this.jdbcTemplate.queryForObject(sql,Integer.class,std_id);
    return count;
}*/

//  public int insert(HomePage1_s homepage1_s) {
//     return jdbcTemplate.update(
//       "insert into homepage1_s (q_id, q_std_id, q_content, cs_id) values(?, ?, ?, ?)",
//       homepage1_s.getQ_id(), homepage1_s.getQ_std_id(), homepage1_s.getQ_content(), homepage1_s.getCs_id());
//  }

 public List<HomePage1_s> findStudentCourse(int std_id) {
     sql = "select c.cs_id, c.cs_name, c.cs_photo, t.teacher_name,cs.std_id,st.std_name from class c inner join class_student cs on c.cs_id = cs.cs_id inner join student st on cs.std_id = st.std_id inner join class_teacher ct on c.cs_id = ct.cs_id inner join teacher t on ct.teacher_id = t. teacher_id where cs.std_id = ? order by c.cs_id";
    return this.jdbcTemplate.query( sql, new Object[]{std_id}, new HomePage1_sMapper_findStudentCourse());
  }

  public List<HomePage1_s> findTeacherCourse(int teacher_id) {
      sql = "select c.cs_id, c.cs_name, c.cs_photo, t.teacher_name,ct.teacher_id from class c inner join class_teacher ct on c.cs_id = ct.cs_id inner join teacher t on ct.teacher_id = t. teacher_id where ct.teacher_id = ? order by c.cs_id";
    return this.jdbcTemplate.query( sql, new Object[]{teacher_id}, new HomePage1_sMapper_findTeacherCourse());
  }

 public List<HomePage1_s> findAllCourse() {
     sql = "select distinct c.cs_id, c.cs_name, c.cs_photo, t.teacher_name from class c inner join class_student cs on c.cs_id = cs.cs_id inner join class_teacher ct on c.cs_id = ct.cs_id inner join teacher t on ct.teacher_id = t. teacher_id order by c.cs_id";
     return this.jdbcTemplate.query( sql, new HomePage1_sMapper2());
 }

 private static final class HomePage1_sMapper_findStudentCourse implements RowMapper<HomePage1_s> {
     public HomePage1_s mapRow(ResultSet rs, int rowNum) throws SQLException {
        HomePage1_s homepage1_s = new HomePage1_s();
         homepage1_s.setCs_id(rs.getString("cs_id"));
         homepage1_s.setCs_name(rs.getString("cs_name"));
         homepage1_s.setCs_photo(rs.getString("cs_photo"));
         homepage1_s.setTeacher_name(rs.getString("teacher_name"));
         homepage1_s.setStd_id(rs.getInt("std_id"));
         homepage1_s.setStd_name(rs.getString("std_name"));
         return homepage1_s;
     }
 }

 private static final class HomePage1_sMapper_findTeacherCourse implements RowMapper<HomePage1_s> {
    public HomePage1_s mapRow(ResultSet rs, int rowNum) throws SQLException {
       HomePage1_s homepage1_s = new HomePage1_s();
        homepage1_s.setCs_id(rs.getString("cs_id"));
        homepage1_s.setCs_name(rs.getString("cs_name"));
        homepage1_s.setCs_photo(rs.getString("cs_photo"));
        homepage1_s.setTeacher_name(rs.getString("teacher_name"));
        homepage1_s.setTeacher_id(rs.getInt("teacher_id"));
        return homepage1_s;
    }
}

 private static final class HomePage1_sMapper2 implements RowMapper<HomePage1_s> {
    public HomePage1_s mapRow(ResultSet rs, int rowNum) throws SQLException {
       HomePage1_s homepage1_s2 = new HomePage1_s();
        homepage1_s2.setCs_id(rs.getString("cs_id"));
        homepage1_s2.setCs_name(rs.getString("cs_name"));
        homepage1_s2.setCs_photo(rs.getString("cs_photo"));
        homepage1_s2.setTeacher_name(rs.getString("teacher_name"));
        return homepage1_s2;
    }
}
//  public int update(HomePage1_s homepage1_s) {
//     return jdbcTemplate.update(
//       "update question set q_std_id=?, q_content=?, cs_id=? where q_std_id =?",
//       homepage1_s.getQ_std_id(), homepage1_s.getQ_content(), homepage1_s.getCs_id(), homepage1_s.getQ_std_id());
//  }

//  public int delete(int id) {
//     return jdbcTemplate.update(
//       "delete from question where q_std_id =?", id);
//  }

 
 
}



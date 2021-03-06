package com.example.demo.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.HomePage1_sDAO;
import com.example.demo.entity.HomePage1_s;
import com.example.demo.util.AuthenticationUtil;

@Repository
public class HomePage1_sDAODB implements HomePage1_sDAO {

 @Autowired
 JdbcTemplate jdbcTemplate;
 
 String sql = new String();



public int queryUserRole(int user_id){
    String sql = "select count(teacher_id) as count from teacher where teacher_id = ?";
    int count = this.jdbcTemplate.queryForObject(sql,Integer.class,user_id);
    return count;
}


 public List<HomePage1_s> findStudentCourse(int std_id) {
     System.out.println(AuthenticationUtil.class.getName());
     sql = "select c.cs_id, c.cs_name, c.cs_photo, t.teacher_name,cs.std_id,st.std_name from class c inner join class_student cs on c.cs_id = cs.cs_id inner join student st on cs.std_id = st.std_id inner join class_teacher ct on c.cs_id = ct.cs_id inner join teacher t on ct.teacher_id = t. teacher_id where cs.std_id = ? and c.cs_close = 0 order by c.cs_id";
    return this.jdbcTemplate.query( sql, new Object[]{std_id}, new HomePage1_sMapper_findStudentCourse());
  }

  public List<HomePage1_s> findTeacherCourse(int teacher_id) {
        sql = "select c.cs_id, c.cs_name, c.cs_photo, t.teacher_name, ct.teacher_id, count(csst.std_id) as std_count from class c  join class_teacher ct on c.cs_id = ct.cs_id  join teacher t on ct.teacher_id = t. teacher_id left join class_student csst on csst.cs_id = c.cs_id where ct.teacher_id = ? and c.cs_close = 0 group by c.cs_id order by c.cs_id";
    return this.jdbcTemplate.query( sql, new Object[]{teacher_id}, new HomePage1_sMapper_findTeacherCourse());
  }

 public List<HomePage1_s> findAllCourse() {
     sql = "select distinct c.cs_id, c.cs_name, c.cs_photo, t.teacher_name from class c inner join class_student cs on c.cs_id = cs.cs_id inner join class_teacher ct on c.cs_id = ct.cs_id inner join teacher t on ct.teacher_id = t. teacher_id order by c.cs_id";
     return this.jdbcTemplate.query( sql, new HomePage1_sMapper2());
 }

 public HomePage1_s queryUserRoleByJson(int user_id){
    return this.jdbcTemplate.queryForObject("select count(teacher_id) as user_role from teacher where teacher_id = ?",
    new Object[] {user_id}, new HomePage1_sMapper3());
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
        homepage1_s.setStd_count(rs.getInt("std_count"));
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

private static final class HomePage1_sMapper3 implements RowMapper<HomePage1_s> {
    public HomePage1_s mapRow(ResultSet rs, int rowNum) throws SQLException {
       HomePage1_s homepage1_s3 = new HomePage1_s();
        homepage1_s3.setUser_role(rs.getInt("user_role"));
        return homepage1_s3;
    }
}

 
 
}



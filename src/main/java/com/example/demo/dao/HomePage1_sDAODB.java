package com.example.demo.dao;

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

import com.example.demo.entity.HomePage1_s;

@Repository
public class HomePage1_sDAODB implements HomePage1_sDAO {

 @Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;

//jdbcTemplate 

 public int insert(HomePage1_s homepage1_s) {
    return jdbcTemplate.update(
      "insert into homepage1_s (q_id, q_std_id, q_content, cs_id) values(?, ?, ?, ?)",
      homepage1_s.getQ_id(), homepage1_s.getQ_std_id(), homepage1_s.getQ_content(), homepage1_s.getCs_id());
 }

 public HomePage1_s findOne(String cs_id, int std_id) {
    return this.jdbcTemplate.queryForObject( "select q.q_id, q.q_std_id, q.q_content, c.cs_id, c.cs_name, q_time from question q inner join class c on c.cs_id = q.cs_id where c.cs_id = ? and q.q_std_id = ? ", new Object[]{cs_id,std_id}, new HomePage1_sMapper());
  }

 public List<HomePage1_s> findAll(String cs_id) {
     return this.jdbcTemplate.query( "select q.q_id, q.q_std_id, q.q_content, c.cs_id, c.cs_name, q_time from question q inner join class c on c.cs_id = q.cs_id where c.cs_id = ? order by q.q_time", new Object[]{cs_id}, new HomePage1_sMapper());
 }

 private static final class HomePage1_sMapper implements RowMapper<HomePage1_s> {
  //private SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
     public HomePage1_s mapRow(ResultSet rs, int rowNum) throws SQLException {
        HomePage1_s homepage1_s = new HomePage1_s();
         homepage1_s.setQ_id(rs.getInt("q_id"));
         homepage1_s.setQ_std_id(rs.getInt("q_std_id"));
         homepage1_s.setQ_content(rs.getString("q_content"));
         homepage1_s.setCs_id(rs.getString("cs_id"));
         homepage1_s.setCs_name(rs.getString("cs_name"));
         //df.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
         //question.setQ_time(df.format(rs.getTimestamp("q_time")));
         homepage1_s.setQ_time(rs.getTime("q_time"));
         return homepage1_s;
     }
 }
 public int update(HomePage1_s homepage1_s) {
    return jdbcTemplate.update(
      "update question set q_std_id=?, q_content=?, cs_id=? where q_std_id =?",
      homepage1_s.getQ_std_id(), homepage1_s.getQ_content(), homepage1_s.getCs_id(), homepage1_s.getQ_std_id());
 }

 public int delete(int id) {
    return jdbcTemplate.update(
      "delete from question where q_std_id =?", id);
 }

 
}



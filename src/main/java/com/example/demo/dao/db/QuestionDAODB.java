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

import com.example.demo.dao.QuestionDAO;
import com.example.demo.entity.Question;

@Repository
public class QuestionDAODB implements QuestionDAO {

 @Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;

//jdbcTemplate 

 public int insert(Question question) {
    return jdbcTemplate.update(
      "insert into question (q_id, q_std_id, q_content, cs_id) values(?, ?, ?, ?)",
      question.getQ_id(), question.getQ_std_id(), question.getQ_content(), question.getCs_id());
 }

 public Question findOne(String cs_id, int std_id) {
    return this.jdbcTemplate.queryForObject( "select q.q_id, q.q_std_id, q.q_content, c.cs_id, c.cs_name, q_time from question q inner join class c on c.cs_id = q.cs_id where c.cs_id = ? and q.q_std_id = ? ", new Object[]{cs_id,std_id}, new QuestionMapper());
  }

 public List<Question> findAll(String cs_id) {
     return this.jdbcTemplate.query( "select q.q_id, q.q_std_id, q.q_content, c.cs_id, c.cs_name, q_time from question q inner join class c on c.cs_id = q.cs_id where c.cs_id = ? order by q.q_time", new Object[]{cs_id}, new QuestionMapper());
 }

 private static final class QuestionMapper implements RowMapper<Question> {
  //private SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
     public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
         Question question = new Question();
         question.setQ_id(rs.getInt("q_id"));
         question.setQ_std_id(rs.getInt("q_std_id"));
         question.setQ_content(rs.getString("q_content"));
         question.setCs_id(rs.getString("cs_id"));
         question.setCs_name(rs.getString("cs_name"));
         //df.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
         //question.setQ_time(df.format(rs.getTimestamp("q_time")));
         question.setQ_time(rs.getTime("q_time"));
         return question;
     }
 }
 public int update(Question question) {
    return jdbcTemplate.update(
      "update question set q_std_id=?, q_content=?, cs_id=? where q_std_id =?",
      question.getQ_std_id(), question.getQ_content(), question.getCs_id(), question.getQ_std_id());
 }

 public int delete(int id) {
    return jdbcTemplate.update(
      "delete from question where q_std_id =?", id);
 }

 
}



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
import com.example.demo.util.AuthenticationUtil;
import com.example.demo.util.CurrentTimeStamp;

@Repository
public class QuestionDAODB implements QuestionDAO {

 @Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;
//jdbcTemplate 

public String findClassName(String cs_id){
  String sql = "select cs_name from class where cs_id = ?";
  String Csname = this.jdbcTemplate.queryForObject(sql, String.class, cs_id);
  return Csname;
}

public String findUserMail(int std_id){
  String sql = "select std_mail from student where std_id = ?";
  String mail = this.jdbcTemplate.queryForObject(sql, String.class, std_id);
  return mail;
}

public int hasThisStudentInQuestion(String std_id, String cs_id){
  String sql = "select count(q_std_id) from question where q_std_id = ? and cs_id = ?";
  int count = this.jdbcTemplate.queryForObject(sql, Integer.class, std_id, cs_id);
  return count;
}

public String findQuestionAsktime(String std_id, String cs_id){
  String sql = "select q_asktime from question where q_std_id = ?and cs_id = ? ORDER BY q_asktime desc LIMIT 1";
  String Asktime = this.jdbcTemplate.queryForObject(sql, String.class, std_id, cs_id);
  return Asktime;
}

public int queryCs_id(String cs_id) {
  String sql = "select count(cs_id) as count from class where cs_id = ?";
  int count = this.jdbcTemplate.queryForObject(sql,Integer.class,cs_id);
  return count;
}

public int hasBeenReply(int std_id, String q_asktime){
  String sql = "select q_solved from question where q_std_id = ? and q_asktime = ?";
  int q_solved = this.jdbcTemplate.queryForObject(sql,Integer.class, std_id, q_asktime);
  return q_solved;
}

public int hasQuestion(int std_id, String q_asktime){
  String sql = "select count(q_std_id) as count from question where q_std_id = ? and q_asktime = ?";
  int count = this.jdbcTemplate.queryForObject(sql,Integer.class, std_id, q_asktime);
  return count;
}

 public int studentinsert(final Question question) {
   AuthenticationUtil auth = new AuthenticationUtil();
   String std_id = auth.getCurrentUserName();
   CurrentTimeStamp ts = new CurrentTimeStamp();
   String timestamp = ts.getCurrentTimeStamp();
    return jdbcTemplate.update(
      "insert into question (q_std_id, q_content, q_asktime, cs_id) values(?, ?, ?, ?)",
      std_id, question.getQ_content(), timestamp, question.getCs_id());
 }

 /*public Question findOne(final String cs_id, final int std_id) {
    return this.jdbcTemplate.queryForObject( "select q.q_id, q.q_std_id, q.q_content, c.cs_id, c.cs_name, q_time, q_solved from question q inner join class c on c.cs_id = q.cs_id where c.cs_id = ? and q.q_std_id = ? ", new Object[]{cs_id,std_id}, new QuestionMapper());
  }*/

 public List<Question> findQuestion(final String cs_id) {
     return this.jdbcTemplate.query( "select q.q_id, q.q_std_id, q.q_content, q_reply, c.cs_id, c.cs_name, q.q_asktime, q.q_replytime, q.q_solved from question q inner join class c on c.cs_id = q.cs_id where c.cs_id = ? order by q.q_asktime", new Object[]{cs_id}, new QuestionMapper());
 }

 public String findCsId(int std_id, String q_asktime){
  String sql = "select cs_id from question where q_std_id = ? and q_asktime = ?";
  String cs_id = this.jdbcTemplate.queryForObject(sql,String.class, std_id, q_asktime);
  return cs_id;
 }

 private static final class QuestionMapper implements RowMapper<Question> {
  private SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
     public Question mapRow(final ResultSet rs, final int rowNum) throws SQLException {
         final Question question = new Question();
         question.setQ_id(rs.getInt("q_id"));
         question.setQ_std_id(rs.getInt("q_std_id"));
         question.setQ_content(rs.getString("q_content"));
         question.setQ_reply(rs.getString("q_reply"));
         question.setCs_id(rs.getString("cs_id"));
         question.setCs_name(rs.getString("cs_name"));
         //df.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
         question.setQ_asktime(df.format(rs.getTimestamp("q_asktime")));
         question.setQ_replytime(rs.getString("q_replytime"));
         //question.setQ_time(rs.getTime("q_time"));
         question.setQ_solved(rs.getString("q_solved"));
         return question;
     }
 }



public int updateStudentQuestionContent(final Question question) {
  AuthenticationUtil auth = new AuthenticationUtil();
  String std_id = auth.getCurrentUserName();
  return jdbcTemplate.update(
    "update question set q_content = ? where q_std_id = ? and q_asktime = ?",
    question.getQ_content(), std_id, question.getQ_asktime());
}


 public int updateTeacherReply(final Question question) {
  CurrentTimeStamp ts = new CurrentTimeStamp();
  String timestamp = ts.getCurrentTimeStamp();
    return jdbcTemplate.update(
      "update question set q_reply = ?, q_replytime = ?, q_solved = 1 where q_std_id = ? and q_asktime = ?",
      question.getQ_reply(), timestamp, question.getQ_std_id(), question.getQ_asktime());
 }

 public int deleteQuestion(Question question) {
    return jdbcTemplate.update(
      "delete from question where q_std_id = ? and q_asktime = ?", question.getQ_std_id(), question.getQ_asktime());
 }

 //delete teacher reply?? (have to set q_solved to 0).
 public int deleteQuestionReply(Question question) {
  return jdbcTemplate.update(
    "update question set q_reply = null, q_solved = 0, q_replytime = null where q_std_id = ? and q_asktime = ?", question.getQ_std_id(), question.getQ_asktime());
}
 //顯示學生自己問過的問題
}



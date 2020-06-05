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


public int changeSolvedState(String std_id, String q_asktime){
  String sql = "update question set q_solved = 1 where q_std_id = ? and q_asktime = ?";
  int q_solved = this.jdbcTemplate.queryForObject(sql,Integer.class, std_id, q_asktime);
  return q_solved;
}

public int checkTheQuestionHasBeenSolved(int q_id){
  String sql = "select q_solved from question where q_id = ?";
  int solved = this.jdbcTemplate.queryForObject(sql, Integer.class, q_id);
  return solved;
}

public int findQuestionSolved(String cs_id, String q_asktime){
  String sql = "select q_solved from question where cs_id = ? and q_asktime = ?";
  int solved = this.jdbcTemplate.queryForObject(sql, Integer.class, cs_id, q_asktime);
  return solved;
}

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

public int hasThisStudentInPersonQuestion(String std_id, String cs_id){
  String sql = "select count(std_id) from personal_question where std_id = ? and cs_id = ?";
  int count = this.jdbcTemplate.queryForObject(sql, Integer.class, std_id, cs_id);
  return count;
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

public int TeacherAddNewMessages(Question question){
  CurrentTimeStamp ts = new CurrentTimeStamp();
  String timestamp = ts.getCurrentTimeStamp();
    return jdbcTemplate.update(
      "insert into comment_box (q_id, cb_content, cb_time, cb_role) values(?, ?, ?, 1)",
      question.getQ_id(), question.getCb_content(), timestamp);
}

public int StudentAddNewMessages(Question question){
  AuthenticationUtil auth = new AuthenticationUtil();
  String std_id = auth.getCurrentUserName();
  CurrentTimeStamp ts = new CurrentTimeStamp();
  String timestamp = ts.getCurrentTimeStamp();
    return jdbcTemplate.update(
      "insert into comment_box (q_id, std_id, cb_content, cb_time, cb_role) values(?, ?, ?, ?, 0)",
      question.getQ_id(), std_id, question.getCb_content(), timestamp);
}

public int teacherinsert(Question question){
  CurrentTimeStamp ts = new CurrentTimeStamp();
  String timestamp = ts.getCurrentTimeStamp();
    return jdbcTemplate.update(
      "insert into question (q_content, q_asktime, cs_id, q_role) values(?, ?, ?, 1)",
      question.getQ_content(), timestamp, question.getCs_id()); 
}

public int studentinsert(final Question question) {
  AuthenticationUtil auth = new AuthenticationUtil();
  String std_id = auth.getCurrentUserName();
  CurrentTimeStamp ts = new CurrentTimeStamp();
  String timestamp = ts.getCurrentTimeStamp();
    return jdbcTemplate.update(
      "insert into question (q_std_id, q_content, q_asktime, cs_id, q_role) values(?, ?, ?, ?, 0)",
      std_id, question.getQ_content(), timestamp, question.getCs_id());
 }

 /*public Question findOne(final String cs_id, final int std_id) {
    return this.jdbcTemplate.queryForObject( "select q.q_id, q.q_std_id, q.q_content, c.cs_id, c.cs_name, q_time, q_solved from question q inner join class c on c.cs_id = q.cs_id where c.cs_id = ? and q.q_std_id = ? ", new Object[]{cs_id,std_id}, new QuestionMapper());
  }*/

 public List<Question> findSolvedQuestion(final String cs_id) {
     return this.jdbcTemplate.query( "select count(cb.q_id) as count, q.q_id, q.q_std_id, q.q_content, c.cs_id, c.cs_name, q.q_asktime, q.q_solved from question q join class c on c.cs_id = q.cs_id left join comment_box cb on q.q_id = cb.q_id where q.cs_id = ? and q.q_solved = 1 group by q.q_id order by q.q_asktime"
     , new Object[]{cs_id}, new QuestionMapper());
 }

 public List<Question> findUnresolvedQuestion(final String cs_id) {
  return this.jdbcTemplate.query( "select count(cb.q_id) as count, q.q_id, q.q_std_id, q.q_content, c.cs_id, c.cs_name, q.q_asktime, q.q_solved from question q join class c on c.cs_id = q.cs_id left join comment_box cb on q.q_id = cb.q_id where q.cs_id = ? and q.q_solved = 0 group by q.q_id order by q.q_asktime"
  , new Object[]{cs_id}, new QuestionMapper());
}

 public List<Question> findAllQuestionsThisStudentAsked(String std_id, String cs_id){
     return this.jdbcTemplate.query("select q_content, q_asktime from question where q_std_id = ? and cs_id = ?"
     , new Object[]{std_id, cs_id}, new QuestionMapper2());
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
         question.setCount(rs.getInt("count"));
         question.setQ_id(rs.getInt("q_id"));
         question.setQ_std_id(rs.getInt("q_std_id"));
         question.setQ_content(rs.getString("q_content"));
         question.setCs_id(rs.getString("cs_id"));
         question.setCs_name(rs.getString("cs_name"));
         //df.setTimeZone(TimeZone.getTimeZone("Asia/Taipei"));
         question.setQ_asktime(df.format(rs.getTimestamp("q_asktime")));
         //question.setQ_time(rs.getTime("q_time"));
         question.setQ_solved(rs.getString("q_solved"));
         return question;
     }
 }

 private static final class QuestionMapper2 implements RowMapper<Question> {
     public Question mapRow(final ResultSet rs, final int rowNum) throws SQLException {
         final Question question = new Question();
         question.setQ_content(rs.getString("q_content"));
         question.setQ_asktime(rs.getString("q_asktime"));
         return question;
     }
 }


 public int TeacherCompletionQuestion(Question question){
  return jdbcTemplate.update(
    "update question set q_solved = 1 where cs_id = ? and q_asktime = ?",
    question.getCs_id(), question.getQ_asktime()); 
}


public int StudentCompletionQuestion(Question question){
  AuthenticationUtil auth = new AuthenticationUtil();
  String std_id = auth.getCurrentUserName();
  return jdbcTemplate.update(
    "update question set q_solved = 1 where q_std_id = ? and q_asktime = ?",
    std_id, question.getQ_asktime()); 
}

public int updateStudentCommentBoxContent(final Question question) {
  AuthenticationUtil auth = new AuthenticationUtil();
  String std_id = auth.getCurrentUserName();
  return jdbcTemplate.update(
    "update comment_box set cb_content = ? where std_id = ? and cb_time = ?",
    question.getCb_content(), std_id, question.getCb_time());
}

public int updateTeacherCommentBoxContent(final Question question) {
  return jdbcTemplate.update(
    "update comment_box set cb_content = ? where q_id = ? and cb_time = ?",
    question.getCb_content(), question.getQ_id(), question.getCb_time());
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
      "update question set q_reply = ?, q_replytime = ? where q_std_id = ? and q_asktime = ?",
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

public int deleteStudentMessages(Question question){
  AuthenticationUtil auth = new AuthenticationUtil();
  String std_id = auth.getCurrentUserName();
  return jdbcTemplate.update(
  "delete from comment_box where std_id = ? and cb_time = ?"
  , std_id, question.getCb_time());
}

public int deleteTeacherMessages(Question question){
  return jdbcTemplate.update( 
  "delete from comment_box where q_id = ? and cb_time = ?"
  , question.getQ_id(), question.getCb_time());
}




}



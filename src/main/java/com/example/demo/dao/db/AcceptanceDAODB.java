package com.example.demo.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.AcceptanceDAO;
import com.example.demo.entity.Acceptance;
import com.example.demo.util.AuthenticationUtil;
import com.example.demo.util.CurrentTimeStamp;

@Repository
public class AcceptanceDAODB implements AcceptanceDAO {

 @Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;

//jdbcTemplate 

public int queryStudentAcceptDone(int accept_std_id, boolean accept_done, int accept_hw_id){
  String sql = "select count(accept_std_id) as count from acceptance where accept_std_id = ? and accept_done = ? and accept_hw_id = ?";
  int count = this.jdbcTemplate.queryForObject(sql, Integer.class,accept_std_id,accept_done,accept_hw_id);
  return count; //查看學生是否已經驗收完成 
}

public int findHomeworkID(String cs_id, String hw_name) {
  String sql = "select hw_id from homework where hw_cs_id = ? and hw_name = ?";
  int homeworkId = this.jdbcTemplate.queryForObject(sql, Integer.class, cs_id, hw_name);
  return homeworkId; //透過hw_id找hw_name
}

public String findCsID(int hw_id){
  String sql = "select hw_cs_id from homework where hw_id = ?";
  String CsId = this.jdbcTemplate.queryForObject(sql, String.class ,hw_id);
  return CsId;
}

public String findUnAcceptRejectStudent(int hw_id){
  String sql = "select group_concat( accept_std_id SEPARATOR ',' ) from acceptance where accept_done = 0 and accept_hw_id = ? and( accept_score > 0 or accept_tag = 1 or accept_content != \"\" or accept_state = 2 )";
  String rejectStudent = this.jdbcTemplate.queryForObject(sql, String.class ,hw_id);
  return rejectStudent;
}

public int hasThisHomework(String cs_id, String hw_name){
  String sql = "select count(hw_name) as count from homework where hw_cs_id = ? and hw_name = ?";
  int count = this.jdbcTemplate.queryForObject(sql, Integer.class, cs_id, hw_name);
  return count;
}

public int hasInLine(String cs_id, String hw_name, int std_id){
  String sql = "select count(act.accept_std_id) as count from homework hw join acceptance act on act.accept_hw_id = hw.hw_id where hw.hw_cs_id = ? and hw.hw_name = ? and act.accept_std_id = ?";
  int count = this.jdbcTemplate.queryForObject(sql, Integer.class, cs_id, hw_name, std_id);
  return count;
}

public int hasInQuestionLine(String cs_id, String hw_name, int std_id){
  String sql = "select count(act.accept_std_id) as count from homework hw join acceptance act on act.accept_hw_id = hw.hw_id where hw.hw_cs_id = ? and hw.hw_name = ? and act.accept_std_id = ? and act.accept_state = 0";
  int count = this.jdbcTemplate.queryForObject(sql, Integer.class, cs_id, hw_name, std_id);
  return count;
}

public int hasAcceptDone(String cs_id, String hw_name, int std_id){
  String sql = "select act.accept_done from homework hw join acceptance act on act.accept_hw_id = hw.hw_id where hw.hw_cs_id = ? and hw.hw_name = ? and act.accept_std_id = ?";
  int accept_done = this.jdbcTemplate.queryForObject(sql, Integer.class, cs_id, hw_name, std_id);
  return accept_done;
}

public int hasRejectByTeacher(String cs_id, String hw_name, int std_id){
  String sql = "select act.accept_state as count from homework hw join acceptance act on act.accept_hw_id = hw.hw_id where hw.hw_cs_id = ? and hw.hw_name = ? and act.accept_std_id = ?";
  int accept_state = this.jdbcTemplate.queryForObject(sql, Integer.class, cs_id, hw_name, std_id);

  int hasReject = 0;
  if(accept_state == 2){
    hasReject = 1;
  }
  return hasReject;
}

public int hasHomeworkClosed(int hw_id){
  String sql = "select hw_closed from homework where hw_id = ?";
  int hasClosed = this.jdbcTemplate.queryForObject(sql,Integer.class,hw_id);
  return hasClosed;
}

public int queryHomeworkID(int hw_id){
  String sql = "select count(hw_id) as count from homework where hw_id = ?";
  int count = this.jdbcTemplate.queryForObject(sql,Integer.class,hw_id);
  return count;
}

public int queryStudentInTheAcceptance(int accept_std_id, int accept_hw_id){
  String sql = "select count(accept_std_id) as count from acceptance where accept_std_id = ? and accept_hw_id = ? ";
  int count = this.jdbcTemplate.queryForObject(sql, Integer.class,accept_std_id,accept_hw_id);
  return count; //看這筆驗收中有沒有這個學生
}

// public int queryHomeworkInTheAcceptance(final int accept_std_id, final String hw_name){
//   final String sql = "select count(accept_std_id) as count from acceptance where accept_std_id = ? and hw_name = ? ";
//   final int count = this.jdbcTemplate.queryForObject(sql, Integer.class,accept_std_id,hw_name);
//   return count; //這個學生有沒有驗收過這個作業(看作業名稱)
// }

public int queryHomeworkInTheClass(final String hw_name, final String hw_cs_id){
  final String sql = "select count(hw_name) as count from homework where hw_name = ? and hw_cs_id = ?"; 
  final int count = this.jdbcTemplate.queryForObject(sql, Integer.class,hw_name,hw_cs_id);
  return count; //這堂課中有沒有這筆homework

}

public int queryStudentInTheClass(final String cs_id, final String std_id){
  final String sql = "select count(cs_id) as count from class_student where cs_id = ? and std_id = ?";
  final int count = this.jdbcTemplate.queryForObject(sql, Integer.class,cs_id,std_id);
  return count; //比對學生有沒有在這堂課中，get時判斷
}

public int queryTeacherInTheClass(final String cs_id, final String teacher_id){
  final String sql = "select count(cs_id) as count from class_teacher where cs_id = ? and teacher_id = ?";
  final int count = this.jdbcTemplate.queryForObject(sql, Integer.class, cs_id,teacher_id);
  return count; //比對教師有沒有在這個課堂中
}



 public int insertAcceptance(final Acceptance acceptance) {
  CurrentTimeStamp ts = new CurrentTimeStamp();
  String timestamp = ts.getCurrentTimeStamp();
  return jdbcTemplate.update(
    "insert into acceptance (accept_std_id, accept_hw_id, accept_state, accept_time, accept_done) values(?, ?, ?, ?, ?)",
    acceptance.getStd_id(), acceptance.getAccept_hw_id(), acceptance.getAccept_state(), timestamp, 0);
 }

public int insertHomework(final Acceptance acceptance){
  CurrentTimeStamp ts = new CurrentTimeStamp();
  String timestamp = ts.getCurrentTimeStamp();
  return jdbcTemplate.update(
    "insert into homework (hw_name, hw_content, hw_cs_id, hw_createtime) values(?, ?, ?, ?)",
    acceptance.getHw_name(), acceptance.getHw_content(), acceptance.getHw_cs_id(), timestamp);
}

 public List<Acceptance> findCourseHomework(String hw_cs_id) {
    return this.jdbcTemplate.query("select hw_name, hw_createtime, hw_content from homework where hw_cs_id = ?"
       ,new Object[]{hw_cs_id}, new HomeWorkMapper());
  }

 public List<Acceptance> findHomeworkDetail(String cs_id, String hw_name) {  
     return this.jdbcTemplate.query( "select distinct a.accept_id, a.accept_std_id, s.std_name, a.accept_hw_id, a.accept_state, a.accept_time, a.accept_score, a.accept_done, hw.hw_name, hw.hw_content from acceptance a join homework hw on hw.hw_id = a.accept_hw_id join student s on a.accept_std_id = s.std_id where hw.hw_cs_id = ? and hw.hw_name = ?"
       , new Object[]{cs_id,hw_name}, new AcceptanceMapper());
 }


 public List<Acceptance> findCourseHomeworkformTeacher(String hw_cs_id){
  return this.jdbcTemplate.query( "select hw_name, hw_createtime, hw_content, hw_id, hw_closed from homework where hw_cs_id = ?"
  ,new Object[]{hw_cs_id}, new HomeWorkMapper2());
 }

 public List<Acceptance> findHomeworkDetailformTeacher(String cs_id, String hw_name) {  
      return this.jdbcTemplate.query( "select distinct a.accept_id, a.accept_std_id, s.std_name, a.accept_hw_id, a.accept_state, a.accept_time, a.accept_score, a.accept_content, a.accept_tag, a.accept_done, hw.hw_name, hw.hw_content from acceptance a join homework hw on hw.hw_id = a.accept_hw_id join student s on a.accept_std_id = s.std_id where hw.hw_cs_id = ? and hw.hw_name = ?"
        , new Object[]{cs_id,hw_name}, new AcceptanceMapper2());
}

public Acceptance getRejectAcceptance(String cs_id, String hw_name, int std_id){
      return this.jdbcTemplate.queryForObject( "select act.accept_content, act.accept_tag, act.accept_score from homework hw join acceptance act on act.accept_hw_id = hw.hw_id where hw.hw_cs_id = ? and hw.hw_name = ? and act.accept_std_id = ?"
            , new Object[]{cs_id,hw_name,std_id}, new RejectAcceptanceMapper());
}

 private static class AcceptanceMapper implements RowMapper<Acceptance> {

     public Acceptance mapRow(ResultSet rs, int rowNum) throws SQLException {
       Acceptance acceptance = new Acceptance();
      acceptance.setAccept_id(rs.getInt("accept_id"));
      acceptance.setStd_id(rs.getInt("accept_std_id"));
      acceptance.setStd_name(rs.getString("std_name"));
      acceptance.setAccept_hw_id(rs.getInt("accept_hw_id"));
      acceptance.setAccept_state(rs.getInt("accept_state"));
      acceptance.setAccept_time(rs.getTime("accept_time"));
      acceptance.setAccept_score(rs.getInt("accept_score"));
      acceptance.setAccept_done(rs.getBoolean("accept_done"));
      acceptance.setHw_name(rs.getString("hw_name"));
      acceptance.setHw_content(rs.getString("hw_content"));
         return acceptance;
     }
 }

 private static class AcceptanceMapper2 implements RowMapper<Acceptance> {

  public Acceptance mapRow(ResultSet rs, int rowNum) throws SQLException {
    Acceptance acceptance = new Acceptance();
   acceptance.setAccept_id(rs.getInt("accept_id"));
   acceptance.setStd_id(rs.getInt("accept_std_id"));
   acceptance.setStd_name(rs.getString("std_name"));
   acceptance.setAccept_hw_id(rs.getInt("accept_hw_id"));
   acceptance.setAccept_state(rs.getInt("accept_state"));
   acceptance.setAccept_time(rs.getTime("accept_time"));
   acceptance.setAccept_score(rs.getInt("accept_score"));
   acceptance.setAccept_content(rs.getString("accept_content"));
   acceptance.setAccept_tag(rs.getInt("accept_tag"));
   acceptance.setAccept_done(rs.getBoolean("accept_done"));
   acceptance.setHw_name(rs.getString("hw_name"));
   acceptance.setHw_content(rs.getString("hw_content"));
      return acceptance;
  }
}

private static final class HomeWorkMapper implements RowMapper<Acceptance>{

  public Acceptance mapRow(final ResultSet rs, final int rowNum) throws SQLException {
     Acceptance acceptance = new Acceptance();
    acceptance.setHw_name(rs.getString("hw_name"));
    acceptance.setHw_createtime (rs.getString("hw_createtime"));
    acceptance.setHw_content(rs.getString("hw_content"));
      return acceptance;
  }
}


private static class HomeWorkMapper3 implements RowMapper<Acceptance>{

  public Acceptance mapRow(ResultSet rs, int rowNum) throws SQLException {
     Acceptance acceptance = new Acceptance();
    acceptance.setStd_id(rs.getInt("std_id"));
      return acceptance;
  }
}


private static class RejectAcceptanceMapper implements RowMapper<Acceptance>{

  public Acceptance mapRow(ResultSet rs, int rowNum) throws SQLException {
     Acceptance acceptance = new Acceptance();
     acceptance.setAccept_content(rs.getString("accept_content"));
     acceptance.setAccept_tag(rs.getInt("accept_tag"));
     acceptance.setAccept_score(rs.getInt("accept_score"));
      return acceptance;
  }
}


private static class HomeWorkMapper2 implements RowMapper<Acceptance>{

  public Acceptance mapRow(ResultSet rs, int rowNum) throws SQLException {
     Acceptance acceptance = new Acceptance();
    acceptance.setHw_name(rs.getString("hw_name"));
    acceptance.setHw_createtime (rs.getString("hw_createtime"));
    acceptance.setHw_content(rs.getString("hw_content"));
    acceptance.setHw_id(rs.getInt("hw_id"));
    acceptance.setHw_closed(rs.getInt("hw_closed"));
      return acceptance;
  }
}


public int rejectAcceptance(Acceptance acceptance){
  return jdbcTemplate.update(
      "update acceptance set accept_state = 2, accept_score = ?, accept_content = ?, accept_tag = ? where accept_std_id = ? and accept_hw_id = ?",
      acceptance.getAccept_score(), acceptance.getAccept_content(), acceptance.getAccept_tag(), acceptance.getStd_id(),acceptance.getAccept_hw_id());
}

public int updateFromRejectStateToAcceptdone(int std_id, int hw_id){
  return jdbcTemplate.update(
      "update acceptance set accept_done = 1 where accept_std_id = ? and accept_hw_id = ?",
      std_id, hw_id);
}

public int updateAcceptanceLine(Acceptance acceptance){
  CurrentTimeStamp ts = new CurrentTimeStamp();
  String timestamp = ts.getCurrentTimeStamp();
    return jdbcTemplate.update(
      "update acceptance set accept_state = ?, accept_time = ?, accept_score = ?, accept_content = ?, accept_tag = ?, accept_done = 0 where accept_std_id = ? and accept_hw_id = ?",
      acceptance.getAccept_state(), timestamp, acceptance.getAccept_score(), acceptance.getAccept_content(), acceptance.getAccept_tag(), acceptance.getStd_id(),acceptance.getAccept_hw_id());
}


 public int updateScore(Acceptance acceptance) {
    return jdbcTemplate.update(
      "update acceptance set accept_score = ?, accept_content = ?, accept_tag = ?, accept_done = 1 where accept_std_id = ? and accept_hw_id = ?",
      acceptance.getAccept_score(), acceptance.getAccept_content(), acceptance.getAccept_tag(), acceptance.getStd_id(),acceptance.getAccept_hw_id());
 }
 

 public int updateTag(Acceptance acceptance){
    return jdbcTemplate.update(
      "update acceptance set accept_tag = ? where accept_std_id = ? and accept_hw_id = ?",
      acceptance.getAccept_tag(), acceptance.getStd_id(),acceptance.getAccept_hw_id());
 }


 public int updateContent(Acceptance acceptance){
    return jdbcTemplate.update(
      "update homework set hw_name = ?, hw_content = ? where hw_id = ?",
      acceptance.getHw_name(), acceptance.getHw_content(), acceptance.getHw_id());
    
 }

 public int updateClosedHomework(Acceptance acceptance){
    return jdbcTemplate.update(
      "update homework set hw_closed = 1 where hw_id = ?",
      acceptance.getHw_id());
 }

 public int updateReopenHomework(Acceptance acceptance){
    return jdbcTemplate.update(
      "update homework set hw_closed = 0 where hw_id = ?",
      acceptance.getHw_id());
  }

 public int deleteAcceptance(Acceptance acceptance){
    return jdbcTemplate.update(
      "delete from acceptance where accept_std_id =? and accept_hw_id =?", acceptance.getStd_id(), acceptance.getAccept_hw_id());
 }


 public int deleteHomework(Acceptance acceptance){
   return jdbcTemplate.update("delete from homework where hw_name =? and hw_cs_id =?", acceptance.getHw_name(), acceptance.getHw_cs_id());
 }

 
 
 

 
 
}
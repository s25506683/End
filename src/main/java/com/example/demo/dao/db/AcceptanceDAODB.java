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

@Repository
public class AcceptanceDAODB implements AcceptanceDAO {

 @Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;

//jdbcTemplate 


public int queryStudentInTheAcceptance(final int accept_std_id, final int accept_hw_id){
  final String sql = "select count(accept_std_id) as count from acceptance where accept_std_id = ? and accept_hw_id = ? ";
  final int count = this.jdbcTemplate.queryForObject(sql, Integer.class,accept_std_id,accept_hw_id);
  return count; //看這筆驗收中有沒有這個學生
}

public int queryHomeworkInTheClass(final String hw_name, final String hw_cs_id){
  final String sql = "select count(hw_name) as count from homework where hw_name = ? and hw_cs_id = ?"; 
  final int count = this.jdbcTemplate.queryForObject(sql, Integer.class,hw_name,hw_cs_id);
  return count; //這堂課中有沒有這筆homework

}

 public int insertAcceptance(final Acceptance acceptance) {
    return jdbcTemplate.update(
      "insert into acceptance (accept_std_id, accept_hw_id) values(?, ?)",
      acceptance.getAccept_std_id(), acceptance.getAccept_hw_id());
 }

public int insertHomework(final Acceptance acceptance){
  return jdbcTemplate.update(
    "insert into homework (hw_name, hw_content, hw_cs_id) values(?, ?, ?)",
    acceptance.getHw_name(), acceptance.getHw_content(), acceptance.getHw_cs_id());
}


 public List<Acceptance> findCourseHomework(final String hw_cs_id) {
    return this.jdbcTemplate.query( "select hw.hw_name, hw.hw_createtime,a.accept_score, a.accept_done from acceptance as a inner join homework as hw on hw.hw_id = a.accept_hw_id where hw.hw_cs_id = ?"
       ,new Object[]{hw_cs_id}, new HomeWorkMapper());
  }

 public List<Acceptance> findHomeworkDetail(final String cs_id, final String hw_name) {  
     return this.jdbcTemplate.query( "select a.accept_id, a.accept_std_id, a.accept_hw_id, a.accept_time, a.accept_score, a.accept_done, hw.hw_name from acceptance a inner join homework hw on hw.hw_id = a.accept_hw_id where hw.hw_cs_id = ? and hw.hw_name = ?"
       , new Object[]{cs_id,hw_name}, new AcceptanceMapper());
 }

 private static final class AcceptanceMapper implements RowMapper<Acceptance> {

     public Acceptance mapRow(final ResultSet rs, final int rowNum) throws SQLException {
      final Acceptance acceptance = new Acceptance();
      acceptance.setAccept_id(rs.getInt("accept_id"));
      acceptance.setAccept_std_id(rs.getInt("accept_std_id"));
      acceptance.setAccept_hw_id(rs.getInt("accept_hw_id"));
      acceptance.setAccept_time(rs.getTime("accept_time"));
      acceptance.setAccept_score(rs.getInt("accept_score"));
      acceptance.setAccept_done(rs.getBoolean("accept_done"));
      acceptance.setHw_name(rs.getString("hw_name"));
         return acceptance;
     }
 }

private static final class HomeWorkMapper implements RowMapper<Acceptance>{

  public Acceptance mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    final Acceptance acceptance = new Acceptance();
    acceptance.setHw_name(rs.getString("hw_name"));
    acceptance.setHw_createtime (rs.getDate("hw_createtime"));
    acceptance.setAccept_score(rs.getInt("accept_score"));
    acceptance.setAccept_done(rs.getBoolean("accept_done"));
      return acceptance;
  }


}



 public int updateScore(final Acceptance acceptance) {
    return jdbcTemplate.update(
      "update acceptance set accept_std_id=?, accept_hw_id=?, accept_score=?, accept_done=1 where accept_std_id =? and accept_hw_id = ?",
      acceptance.getAccept_std_id(), acceptance.getAccept_hw_id(), acceptance.getAccept_score(), acceptance.getAccept_std_id(),acceptance.getAccept_hw_id());
 }


 public int updateContent(final Acceptance acceptance){
    return jdbcTemplate.update(
      "update homework set hw_name=?, hw_content=?, hw_cs_id=? where hw_name=? and hw_cs_id=?",
      acceptance.getHw_name(),acceptance.getHw_content(),acceptance.getHw_cs_id(),acceptance.getHw_name(),acceptance.getHw_cs_id());
    
 }


 public int delete(final int accept_std_id, int accept_hw_id) {
    return jdbcTemplate.update(
      "delete from acceptance where accept_std_id =? and accept_hw_id =?", accept_std_id, accept_hw_id);
 }


 
 
 

 
 
}
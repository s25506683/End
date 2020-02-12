package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Acceptance;

@Repository
public class AcceptanceDAODB implements AcceptanceDAO {

 @Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;

//jdbcTemplate 

 public int insert(Acceptance acceptance) {
    return jdbcTemplate.update(
      "insert into acceptance (accept_std_id, accept_hw_id, accept_score, accept_done) values(?, ?, ?, ?)",
      acceptance.getAccept_std_id(), acceptance.getAccept_hw_id(), acceptance.getAccept_score(), acceptance.isAccept_done());
 }

 public Acceptance findOne(int id) {
    return this.jdbcTemplate.queryForObject( "select accept_id, accept_std_id, accept_hw_id, accept_time, accept_score, accept_done from acceptance where accept_id = ?", new Object[]{id}, new AcceptanceMapper());
  }

 public List<Acceptance> findAll(String hw) {
     return this.jdbcTemplate.query( "select a.accept_id, a.accept_std_id, a.accept_hw_id, a.accept_time, a.accept_score, a.accept_done, hw.hw_name from acceptance a inner join homework hw on hw.hw_id = a.accept_hw_id where hw.hw_name = ?"
       , new Object[]{hw}, new AcceptanceMapper());
 }

 private static final class AcceptanceMapper implements RowMapper<Acceptance> {

     public Acceptance mapRow(ResultSet rs, int rowNum) throws SQLException {
      Acceptance acceptance = new Acceptance();
      acceptance.setAccept_id(rs.getInt("accept_id"));
      acceptance.setAccept_std_id(rs.getInt("accept_std_id"));
      acceptance.setAccept_hw_id(rs.getInt("accept_hw_id"));
      acceptance.setAccept_time(rs.getTime("accept_time"));
      acceptance.setAccept_score(rs.getInt("accept_score"));
      acceptance.setAccept_done(rs.getBoolean("accept_done"));
         return acceptance;
     }
 }
 public int update(Acceptance acceptance) {
    return jdbcTemplate.update(
      "update acceptance set accept_std_id=?, accept_hw_id=?, accept_score=?, accept_done=? where accept_std_id =?",
      acceptance.getAccept_std_id(), acceptance.getAccept_hw_id(), acceptance.getAccept_score(), acceptance.isAccept_done(), acceptance.getAccept_std_id());
 }

 public int delete(int std_id) {
    return jdbcTemplate.update(
      "delete from acceptance where accept_std_id =?", std_id);
 }
 
 
 
}






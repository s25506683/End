package com.example.demo.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
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


public int queryUser(final int accept_std_id, final int accept_hw_id){
  final String sql = "select count(accept_std_id) as count from acceptance where accept_std_id = ? and accept_hw_id = ? ";
  final int count = this.jdbcTemplate.queryForObject(sql, Integer.class,accept_std_id,accept_hw_id);
  return count;
}

 public int insert(final Acceptance acceptance) {
    return jdbcTemplate.update(
      "insert into acceptance (accept_std_id, accept_hw_id) values(?, ?)",
      acceptance.getAccept_std_id(), acceptance.getAccept_hw_id());
 }

 public Acceptance findOne(final int accept_std_id) {
    return this.jdbcTemplate.queryForObject( "select * from acceptance where accept_std_id = ?", new Object[]{accept_std_id}, new AcceptanceMapper());
  }

 public List<Acceptance> findAll(final String cs_id, final String hw_name) {
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
         return acceptance;
     }
 }
 public int update(final Acceptance acceptance) {
    return jdbcTemplate.update(
      "update acceptance set accept_std_id=?, accept_hw_id=?, accept_score=?, accept_done=? where accept_std_id =?",
      acceptance.getAccept_std_id(), acceptance.getAccept_hw_id(), acceptance.getAccept_score(), acceptance.isAccept_done(), acceptance.getAccept_std_id());
 }

 public int delete(final int std_id) {
    return jdbcTemplate.update(
      "delete from acceptance where accept_std_id =?", std_id);
 }
 
 
 

 
 
}
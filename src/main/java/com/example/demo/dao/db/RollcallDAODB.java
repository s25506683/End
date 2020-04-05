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

import com.example.demo.dao.RollcallDAO;
import com.example.demo.entity.Rollcall;

@Repository
public class RollcallDAODB implements RollcallDAO {

 @Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;

//jdbcTemplate 

  public int addRollcall(final Rollcall rollcall) {
     return jdbcTemplate.update(
       "insert into rollcall (cs_id, rc_name, rc_endtime, rc_inputsource, rc_scoring, qrcode) values(?, ?, ?, ?, ?, ?)",
       rollcall.getCs_id(), rollcall.getRc_name(), rollcall.getRc_endtime(), rollcall.getRc_inputsource(), rollcall.getRc_scoring(), rollcall.getQrcode());
  }

  public int hasTheSameRollcallName(String rc_name){
    String sql = "select count(rc_name) as count from rollcall where rc_name = ?";
    int count = this.jdbcTemplate.queryForObject(sql,Integer.class,rc_name);
    return count;
  }

  public int hasThisRollcallId(int rc_id){
    String sql = "select count(rc_id) as count from rollcall where rc_id = ?";
    int count = this.jdbcTemplate.queryForObject(sql,Integer.class,rc_id);
    return count;
  }

  public String[] findClassStudent(String cs_id){
    String sql = "select group_concat( std_id SEPARATOR  ',' ) as inClassStudent from class_student where cs_id = ?";
    String studentInfo = this.jdbcTemplate.queryForObject(sql, String.class, cs_id);
    //convert string to array(String).
    String[] studentInfoarr = studentInfo.split(",");
    return studentInfoarr;
  }

  public int addRollcallRecord(String rc_name, int std_id){
    return jdbcTemplate.update(
       "insert into rc_record (rc_id, std_id, tl_type_id) values((select rc_id from rollcall where rc_name = ?), ?, 0)",
       rc_name, std_id);
  }

  public List<Rollcall> findOneRollcallRecord(final int rc_id) {
    return this.jdbcTemplate.query( "select rcre.std_id, s.std_name, s.std_department, rcre.record_time, tltype.tl_type_name from rc_record rcre inner join student s on s.std_id = rcre.std_id inner join takeleave_type tltype on tltype.tl_type_id = rcre.tl_type_id where rcre.rc_id = ? order by rcre.std_id"
    , new Object[]{rc_id}, new RollcallMapper());
  }

  public List<Rollcall> findAllRollcallRecord(final String cs_id) {
     return this.jdbcTemplate.query( "select rc.rc_id, rc.rc_starttime, sum(case when rcre.tl_type_id = 1 then 1 else 0 end) as present, sum(case when rcre.tl_type_id = 0 then 1 else 0 end) as absent, sum(case when rcre.tl_type_id >= 2 then 1 else 0 end) as otherwise, rc.rc_scoring, rc.rc_inputsource from rollcall as rc inner join rc_record as rcre on rc.rc_id = rcre.rc_id where rc.cs_id = ? group by rc.rc_id"
     , new Object[]{cs_id}, new RollcallMapper2());
  }

 public List<Rollcall> findClassStudentList(final String cs_id){
     return this.jdbcTemplate.query("select s.std_id, s.std_name, s.std_department from student s inner join class_student cs on cs.std_id = s.std_id where cs.cs_id = ?"
     , new Object[]{cs_id}, new RollcallMapper3());
  }

  private static final class RollcallMapper implements RowMapper<Rollcall> {
     public Rollcall mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Rollcall rollcall = new Rollcall();
         rollcall.setStd_id(rs.getInt("std_id"));
         rollcall.setStd_name(rs.getString("std_name"));
         rollcall.setStd_department(rs.getString("std_department"));
         rollcall.setRecord_time(rs.getString("record_time"));
         rollcall.setTl_type_name(rs.getString("tl_type_name"));
         return rollcall;
     }
  }

 private static final class RollcallMapper2 implements RowMapper<Rollcall> {
    public Rollcall mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Rollcall rollcall2 = new Rollcall();
        rollcall2.setRc_id(rs.getInt("rc_id"));
        rollcall2.setRc_starttime(rs.getString("rc_starttime"));
        rollcall2.setPresent(rs.getInt("present"));
        rollcall2.setAbsent(rs.getInt("absent"));
        rollcall2.setOtherwise(rs.getInt("otherwise"));
        rollcall2.setRc_scoring(rs.getInt("rc_scoring"));
        rollcall2.setRc_inputsource(rs.getString("rc_inputsource"));
        return rollcall2;
    }
}

  private static final class RollcallMapper3 implements RowMapper<Rollcall> {
    public Rollcall mapRow(final ResultSet rs, final int rowNum) throws SQLException {
       final Rollcall rollcall = new Rollcall();
        rollcall.setStd_id(rs.getInt("std_id"));
        rollcall.setStd_name(rs.getString("std_name"));
        rollcall.setStd_department(rs.getString("std_department"));
        return rollcall;
    }
  }

//  public int update(Rollcall rollcall) {
//     return jdbcTemplate.update(
//       "update question set q_std_id=?, q_content=?, cs_id=? where q_std_id =?",
//       homepage1_s.getQ_std_id(), homepage1_s.getQ_content(), homepage1_s.getCs_id(), homepage1_s.getQ_std_id());
//  }

public int deleteRollcall(String rc_name) {
    return jdbcTemplate.update(
      "delete from rollcall where rc_name =?", rc_name);
 }

 
 
}



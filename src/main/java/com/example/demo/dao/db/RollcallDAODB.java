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
import com.example.demo.util.CurrentTimeStamp;

@Repository
public class RollcallDAODB implements RollcallDAO {

 @Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;

//jdbcTemplate 

  public int addRollcall(final Rollcall rollcall) {
     return jdbcTemplate.update(
       "insert into rollcall (cs_id, rc_starttime, rc_inputsource, qrcode, longitude, latitude) values(?, ?, ?, ?, ?, ?)",
       rollcall.getCs_id(), rollcall.getRc_starttime(), rollcall.getRc_inputsource(), rollcall.getQrcode(), rollcall.getLongitude(), rollcall.getLatitude());
  }

  public int findRcId(String cs_id, String rc_starttime){
    String sql = "select rc_id from rollcall where cs_id = ? and rc_starttime = ?";
    int rc_id = this.jdbcTemplate.queryForObject(sql,Integer.class,cs_id, rc_starttime);
    return rc_id;
  }

  public String findCs_id(int rc_id){
    String sql = "select cs_id from rollcall where rc_id = ?";
    String cs_id = this.jdbcTemplate.queryForObject(sql,String.class,rc_id);
    return cs_id;
  }

  public String findTlTypeName(int tl_type_id){
    String sql = "select tl_type_name from takeleave_type where tl_type_id = ?";
    String tl_type_name = this.jdbcTemplate.queryForObject(sql,String.class,tl_type_id);
    return tl_type_name;
  }

  public int findRcIdWithQRcode2(String qrcode){
    String sql = "select rc_id from rollcall where qrcode = ?";
    int rc_id = this.jdbcTemplate.queryForObject(sql,Integer.class,qrcode);
    return rc_id; 
  }

  public int hasThisQRcode(String qrcode){
    String sql = "select count(qrcode) as count from rollcall where qrcode = ?";
    int count = this.jdbcTemplate.queryForObject(sql,Integer.class,qrcode);
    return count;
  }

  public int hasThisRollcallId(int rc_id){
    String sql = "select count(rc_id) as count from rollcall where rc_id = ?";
    int count = this.jdbcTemplate.queryForObject(sql,Integer.class,rc_id);
    return count;
  }

  public int hasThisCsId(String cs_id){
    String sql = "select count(cs_id) as count from class where cs_id = ?";
    int count = this.jdbcTemplate.queryForObject(sql,Integer.class,cs_id);
    return count;
  }

  public int hasThisRcRecord(int std_id, int rc_id){
    String sql = "select count(rc_id) as count from rc_record where std_id = ? and rc_id = ?";
    int count = this.jdbcTemplate.queryForObject(sql,Integer.class,std_id, rc_id);
    return count;
  }

  public String[] findClassStudent(String cs_id){
    String sql = "select group_concat( std_id SEPARATOR  ',' ) as inClassStudent from class_student where cs_id = ?";
    String studentInfo = this.jdbcTemplate.queryForObject(sql, String.class, cs_id);
    //convert string to array(String).
    String[] studentInfoarr = studentInfo.split(",");
    return studentInfoarr;
  }

  public int addRollcallRecord(int rc_id, int std_id){
    return jdbcTemplate.update(
       "insert into rc_record (rc_id, std_id, tl_type_id) values(?, ?, 0)",
       rc_id, std_id);
  }

  public Rollcall findOneRollcallSummaryRecord(int rc_id){
    return this.jdbcTemplate.queryForObject( "select sum(tl_type_id = 1 or tl_type_id = 3) as present, sum(tl_type_id = 2) as long_distance, sum(tl_type_id > 3 and tl_type_id < 8) as takeleave, sum(tl_type_id = 0 || tl_type_id = 8) as otherwise from rc_record where rc_id = ?"
    , new Object[]{rc_id}, new RollcallMapper7());
  }

  public List<Rollcall> findOneRollcallRecord(final int rc_id) {
    return this.jdbcTemplate.query( "select rcre.std_id, s.std_name, s.std_department, rcre.record_time, tltype.tl_type_name, rcre.tl_type_id from rc_record rcre inner join student s on s.std_id = rcre.std_id inner join takeleave_type tltype on tltype.tl_type_id = rcre.tl_type_id where rcre.rc_id = ? order by rcre.std_id"
    , new Object[]{rc_id}, new RollcallMapper());
  }

  public List<Rollcall> findAllRollcallRecord(final String cs_id) {
     return this.jdbcTemplate.query( "select rc.rc_id, rc.rc_starttime, sum(case when rcre.tl_type_id <= 3 and rcre.tl_type_id > 0 then 1 else 0 end) as present, sum(case when rcre.tl_type_id = 0 or rcre.tl_type_id = 8 then 1 else 0 end) as absent, sum(case when rcre.tl_type_id > 3 and rcre.tl_type_id < 8 then 1 else 0 end) as otherwise, rc.rc_inputsource from rollcall as rc inner join rc_record as rcre on rc.rc_id = rcre.rc_id where rc.cs_id = ? group by rc.rc_id order by rc.rc_starttime desc"
     , new Object[]{cs_id}, new RollcallMapper2());
  }

  public Rollcall findNewlyGPSRollcallRecord(String cs_id){
    return this.jdbcTemplate.queryForObject( "select rc_id, rc_starttime, rc_inputsource, rc_end from rollcall where cs_id = ? and rc_inputsource = 'GPS點名' order by rc_starttime desc limit 1"
     , new Object[]{ cs_id }, new RollcallMapper8());
  }

  public List<Rollcall> findStudentOwnRollcallInClass(int std_id, String cs_id){
     return this.jdbcTemplate.query("select rcre.std_id, rc.rc_id, rcre.record_id, rc.rc_starttime, rcre.record_time, rc.rc_inputsource, tlty.tl_type_id, tlty.tl_type_name from rc_record rcre inner join rollcall rc on rc.rc_id = rcre.rc_id inner join takeleave_type tlty on tlty.tl_type_id = rcre.tl_type_id where rcre.std_id = ? and rc.cs_id = ? order by rc.rc_starttime desc"
      , new Object[]{std_id, cs_id}, new RollcallMapper4());
  }

  public List<Rollcall> findRollcallByPerson(String cs_id){
    return this.jdbcTemplate.query("select rcre.std_id, st.std_name, st.std_department, sum(case when rcre.tl_type_id <= 3 and rcre.tl_type_id > 0 then 1 else 0 end) as present, sum(case when rcre.tl_type_id = 0 then 1 else 0 end) as absent, sum(case when rcre.tl_type_id >= 2 then 1 else 0 end) as otherwise from rc_record as rcre inner join rollcall as rc on rc.rc_id = rcre.rc_id inner join student as st on st.std_id = rcre.std_id where rc.cs_id = ? group by rcre.std_id order by rcre.std_id"
      , new Object[]{cs_id}, new RollcallMapper5());
  }

  public List<Rollcall> findRcIdWithQRcode(String qrcode){
    return this.jdbcTemplate.query( "select rc_id from rollcall where qrcode = ?"
    , new Object[]{qrcode}, new RollcallMapper6());
  }


  private static final class RollcallMapper implements RowMapper<Rollcall> {
     public Rollcall mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Rollcall rollcall = new Rollcall();
         rollcall.setStd_id(rs.getInt("std_id"));
         rollcall.setStd_name(rs.getString("std_name"));
         rollcall.setStd_department(rs.getString("std_department"));
         rollcall.setRecord_time(rs.getString("record_time"));
         rollcall.setTl_type_name(rs.getString("tl_type_name"));
         rollcall.setTl_type_id(rs.getInt("tl_type_id"));
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
        rollcall2.setRc_inputsource(rs.getString("rc_inputsource"));
        return rollcall2;
    }
}

  private static final class RollcallMapper3 implements RowMapper<Rollcall> {
    public Rollcall mapRow(final ResultSet rs, final int rowNum) throws SQLException {
       final Rollcall rollcall3 = new Rollcall();
        rollcall3.setStd_id(rs.getInt("std_id"));
        rollcall3.setStd_name(rs.getString("std_name"));
        rollcall3.setStd_department(rs.getString("std_department"));
        return rollcall3;
    }
  }

  private static final class RollcallMapper4 implements RowMapper<Rollcall> {
   public Rollcall mapRow(final ResultSet rs, final int rowNum) throws SQLException {
      final Rollcall rollcall4 = new Rollcall();
        rollcall4.setStd_id(rs.getInt("std_id"));
        rollcall4.setRc_id(rs.getInt("rc_id"));
        rollcall4.setRecord_id(rs.getInt("record_id"));
        rollcall4.setRc_starttime(rs.getString("rc_starttime"));
        rollcall4.setRecord_time(rs.getString("record_time"));
        rollcall4.setRc_inputsource(rs.getString("rc_inputsource"));
        rollcall4.setTl_type_id(rs.getInt("tl_type_id"));
        rollcall4.setTl_type_name(rs.getString("tl_type_name"));
        return rollcall4;
    }
  }
  
  private static final class RollcallMapper5 implements RowMapper<Rollcall> {
    public Rollcall mapRow(final ResultSet rs, final int rowNum) throws SQLException {
       final Rollcall rollcall5 = new Rollcall();
         rollcall5.setStd_id(rs.getInt("std_id"));
         rollcall5.setStd_name(rs.getString("std_name"));
         rollcall5.setStd_department(rs.getString("std_department"));
         rollcall5.setPresent(rs.getInt("present"));
         rollcall5.setAbsent(rs.getInt("absent"));
         rollcall5.setOtherwise(rs.getInt("otherwise"));
         return rollcall5;
     }
   }

   private static final class RollcallMapper6 implements RowMapper<Rollcall> {
    public Rollcall mapRow(final ResultSet rs, final int rowNum) throws SQLException {
       final Rollcall rollcall6 = new Rollcall();
        rollcall6.setRc_id(rs.getInt("rc_id"));
        return rollcall6;
    }
  }

  private static final class RollcallMapper7 implements RowMapper<Rollcall> {
    public Rollcall mapRow(final ResultSet rs, final int rowNum) throws SQLException {
       final Rollcall rollcall7 = new Rollcall();
         rollcall7.setPresent(rs.getInt("present"));
         rollcall7.setLong_distance(rs.getInt("long_distance"));
         rollcall7.setTakeleave(rs.getInt("takeleave"));
         rollcall7.setOtherwise(rs.getInt("otherwise"));
         return rollcall7;
     }
   }

   private static final class RollcallMapper8 implements RowMapper<Rollcall> {
    public Rollcall mapRow(final ResultSet rs, final int rowNum) throws SQLException {
       final Rollcall rollcall8 = new Rollcall();
         rollcall8.setRc_id(rs.getInt("rc_id"));
         rollcall8.setRc_starttime(rs.getString("rc_starttime"));
         rollcall8.setRc_inputsource(rs.getString("rc_inputsource"));
         rollcall8.setRc_end(rs.getInt("rc_end"));
         return rollcall8;
     }
   }

   


  public String findQRcodeInRollcallName(int rc_id){
    String sql = "select qrcode from rollcall where rc_id = ?";
    String qrcode = this.jdbcTemplate.queryForObject(sql,String.class,rc_id);
    return qrcode;
  }

  public String rollcallByHand(int rc_id){
    String sql = "select rc_inputsource from rollcall where rc_id = ?";
    String rc_inputsource = this.jdbcTemplate.queryForObject(sql,String.class,rc_id);
    return rc_inputsource;
  }

  public int rollcallIsEnd(int rc_id){
    String sql = "select rc_end from rollcall where rc_id = ?";
    int rc_end = this.jdbcTemplate.queryForObject(sql,Integer.class,rc_id);
    return rc_end;
  }

 public int updateRollcallRecord(int std_id, int rc_id){
    return jdbcTemplate.update(
      "update rc_record set tl_type_id = 1 where std_id = ? and rc_id = ?",
      std_id, rc_id);
 }

 public int updateQRcode(int rc_id, String qrcode){
  return jdbcTemplate.update(
    "update rollcall set qrcode = ? where rc_id = ?",
    qrcode, rc_id);
 }

 public int updateRollcall(int rc_id, int std_id, int tl_type_id){
  return jdbcTemplate.update(
    "update rc_record set tl_type_id = ? where rc_id = ? and std_id = ?",
    tl_type_id, rc_id, std_id);
 }

 public int closedRollcall(int rc_id){
  return jdbcTemplate.update(
    "update rollcall set rc_end = 1 where rc_id = ?",
    rc_id);
 }

public int deleteRollcall(int rc_id) {
    return jdbcTemplate.update(
      "delete from rollcall where rc_id =?", rc_id);
 }

 
 
}



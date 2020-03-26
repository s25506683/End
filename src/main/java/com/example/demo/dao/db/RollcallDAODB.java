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

  public int addrollcall(final Rollcall rollcall) {
     return jdbcTemplate.update(
       "insert into rollcall (cs_id, rc_endtime, rc_inputsource, rc_scoring) values(?, ?, ?, ?)",
       rollcall.getCs_id(), rollcall.getRc_endtime(), rollcall.getRc_inputsoure(), rollcall.getRc_scoring());
  }

 public List<Rollcall> findOneRollcallRecord(final String rc_id) {
    return this.jdbcTemplate.query( "select rcre.std_id, s.std_name, s.std_department, tltype.tl_type_name from rc_record rcre inner join student s on s.std_id = rcre.std_id inner join takeleave_type tltype on tltype.tl_type_id = rcre.tl_type_id where rcre.rc_id = ? order by rcre.std_id", new Object[]{rc_id}, new RollcallMapper());
  }

 public List<Rollcall> findAllRollcallRecord(final String cs_id) {
     return this.jdbcTemplate.query( "select rc.rc_starttime, (select count(tl_type_id) from rc_record rcre where rcre.rc_id = 1 and rcre.tl_type_id = 1) as present, (select count(tl_type_id) from rc_record rcre where rcre.rc_id = 1 and rcre.tl_type_id = 0) as late, (select count(tl_type_id) from rc_record rcre where rcre.rc_id = 1 and rcre.tl_type_id = 2) as absent, rc.rc_scoring, rc.rc_inputsource from rollcall rc inner join rc_record rcre on rc.rc_id = rcre.rc_id where rc.cs_id = ? group by rc.rc_id"
     , new Object[]{cs_id}, new RollcallMapper2());
 }

 public List<Rollcall> findClassList(final String cs_id){
     return this.jdbcTemplate.query("select s.std_id, s.std_name, s.std_department from student s inner join class_student cs on cs.std_id = s.std_id where cs.cs_id = ?"
     , new Object[]{cs_id}, new RollcallMapper3());
    }

 private static final class RollcallMapper implements RowMapper<Rollcall> {
     public Rollcall mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Rollcall rollcall = new Rollcall();
         rollcall.setStd_id(rs.getInt("std_id"));
         rollcall.setStd_name(rs.getString("std_name"));
         rollcall.setStd_department(rs.getString("std_department"));
         rollcall.setTl_type_name(rs.getString("tl_type_name"));
         return rollcall;
     }
 }

 private static final class RollcallMapper2 implements RowMapper<Rollcall> {
    public Rollcall mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Rollcall rollcall2 = new Rollcall();
        rollcall2.setStd_id(rs.getInt("std_id"));
        rollcall2.setStd_name(rs.getString("std_name"));
        rollcall2.setStd_department(rs.getString("std_department"));
        rollcall2.setTl_type_name(rs.getString("tl_type_name"));
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

//  public int delete(int id) {
//     return jdbcTemplate.update(
//       "delete from question where q_std_id =?", id);
//  }

 
 
}



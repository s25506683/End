package com.example.demo.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.TakeleaveDAO;
import com.example.demo.entity.Takeleave;
import com.example.demo.util.AuthenticationUtil;
import com.example.demo.util.CurrentTimeStamp;

@Repository
public class TakeleaveDAODB implements TakeleaveDAO {

 @Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;

//jdbcTemplate 

// public int findCsID(final int rc_id){
//     String sql = "select rc.cs_id from takeleave tl inner join rollcall rc on rc.rc_id = tl.rc_id where rc.rc_id = 2";
//     final int CsID = this.jdbcTemplate.queryForObject(sql, Integer.class, rc_id); //find cs_id from rc_id
// }

public int updateTltypeID(final Takeleave takeleave){
    return jdbcTemplate.update("update rc_record set rc_id = ?, std_id = ?, tl_type_id = ? where rc_id = ? and std_id = ?",
    takeleave.getRc_id(), takeleave.getStd_id(), takeleave.getTl_type_id(), takeleave.getRc_id(), takeleave.getStd_id());
}

public int findTltypeID(final int rc_id, final int std_id){
    String sql = "select tl_type_id from takeleave where rc_id = ? and std_id = ?";
    final int typeID = this.jdbcTemplate.queryForObject(sql, Integer.class, rc_id, std_id);
    return typeID; //找tl_type
}

public int findStateInTheTakeleave(int rc_id, int std_id){
    String sql = "select tl_state from takeleave where rc_id = ? and std_id = ?";
    final int state = this.jdbcTemplate.queryForObject(sql, Integer.class, rc_id, std_id);
    return state;
}

public int queryState(final int rc_id, final int std_id, final int tl_state){ //判斷state是否為0
    String sql = "select count(std_id) as count from takeleave where rc_id = ? and std_id  = ? and tl_state = ?";
    final int count = this.jdbcTemplate.queryForObject(sql, Integer.class, rc_id, std_id, tl_state);
    return count;
}

public int queryStudentInTakeleave(final int rc_id, final int std_id){
    String sql = "select count(std_id) as count from takeleave where rc_id = ? and std_id = ?";
    final int count = this.jdbcTemplate.queryForObject(sql, Integer.class,rc_id, std_id);
    return count; //這個請假中是否已經有此學生
}


// public List<Takeleave> findAll() {
//     return this.jdbcTemplate.query( "select * from takeleave", new TakeleaveMapper());
// }


public int Applyforleave(final Takeleave takeleave) {
    CurrentTimeStamp ts = new CurrentTimeStamp();
    String timestamp = ts.getCurrentTimeStamp();
    return jdbcTemplate.update("insert into takeleave (rc_id, std_id, tl_content, tl_type_id, tl_createtime) values(?, ?, ?, ?, ?)",
    takeleave.getRc_id(), takeleave.getStd_id(), takeleave.getTl_content(), takeleave.getTl_type_id(), timestamp);
    
}

public List<Takeleave> findTakeleaveInTheClass(final String cs_id){
    return this.jdbcTemplate.query("select rc.rc_starttime,rcrc.record_time, tl.tl_createtime, s.std_id ,s.std_name, tt.tl_type_name, tl.tl_type_id, tl.tl_content, tl.tl_state from takeleave tl inner join rc_record rcrc on rcrc.rc_id = tl.rc_id inner join student s on s.std_id = tl.std_id inner join rollcall rc on rc.rc_id = tl.rc_id inner join takeleave_type tt on tt.tl_type_id = tl.tl_type_id where rc.cs_id = ? group by tl.tl_id",
        new Object[]{cs_id}, new TakeleaveMapper());
}

public List<Takeleave> findStudentTakeleaveRecord(final String std_id, final String cs_id){
    return this.jdbcTemplate.query("select rc.rc_starttime, rcrc.record_time, tl.tl_createtime, tl. tl_type_id, tl.tl_content, tl.tl_state from takeleave tl inner join rc_record rcrc on rcrc.rc_id = tl.rc_id inner join rollcall rc on rc.rc_id = tl.rc_id where tl.std_id = ? and rc.cs_id= ? group by tl.tl_id",
        new Object[]{std_id, cs_id}, new TakeleaveMapper1());
}

public List<Takeleave> findStudentTakeleave(final String std_id, final String cs_id){
    return this.jdbcTemplate.query("select rc.rc_id, rc.rc_starttime, rc.rc_inputsource, tl.tl_type_id from rc_record rcre inner join rollcall rc on rc.rc_id = rcre.rc_id inner join takeleave tl on tl.rc_id = rcre.rc_id where rcre.std_id = ? and rc.cs_id = ? and tl.tl_type_id = 0",
        new Object[]{std_id, cs_id}, new TakeleaveMapper2());
}

private static final class TakeleaveMapper implements RowMapper<Takeleave> {

    public Takeleave mapRow(ResultSet rs, int rowNum) throws SQLException {
     Takeleave takeleave = new Takeleave();
     takeleave.setRc_starttime(rs.getString("rc_starttime"));
     takeleave.setRecord_time(rs.getString("record_time"));
     takeleave.setTl_createtime(rs.getString("tl_createtime"));
     takeleave.setStd_id(rs.getInt("std_id"));
     takeleave.setStd_name(rs.getString("std_name"));
     takeleave.setTl_type_name(rs.getString("tl_type_name"));
     takeleave.setTl_type_id(rs.getInt("tl_type_id"));
     takeleave.setTl_content(rs.getString("tl_content"));
     takeleave.setTl_state(rs.getInt("tl_state"));
        return takeleave;
    }
}

private static final class TakeleaveMapper1 implements RowMapper<Takeleave> {

    public Takeleave mapRow(ResultSet rs, int rowNum) throws SQLException {
     Takeleave takeleave = new Takeleave();
     takeleave.setRc_starttime(rs.getString("rc_starttime"));
     takeleave.setRecord_time(rs.getString("record_time"));
     takeleave.setTl_createtime(rs.getString("tl_createtime"));
     takeleave.setTl_type_id(rs.getInt("tl_type_id"));
     takeleave.setTl_content(rs.getString("tl_content"));
     takeleave.setTl_state(rs.getInt("tl_state"));
        return takeleave;
    }
}

private static final class TakeleaveMapper2 implements RowMapper<Takeleave> {

    public Takeleave mapRow(ResultSet rs, int rowNum) throws SQLException {
     Takeleave takeleave = new Takeleave();
     takeleave.setRc_id(rs.getInt("rc_id"));
     takeleave.setRc_starttime(rs.getString("rc_starttime"));
     takeleave.setRc_inputsource(rs.getString("rc_inputsource"));
        return takeleave;
    }
}





// private static final class TakeleaveMapper implements RowMapper<Takeleave> {

//     public Takeleave mapRow(ResultSet rs, int rowNum) throws SQLException {
//      Takeleave takeleave = new Takeleave();
//      takeleave.setTl_id(rs.getInt("tl_id"));
//      takeleave.setRc_id(rs.getInt("rc_id"));
//      takeleave.setStd_id(rs.getInt("std_id"));
//      takeleave.setTl_content(rs.getString("tl_content"));
//      takeleave.setTl_state(rs.getInt("tl_state"));
//      takeleave.setTl_type_id(rs.getInt("tl_type_id"));
//      takeleave.setTl_teacher_reply(rs.getString("tl_teacher_reply"));
//      takeleave.setTl_createtime(rs.getString("tl_createtime"));

//         return takeleave;
//     }
// }

public int Allowleave(final Takeleave takeleave){

    return jdbcTemplate.update("update takeleave set rc_id = ?, std_id = ?, tl_state = 1 where rc_id = ? and std_id = ?",
    takeleave.getRc_id(), takeleave.getStd_id(),takeleave.getRc_id(), takeleave.getStd_id());
}

public int UnAllowleave(final Takeleave takeleave){

    return jdbcTemplate.update("update takeleave set rc_id = ?, std_id = ?, tl_state = 2 where rc_id = ? and std_id = ?",
    takeleave.getRc_id(), takeleave.getStd_id(),takeleave.getRc_id(), takeleave.getStd_id());
    
}

public int updateContent(final Takeleave takeleave){
    AuthenticationUtil auth = new AuthenticationUtil();
    String std_id = auth.getCurrentUserName();
    return jdbcTemplate.update("update takeleave set rc_id = ?, std_id = ?, tl_content = ? where rc_id = ? and std_id = ?",
    takeleave.getRc_id(), std_id, takeleave.getTl_content(), takeleave.getRc_id(), std_id);
}


 
}



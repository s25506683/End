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
import com.example.demo.util.CurrentTimeStamp;

@Repository
public class TakeleaveDAODB implements TakeleaveDAO {

 @Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;

//jdbcTemplate 

public int queryStudentInTakeleave(final int rc_id, final int std_id){
    String sql = "select count(std_id) as count from takeleave where rc_id = ? and std_id = ?";
    final int count = this.jdbcTemplate.queryForObject(sql, Integer.class,rc_id, std_id);
    return count; //這個請假中是否已經有此學生
}

public List<Takeleave> findAll() {
    return this.jdbcTemplate.query( "select * from takeleave", new TakeleaveMapper());
}


public int Applyforleave(final Takeleave takeleave) {
    CurrentTimeStamp ts = new CurrentTimeStamp();
    String timestamp = ts.getCurrentTimeStamp();
    return jdbcTemplate.update("insert into takeleave (rc_id, std_id, tl_content, tl_type_id, tl_createtime) values(?, ?, ?, ?, ?)",
    takeleave.getRc_id(), takeleave.getStd_id(), takeleave.getTl_content(), takeleave.getTl_type_id(), timestamp);
    
}



private static final class TakeleaveMapper implements RowMapper<Takeleave> {

    public Takeleave mapRow(ResultSet rs, int rowNum) throws SQLException {
     Takeleave takeleave = new Takeleave();
     takeleave.setTl_id(rs.getInt("tl_id"));
     takeleave.setRc_id(rs.getInt("rc_id"));
     takeleave.setStd_id(rs.getInt("std_id"));
     takeleave.setTl_content(rs.getString("tl_content"));
     takeleave.setTl_state(rs.getInt("tl_state"));
     takeleave.setTl_type_id(rs.getInt("tl_type_id"));
     takeleave.setTl_teacher_reply(rs.getString("tl_teacher_reply"));
     takeleave.setTl_createtime(rs.getString("tl_createtime"));

        return takeleave;
    }
}

// public int LeaveforReview(final Takeleave takeleave){
//     return jdbcTemplate.update("update takeleave set rc_id = ?, std_id = ?, tl_state = ? where rc_id = ? and std_id = ?",
//     )
// }


 
}



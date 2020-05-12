package com.example.demo.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.ExcelDownloadDAO;
import com.example.demo.entity.ExcelDownload;



    
@Repository
public class ExcelDownloadDAODB implements ExcelDownloadDAO {

    @Autowired
    private DataSource dataSource;
    @Autowired
    JdbcTemplate jdbcTemplate;



    public int RcIdExist(int rc_id){
        String sql = "select count(cs_id) as count from rollcall where rc_id = ?";
        int count = this.jdbcTemplate.queryForObject(sql,Integer.class,rc_id);
        return count;
    }

    public String queryRcStartTime(int rc_id){
        String sql = "select rc_starttime from rollcall where rc_id = ?";
        String rc_starttime = this.jdbcTemplate.queryForObject(sql,String.class,rc_id);
        return rc_starttime;
    }

    public String findCsId(int rc_id){
        String sql = "select cs_id from rollcall where rc_id = ?";
        String cs_id = this.jdbcTemplate.queryForObject(sql,String.class,rc_id);
        return cs_id;
    }

    public String findRcClassInfo(int rc_id){
        String sql = "select concat(rc.cs_id, ',', cs.cs_name, ',', rc.rc_starttime, ',', rc.rc_inputsource) from rollcall as rc inner join class as cs on cs.cs_id = rc.cs_id where rc_id = ?";
        String classinfo = this.jdbcTemplate.queryForObject(sql,String.class,rc_id);
        return classinfo;
    }

    public List<ExcelDownload> findOneRollcallRecord(int rc_id){
        return this.jdbcTemplate.query( "select rcre.std_id, st.std_name, st.std_department, rcre.record_time, tk.tl_type_name from rc_record as rcre inner join takeleave_type as tk on tk.tl_type_id = rcre.tl_type_id inner join student as st on st.std_id = rcre.std_id where rcre.rc_id = ?"
    , new Object[]{rc_id}, new ExcelDownloadMapper());
    }

    private static final class ExcelDownloadMapper implements RowMapper<ExcelDownload> {
        public ExcelDownload mapRow(final ResultSet rs, final int rowNum) throws SQLException {
           final ExcelDownload exceldownload = new ExcelDownload();
            exceldownload.setStd_id(rs.getInt("std_id"));
            exceldownload.setStd_name(rs.getString("std_name"));
            exceldownload.setStd_department(rs.getString("std_department"));
            exceldownload.setRecord_time(rs.getString("record_time"));
            exceldownload.setTl_type_name(rs.getString("tl_type_name"));
            return exceldownload;
        }
     }


}
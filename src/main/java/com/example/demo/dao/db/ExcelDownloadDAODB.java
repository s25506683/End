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



    public String findRcStarttime(int rc_id){
        String sql = "select rc_starttime from rollcall where rc_id = ?";
        String rc_starttime = this.jdbcTemplate.queryForObject(sql,String.class,rc_id);
        return rc_starttime;
    }

    public List<ExcelDownload> findOneRollcallRecord(int rc_id){
        return this.jdbcTemplate.query( "select rc.cs_id, rc.rc_inputsource, rcre.std_id, st.std_name, st.std_department, rcre.record_time, tk.tl_type_name from rc_record as rcre inner join rollcall as rc on rc.rc_id = rcre.rc_id inner join takeleave_type as tk on tk.tl_type_id = rcre.tl_type_id inner join student as st on st.std_id = rcre.std_id where rc.rc_id = ?"
    , new Object[]{rc_id}, new ExcelDownloadMapper());
    }

    private static final class ExcelDownloadMapper implements RowMapper<ExcelDownload> {
        public ExcelDownload mapRow(final ResultSet rs, final int rowNum) throws SQLException {
           final ExcelDownload exceldownload = new ExcelDownload();
            exceldownload.setCs_id(rs.getString("cs_id"));
            exceldownload.setRc_inputsource(rs.getString("rc_inputsource"));
            exceldownload.setStd_id(rs.getInt("std_id"));
            exceldownload.setStd_name(rs.getString("std_name"));
            exceldownload.setStd_department(rs.getString("std_department"));
            exceldownload.setRecord_time(rs.getString("record_time"));
            exceldownload.setTl_type_name(rs.getString("tl_type_name"));
            return exceldownload;
        }
     }


}
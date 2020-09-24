package com.example.demo.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.ExcelDownloadDAO;
import com.example.demo.entity.ExcelDownload;



    
@Repository
public class ExcelDownloadDAODB implements ExcelDownloadDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;



    public int CsIdExist(String cs_id){
        String sql = "select count(cs_id) as count from class where cs_id = ?";
        int count = this.jdbcTemplate.queryForObject(sql,Integer.class,cs_id);
        return count;
    }

    public String queryRcStartTime(int rc_id){
        String sql = "select rc_starttime from rollcall where rc_id = ?";
        String rc_starttime = this.jdbcTemplate.queryForObject(sql,String.class,rc_id);
        return rc_starttime;
    }

    public String findCsName(String cs_id){
        String sql = "select cs_name from class where cs_id = ?";
        String cs_name = this.jdbcTemplate.queryForObject(sql,String.class,cs_id);
        return cs_name;
    }

    public String findRcClassInfo(String cs_id){
        String sql = "select concat(cs.cs_id, ',', cs.cs_name, ',', t.teacher_name, ',', (select count(std_id) from class_student where cs_id = ?) ) from class cs join class_teacher ct on cs.cs_id = ct.cs_id join teacher t on t.teacher_id = ct.teacher_id where cs.cs_id = ?";
        String classinfo = this.jdbcTemplate.queryForObject(sql,String.class, cs_id, cs_id);
        return classinfo;
    }
    
    public String findAllRollcallStartTime(String cs_id){
        String sql = "select group_concat( rc_starttime SEPARATOR ',') as AllRollcallTime from rollcall where cs_id = ?";
        String classstarttime = this.jdbcTemplate.queryForObject(sql,String.class,cs_id);
        return classstarttime;
    }

    public List<ExcelDownload> findStudentList(String cs_id){
        return this.jdbcTemplate.query( "select st.std_id, st.std_name, st.std_department from class_student csst join student st on st.std_id = csst.std_id where csst.cs_id = ?"
    , new Object[]{cs_id}, new ExcelDownloadMapper());
    }

    public List<ExcelDownload> findRollcallRecord(String classinfo, String rc_starttime){
        return this.jdbcTemplate.query( "select rcre.std_id, tlty.tl_type_name from rc_record rcre join rollcall rc on rc.rc_id = rcre.rc_id join takeleave_type tlty on tlty.tl_type_id = rcre.tl_type_id where rc.cs_id = ? and rc.rc_starttime = ? order by rcre.std_id"
    , new Object[]{classinfo, rc_starttime}, new ExcelDownloadMapper2());
    }


    private static final class ExcelDownloadMapper implements RowMapper<ExcelDownload> {
        public ExcelDownload mapRow(final ResultSet rs, final int rowNum) throws SQLException {
           final ExcelDownload exceldownload = new ExcelDownload();
            exceldownload.setStd_id(rs.getInt("std_id"));
            exceldownload.setStd_name(rs.getString("std_name"));
            exceldownload.setStd_department(rs.getString("std_department"));
            return exceldownload;
        }
    }

    private static final class ExcelDownloadMapper2 implements RowMapper<ExcelDownload> {
        public ExcelDownload mapRow(final ResultSet rs, final int rowNum) throws SQLException {
           final ExcelDownload exceldownload = new ExcelDownload();
            exceldownload.setStd_id(rs.getInt("std_id"));
            exceldownload.setTl_type_name(rs.getString("tl_type_name"));
            return exceldownload;
        }
    }


}
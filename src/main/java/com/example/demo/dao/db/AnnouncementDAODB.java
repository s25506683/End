package com.example.demo.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.AnnouncementDAO;
import com.example.demo.entity.Announcement;

@Repository
public class AnnouncementDAODB implements AnnouncementDAO {

 @Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;


 public int queryClassHasExists(String cs_id){
    String sql = "select count(cs_id) as count from class where cs_id = ?";
    int count = this.jdbcTemplate.queryForObject(sql,Integer.class,cs_id);
    return count;
 }

 public int hasAtIdExists(int at_id){
    String sql = "select count(at_id) as count from announcement where at_id = ?";
    int count = this.jdbcTemplate.queryForObject(sql,Integer.class,at_id);
    return count;
 }

 public int postAnnouncement(Announcement announcement) {
    return jdbcTemplate.update(
        "insert into announcement (cs_id, at_title, at_content, at_posttime) values(?, ?, ?, ?)",
        announcement.getCs_id(), announcement.getAt_title(), announcement.getAt_content(), announcement.getAt_posttime());
 }

 public List<Announcement> findAllAnnouncementInTheClass(String cs_id){
    return this.jdbcTemplate.query( "select at_id, at_title, at_content, at_posttime from announcement where cs_id = ?"
    , new Object[]{cs_id}, new AnnouncementMapper());
 }

 private static final class AnnouncementMapper implements RowMapper<Announcement> {
    public Announcement mapRow(final ResultSet rs, final int rowNum) throws SQLException {
       final Announcement announcement = new Announcement();
        announcement.setAt_id(rs.getInt("at_id"));
        announcement.setAt_title(rs.getString("at_title"));
        announcement.setAt_content(rs.getString("at_content"));
        announcement.setAt_posttime(rs.getString("at_posttime"));
        return announcement;
    }
 }

 public int updateAnnouncement(Announcement announcement){
    return jdbcTemplate.update(
        "update announcement set at_title = ?, at_content = ? where at_id = ?",
        announcement.getAt_title(), announcement.getAt_content(), announcement.getAt_id());
 }

 public int deleteAnnouncement(Announcement announcement){
    return jdbcTemplate.update(
        "delete from announcement where at_id =? and cs_id = ?", announcement.getAt_id(), announcement.getCs_id());
 }




}
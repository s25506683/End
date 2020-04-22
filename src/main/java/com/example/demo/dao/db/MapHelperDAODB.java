package com.example.demo.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.MapHelperDAO;



@Repository
public class MapHelperDAODB implements MapHelperDAO{

 @Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;



 public String GetDatabase(int rc_id) {
    //String sql = "select concat(latitude, ',', longitude) from rollcall where rc_id = ?";
    String sql = "select concat(latitude, ',', longitude) from rollcall where rc_id = ?";
    String point = this.jdbcTemplate.queryForObject(sql, String.class,rc_id);


    return point;
 }




}
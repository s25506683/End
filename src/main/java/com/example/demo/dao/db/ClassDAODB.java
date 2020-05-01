package com.example.demo.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.ClassDAO;
import com.example.demo.entity.Class;

@Repository
public class ClassDAODB implements ClassDAO {

 @Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;

//jdbcTemplate 

 
 
}



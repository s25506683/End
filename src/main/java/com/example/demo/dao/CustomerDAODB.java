package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Customer;

@Repository
public class CustomerDAODB implements CustomerDAO {

 @Autowired
 private DataSource dataSource;
 @Autowired
 JdbcTemplate jdbcTemplate;

//jdbcTemplate 

 public int insert(Customer customer) {
    return jdbcTemplate.update(
      "insert into customer (des, price, category, inventory, safetyStock) values(?, ?, ?, ?, ?)",
      customer.getDes(), customer.getPrice(), customer.getCategory(), customer.getInventory(), customer.getSafetyStock());
 }

 public Customer findOne(Long id) {
    return this.jdbcTemplate.queryForObject( "select id, des, price, category, inventory, safetyStock from customer where id = ?", new Object[]{id}, new CustomerMapper());
  }

 public List<Customer> findAll() {
     return this.jdbcTemplate.query( "select id, des, price, category, inventory, safetyStock from customer", new CustomerMapper());
 }

 private static final class CustomerMapper implements RowMapper<Customer> {

     public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
         Customer customer = new Customer();
         customer.setId(rs.getLong("id"));
         customer.setDes(rs.getString("des"));
         customer.setPrice(rs.getInt("price"));
         customer.setCategory(rs.getString("category"));
         customer.setInventory(rs.getInt("inventory"));
         customer.setSafetyStock(rs.getInt("safetyStock"));
         return customer;
     }
 }
 public int update(Customer customer) {
    return jdbcTemplate.update(
      "update customer set des=?, price=?, category=?, inventory=?, safetyStock=? where id =?",
      customer.getDes(), customer.getPrice(), customer.getCategory(), customer.getInventory(), customer.getSafetyStock(), customer.getId());
 }

 public int delete(Long id) {
    return jdbcTemplate.update(
      "delete from customer where id =?", id);
 }


 
}



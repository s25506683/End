package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Customer;

public interface CustomerDAO {

 public int insert(Customer customer);
 public List<Customer> findAll();
 public Customer findOne(Long id);
 public int update(Customer customer);
 public int delete(Long id);

}



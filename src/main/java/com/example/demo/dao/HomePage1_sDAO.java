package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.HomePage1_s;

public interface HomePage1_sDAO {

 public int insert(HomePage1_s homepage1_s);
 public List<HomePage1_s> findAll(String cs_id);
 public HomePage1_s findOne(String cs_id,int std_id);
 public int update(HomePage1_s homepage1_s);
 public int delete(int id);

 
}

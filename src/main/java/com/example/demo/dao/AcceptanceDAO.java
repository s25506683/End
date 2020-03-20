package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Acceptance;

public interface AcceptanceDAO {


 public int queryUser(int accept_std_id , int accept_hw_id);
 public int insert(Acceptance acceptance);
 public List<Acceptance> findAll(String cs_id, String hw_name);
 public Acceptance findOne(int accept_std_id);
 public int update(Acceptance acceptance);
 public int delete(int id);
 

}


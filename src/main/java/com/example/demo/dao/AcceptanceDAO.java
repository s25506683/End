package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Acceptance;

public interface AcceptanceDAO {

 public int insert(Acceptance acceptance);
 public List<Acceptance> findAll(String hw);
 public Acceptance findOne(int id);
 public int update(Acceptance acceptance);
 public int delete(int id);
 

}



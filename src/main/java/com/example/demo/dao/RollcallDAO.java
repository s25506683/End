package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Rollcall;

public interface RollcallDAO {

 public int addrollcall(Rollcall rollcall);
 public List<Rollcall> findAllRollcallRecord(String cs_id);
 public List<Rollcall> findOneRollcallRecord(String rc_id);
 //public int update(HomePage1_s homepage1_s);
 //public int delete(int id);

 
}

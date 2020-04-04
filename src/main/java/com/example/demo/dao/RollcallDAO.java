package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Rollcall;

public interface RollcallDAO {

 public int addRollcall(Rollcall rollcall);

 public int hasTheSameRollcallName(String rc_name);

 public String[] findClassStudent(String cs_id);

 public int addRollcallRecord(String rc_name, int std_id);

 public List<Rollcall> findAllRollcallRecord(String cs_id);

 public List<Rollcall> findOneRollcallRecord(int rc_id);
 
 public List<Rollcall> findClassList(String cs_id);
 //public int update(HomePage1_s homepage1_s);
 //public int delete(int id);

 
}

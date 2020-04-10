package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Rollcall;

public interface RollcallDAO {

 public int addRollcall(Rollcall rollcall);

 //public int hasTheSameRollcallName(String rc_name);

 public int findRcId(String cs_id, String rc_starttime);

 public int hasThisRollcallId(int rc_id);

 public String[] findClassStudent(String cs_id);

 public int addRollcallRecord(int rc_id, int std_id);

 public List<Rollcall> findAllRollcallRecord(String cs_id);

 public List<Rollcall> findOneRollcallRecord(int rc_id);
 
 public List<Rollcall> findClassStudentList(String cs_id);

 public String findQRcodeInRollcallName(int rc_id);

 public String rollcallByHand(int rc_id);

 public int rollcallIsEnd(int rc_id);

 public int updateRollcallRecord(int std_id, int rc_id);

 public int updateQRcode(int rc_id, String qrcode);

 public int deleteRollcall(int rc_id);

 
}

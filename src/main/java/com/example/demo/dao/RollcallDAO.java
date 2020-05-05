package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Rollcall;

public interface RollcallDAO {

 public int addRollcall(Rollcall rollcall);

 //public int hasTheSameRollcallName(String rc_name);

 public int findRcId(String cs_id, String rc_starttime);

 public String findCs_id(int rc_id);

 public String findTlTypeName(int tl_type_id);

 public int findRcIdWithQRcode2(String qrcode);

 public int hasThisQRcode(String qrcode);

 public int hasThisRollcallId(int rc_id);

 public int hasThisCsId(String cs_id);

 public int hasThisRcRecord(int std_id, int rc_id);

 public String[] findClassStudent(String cs_id);

 public int addRollcallRecord(int rc_id, int std_id);

 public Rollcall findOneRollcallSummaryRecord(int rc_id);

 public List<Rollcall> findAllRollcallRecord(String cs_id);

 public List<Rollcall> findOneRollcallRecord(int rc_id);

 public String findQRcodeInRollcallName(int rc_id);

 public List<Rollcall> findStudentOwnRollcallInClass(int std_id, String cs_id);
 
 public List<Rollcall> findRollcallByPerson(String cs_id);

 public List<Rollcall> findRcIdWithQRcode(String qrcode);

 public String rollcallByHand(int rc_id);

 public int rollcallIsEnd(int rc_id);

 public int updateRollcallRecord(int std_id, int rc_id);

 public int updateQRcode(int rc_id, String qrcode);

 public int updateRollcall(int rc_id, int std_id, int tl_type_id);

 public int deleteRollcall(int rc_id);

 
}

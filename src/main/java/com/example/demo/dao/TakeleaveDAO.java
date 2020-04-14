package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Takeleave;

public interface TakeleaveDAO {

public int queryStudentInTakeleave(int rc_id, int std_id);

public List<Takeleave> findAll();

public int Applyforleave(Takeleave takeleave);  //請假申請

//public int LeaveforReview(Takeleave takeleave); //請假審核

}



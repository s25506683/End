package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Takeleave;

public interface TakeleaveDAO {

public int queryStudentInTakeleave(int rc_id, int std_id);

public List<Takeleave> findAll();

public int Applyforleave(Takeleave takeleave);  //請假申請

public int Allowleave(Takeleave takeleave); //教師允許請假

public int UnAllowleave(Takeleave takeleave); //教師不允許請假

public int updateTltypeID(Takeleave takeleave);

public int findTltypeID(int rc_id, int std_id);



}



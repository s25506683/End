package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Takeleave;

public interface TakeleaveDAO {

public List<Takeleave> findTakeleaveInTheClass(String cs_id);

public List<Takeleave> findStudentTakeleaveRecord(String std_id, String cs_id, int tl_state);

public List<Takeleave> findStudentTakeleave(String std_id, String cs_id);

public int queryStudentInTakeleave(int rc_id, int std_id);

public int queryState(int rc_id, int std_id, int tl_state);

public int findStateInTheTakeleave(int rc_id, int std_id); //查看state(教師是否已經審核過)

public String findCsID(int rc_id);

public int Applyforleave(Takeleave takeleave);  //請假申請

public int Allowleave(Takeleave takeleave); //教師允許請假

public int UnAllowleave(Takeleave takeleave); //教師不允許請假

public int updateTltypeID(Takeleave takeleave);

public int UnAllowUpdateTltypeID(Takeleave takeleave);

public int findTltypeID(int rc_id, int std_id);

public int updateContent(Takeleave takeleave); //學生修改請假內容



}



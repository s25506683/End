package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Acceptance;

public interface AcceptanceDAO {


 public int queryUser(int accept_std_id , int accept_hw_id);

 public int insertAcceptance(Acceptance acceptance);

 public int insertHomework(Acceptance acceptance);

 public List<Acceptance> findHomeworkDetail(String cs_id, String hw_name);

 public List<Acceptance> findCourseHomework(int std_id, String hw_cs_id);

 public int updateScore(Acceptance acceptance);

 public int updateContent(Acceptance acceptance);

 public int delete(int id, int accept_hw_id);
 

}


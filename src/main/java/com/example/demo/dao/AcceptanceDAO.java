package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Acceptance;

public interface AcceptanceDAO {

 public int queryStudentAcceptDone(int accept_std_id, boolean accept_done, int accept_hw_id);

 public int findHomeworkID(String hw_name);

 public String findCsID(int hw_id);



 public int queryStudentInTheAcceptance(int Std_id , int accept_hw_id); 

 public int queryHomeworkInTheClass(String hw_name ,String hw_cs_id);

 public int queryHomeworkID(int hw_id);

 public int queryTeacherInTheClass(String cs_id, String teacher_id);

 public int queryStudentInTheClass(String cs_id, String std_id);

 public int insertAcceptance(Acceptance acceptance);

 public int insertHomework(Acceptance acceptance);

//  public List<Acceptance> findStudentID(String hw_name);

 public List<Acceptance> findCourseHomework(String hw_cs_id); //驗收中學生可以看到作業的相關內容

 public List<Acceptance> findHomeworkDetail(String cs_id, String hw_name); //學生查看驗收排序資料

 public List<Acceptance> findCourseHomeworkformTeacher(String hw_cs_id); //驗收中教師可以看到作業的內容

 public List<Acceptance> findHomeworkDetailformTeacher(String cs_id, String hw_name);//教師查看作業驗收的排序

 public int updateScore(Acceptance acceptance);

 public int updateContent(Acceptance acceptance);

 public int deleteAcceptance(Acceptance acceptance);

 public int deleteHomework(Acceptance acceptance);
 

}


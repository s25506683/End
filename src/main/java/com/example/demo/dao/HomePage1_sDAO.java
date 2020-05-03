package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.HomePage1_s;

//import org.springframework.stereotype.Component;
//@Component
public interface HomePage1_sDAO {

 //public int queryCourseCount(int std_id);
 //public int insert(HomePage1_s homepage1_s);
 public List<HomePage1_s> findAllCourse();
 public List<HomePage1_s> findStudentCourse(int std_id);
 public List<HomePage1_s> findTeacherCourse(int std_id);
 //public int update(HomePage1_s homepage1_s);
 //public int delete(int id);

 public List<HomePage1_s> queryUserRole(int user_id);

//  public List<HomePage1_s> CheckUserRole(int std_id);

 
}

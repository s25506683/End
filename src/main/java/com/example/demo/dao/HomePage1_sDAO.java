package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.HomePage1_s;

//import org.springframework.stereotype.Component;
//@Component
public interface HomePage1_sDAO {

 public List<HomePage1_s> findAllCourse();

 public List<HomePage1_s> findStudentCourse(int std_id);

 public List<HomePage1_s> findTeacherCourse(int std_id);

 public HomePage1_s queryUserRoleByJson(int user_id);
 
 public int queryUserRole(int user_id);

 

 
}

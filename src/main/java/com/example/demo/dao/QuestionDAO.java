package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Question;

public interface QuestionDAO {

 public int insert(Question question);
 public List<Question> findAll(String cs_id);
 public Question findOne(String cs_id,int std_id);
 public int update(Question question);
 public int delete(int id);

}







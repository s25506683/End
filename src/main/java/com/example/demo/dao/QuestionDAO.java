package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Question;

public interface QuestionDAO {
 
    public int queryCs_id(String cs_id);

    public int queryStudentInTheClass(String std_id, String cs_id);
    
    public int queryTeacherInTheClass(String teacher_id, String cs_id);

    public int hasBeenReply(int std_id, String q_asktime);

    public int hasQuestion(int std_id, String q_asktime);

    public int studentinsert(Question question);

    public List<Question> findQuestion(String cs_id);

    //public Question findOne(String cs_id,int std_id);

    public int updateStudentQuestionContent(Question question);

    public int updateTeacherReply(Question question);

    public int deleteQuestion(Question question);

    public int deleteQuestionReply(Question question);

}







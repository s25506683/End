package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Question;

public interface QuestionDAO {

    public int findQuestionType(String std_id, String cs_id);

    public String findClassName(String cs_id);

    public String findUserMail(int std_id);

    public int hasThisStudentInPersonQuestion(String std_id, String cs_id);

    public int hasThisStudentInQuestion(String std_id, String cs_id);

    public String findQuestionAsktime(String std_id, String cs_id);
 
    public int queryCs_id(String cs_id);

    public int hasBeenReply(int std_id, String q_asktime);

    public int hasQuestion(int std_id, String q_asktime);

    public int studentinsert(Question question);

    public List<Question> findQuestion(String cs_id);

    public String findCsId(int std_id, String q_asktime);

    //public Question findOne(String cs_id,int std_id);

    public int updateStudentQuestionContent(Question question);

    public int updateTeacherReply(Question question);

    public int deleteQuestion(Question question);

    public int deleteQuestionReply(Question question);

}







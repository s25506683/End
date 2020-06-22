package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Question;

public interface QuestionDAO {

    public int changeSolvedState(String std_id, String q_asktime);

    public int checkTheQuestionHasBeenSolved(int q_id);

    public int findQuestionSolved(String cs_id, String q_asktime);

    public String findClassName(String cs_id);

    public String findUserMail(int std_id);

    public int hasThisStudentInPersonQuestion(String std_id, String cs_id);

    public int hasThisStudentInQuestion(String std_id, String cs_id);

    public String findQuestionAsktime(String std_id, String cs_id);
 
    public int queryCs_id(String cs_id);

    public int hasBeenReply(int std_id, String q_asktime);

    public int hasQuestion(int std_id, String q_asktime);

    public int hasQuestionFromTeacher(String q_asktime);

    public int TeacherAddNewMessages(Question question);

    public int StudentAddNewMessages(Question question);

    public int teacherinsert(Question question);

    public int studentinsert(Question question);

    public List<Question> findSolvedQuestion(String cs_id);

    public List<Question> findUnresolvedQuestion(String cs_id);

    public List<Question> findAllQuestionsThisStudentAsked(String std_id, String cs_id);

    public List<Question> findAllmessageIntheQuestion(int q_id);

    public String findCsId(int std_id, String q_asktime);

    public int TeacherCompletionQuestion(Question question);

    public int StudentCompletionQuestion(Question question);

    public int updateStudentCommentBoxContent(Question question);

    public int updateTeacherCommentBoxContent(Question question);

    public int updateStudentQuestionContent(Question question);

    public int deleteQuestion(Question question);

    public int deleteTeacherQuestion(Question question);

    public int deleteStudentMessages(Question question);

    public int deleteTeacherMessages(Question question);

}







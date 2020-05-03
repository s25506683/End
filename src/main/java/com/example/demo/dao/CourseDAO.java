package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Course;

public interface CourseDAO {

    public int TeacherCreateCourse(Course course);

    public int TeacherAddToClass(int teacher_id, String cs_id);

    public int StudentJoinClass(int std_id, String cs_id);
    
    public int hasSameClassId(String cs_id);

    public int hasThisQRcode(String cs_qrcode);

    public List<Course> findClassStudentList(String cs_id);

    public int updateCsQRcode(String cs_id, String cs_qrcode);

}



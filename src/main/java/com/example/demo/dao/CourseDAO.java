package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Course;

public interface CourseDAO {

    public int TeacherNewCourse(Course course);
    

    public List<Course> findClassStudentList(String cs_id);

}



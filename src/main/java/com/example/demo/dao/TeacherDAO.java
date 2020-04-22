package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Teacher;

public interface TeacherDAO {

	public int queryUser(int teacher_id);

	public String getPassword(int teacher_id);

	public int updateTeacherPassword(int teacher_id, String teacher_password);

	public int insert(Teacher teacher);

	public List<Teacher> findAll();

	public Teacher findOne(int teacher_id);

//	public int update(Teacher teacher);
//
//	public int delete(int std_id);
}

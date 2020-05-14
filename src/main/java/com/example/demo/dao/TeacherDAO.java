package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Teacher;

public interface TeacherDAO {

	public int queryUser(int teacher_id);

	public String getPassword(int teacher_id);

	public int updateTeacherPassword(int teacher_id, String teacher_password);

	public int updateTeacherMail(int teacher_id, String teacher_mail);

	public int updateTeacherPhone(int teacher_id, String teacher_phone);

	public int insert(Teacher teacher);

	public List<Teacher> findAll();

	public Teacher findOne(int teacher_id);

	public int resetPasswordVerify(int teacher_id, String teacher_mail, String teacher_phone);

	public List<Teacher> findStudentInformation(String std_id);

	public int DeletestudentManually(Teacher teacher);

	public int AddstudentManually(String std_id, String cs_id);

//	public int update(Teacher teacher);
//
//	public int delete(int std_id);
}

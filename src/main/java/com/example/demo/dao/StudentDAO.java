package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Student;

public interface StudentDAO {
	//public void writeLog(String writtenmessage);

	public int queryUser(int std_id);

	public String getPassword(int std_id);

	//public int passwordHasRound(int std_id, String old_std_password);

	public int resetPasswordVerify(int std_id, String std_mail, String std_phone);

	public int insert(Student student);

	public List<Student> findAll();

	public Student findOne(int std_id);

	public int updateStudentPassword(int std_id, String std_password);

	public int updateStudentMail(int std_id, String std_mail);

	public int delete(int std_id);
}

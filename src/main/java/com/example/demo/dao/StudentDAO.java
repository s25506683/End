package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Student;

public interface StudentDAO {

	public int insert(Student student);

	public List<Student> findAll();

	public Student findOne(int std_id);

	public int update(Student student);

	public int delete(int std_id);
}

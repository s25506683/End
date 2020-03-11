package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Registration;

public interface RegistrationDAO {

	public int insert(Registration registration);

	public List<Registration> findAll();

	public Registration findOne(int std_id);

	public int update(Registration registration);

	public int delete(int std_id);

}

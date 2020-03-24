package com.example.demo.dao.db;


import java.util.List;

import java.io.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.LogfileDAO;
import com.example.demo.entity.Student;

@Repository
public class LogfileDAODB implements LogfileDAO {

	

	public void writeLog(String writtenmessage){
		String directory = System.getProperty("user.dir");
		String fileName = "src/main/java/com/example/demo/util/logfile.txt";
		String absolutePath = directory + File.separator + fileName;
		System.out.println(absolutePath);
		
		try (FileWriter filewriter = new FileWriter(absolutePath,true)) {
			String fileContent = writtenmessage + "\n";
			filewriter.write(fileContent);
			filewriter.flush();
			filewriter.close();
		} catch (IOException e) {
			System.err.print("Something went wrong");
		}
	}


}

package com.example.demo.util;

import java.util.List;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;


@Repository
public class Logfile {

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	LocalDateTime now = LocalDateTime.now();

	public void writeLog(String writtenmessage) {
		String directory = System.getProperty("user.dir");
		//String filename = "src/main/java/com/example/demo/util/logfile.txt";
		//String absolutePath = directory + File.separator + filename;
		String filepath = "src/main/java/com/example/demo/util/";
		String absolutePath = directory + File.separator + filepath + "logfile.txt";
		System.out.println(absolutePath);
		
		try (FileWriter filewriter = new FileWriter(absolutePath,true)) {
			String fileContent = dtf.format(now) + "\t" + writtenmessage + "\n";
			filewriter.write(fileContent);
			filewriter.flush();
			filewriter.close();
		} catch (IOException e) {
			System.err.print("Something went wrong");
		}
	}

	public void writeLog(String writtenmessage, String cs_id) throws IOException {
		String directory = System.getProperty("user.dir");
		//String filename = "src/main/java/com/example/demo/util/logfile.txt";
		//String absolutePath = directory + File.separator + filename;
		String filepath = "src/main/java/com/example/demo/util/classlog/";
		String absolutePath = directory + File.separator + filepath + cs_id + "_logfile.txt";
		System.out.println(absolutePath);
		
		try (FileWriter filewriter = new FileWriter(absolutePath,true)) {
			String fileContent = dtf.format(now) + "\t" + writtenmessage + "\n";
			filewriter.write(fileContent);
			filewriter.flush();
			filewriter.close();
		} catch (IOException e) {
			File file = new File(absolutePath);
			file.createNewFile();
			FileWriter filewriter = new FileWriter(absolutePath);
			String fileContent = dtf.format(now) + "\t" + writtenmessage + "\n";
			filewriter.write(fileContent);
			filewriter.flush();
			filewriter.close();
			//System.err.print("Something went wrong");
		}
	}


}

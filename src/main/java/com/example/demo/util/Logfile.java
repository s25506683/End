package com.example.demo.util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Repository;


@Repository
public class Logfile {

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	public void writeLog(String writtenmessage) {
		String directory = System.getProperty("user.dir");
		String filepath = "src/main/java/com/example/demo/util/";
		String absolutePath = directory + File.separator + filepath + "logfile.txt";
		System.out.println(absolutePath);
		
		try (FileWriter filewriter = new FileWriter(absolutePath,true)) {
			String fileContent = dtf.format(LocalDateTime.now()) + "\t" + writtenmessage + "\n";
			filewriter.write(fileContent);
			filewriter.flush();
			filewriter.close();
		} catch (IOException e) {
			System.err.print("Something went wrong");
		}
	}

	public void writeLog(String writtenmessage, String cs_id, String partition) throws IOException {
		String directory = System.getProperty("user.dir");
		String filepath = "src/main/java/com/example/demo/util/classlog";
		String absolutePath = directory + File.separator + filepath + File.separator + cs_id + File.separator + cs_id + "_" + partition + "_actions.txt";
		File dir = new File(filepath + File.separator + cs_id );

		Boolean bool = true;

		while(bool){
			if(dir.exists()){
				System.out.println(absolutePath);
			
				try (FileWriter filewriter = new FileWriter(absolutePath,true)) {
					String fileContent = dtf.format(LocalDateTime.now()) + "\t" + writtenmessage + "\n";
					filewriter.write(fileContent);
					filewriter.flush();
					filewriter.close();
				} catch (IOException e) {
					File file = new File(absolutePath);
					file.createNewFile();
					FileWriter filewriter = new FileWriter(absolutePath);
					String fileContent = dtf.format(LocalDateTime.now()) + "\t" + writtenmessage + "\n";
					filewriter.write(fileContent);
					filewriter.flush();
					filewriter.close();
					//System.err.print("Something went wrong");
				}
				bool = false;
			}else{
				dir.mkdirs();
			}
		}
	}

}

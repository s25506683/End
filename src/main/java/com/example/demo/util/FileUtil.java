package com.example.demo.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public class FileUtil {
    
    public static void download(String filepath, HttpServletResponse res) throws IOException {
        // 发送给客户端的数据
        OutputStream outputStream = res.getOutputStream();
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        

        String directory = System.getProperty("user.dir") + "/src/main/java/com/example/demo/util/classlog/";
        // 读取filename(filepath)
        bis = new BufferedInputStream(new FileInputStream(new File(directory + filepath)));
        //new BufferedInputStream(new FileInputStream(new File("./file/" + filename)));
        int i = bis.read(buff);
        while (i != -1) {
            outputStream.write(buff, 0, buff.length);
            outputStream.flush();
            i = bis.read(buff);
        }
    }
    
}
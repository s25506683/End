package com.example.demo.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.util.FileUtil;



@RestController
@RequestMapping("/download")
public class  FileDownloadController {


    //不可用！！！
    @RequestMapping("/Question/{cd_id}/98/")
    public void downloadPDFResource( HttpServletRequest request, HttpServletResponse response, @PathVariable("cd_id") String cd_id) {
        //If user is not authorized - he should be thrown out from here itself
         
        //Authorized user will download the file
        //String dataDirectory = request.getServletContext().getRealPath("/WEB-INF/downloads/pdf/");
        String directory = System.getProperty("user.dir") + "/src/main/java/com/example/demo/util/classlog/";
        System.out.println(directory);
        //Path file = Paths.get(dataDirectory, cd_id);//fileName
        Path file = Paths.get(directory, cd_id+"_Question_actions.java");//10811000DMG741D7411023900_Question_actions
        System.out.println("\n\n\n\n\n\n\n\n");
        System.out.println(directory);
        System.out.println(file);
        System.out.println("\n\n\n\n\n\n\n\n");

        if (Files.exists(file)) 
        {
            response.setContentType("text/plain");
            response.addHeader("Content-Disposition", "attachment; filename="+cd_id);//fileName
            try
            {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } 
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //下載announcement的actions
    @RequestMapping(value = "/AnnouncementActions/{cs_id}/")
    public void downloadAnnouncementActions(@PathVariable("cs_id") String cs_id)throws IOException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        // 设置信息给客户端不解析
        String filename = cs_id + "_Announcement_actions.txt";
        String type = new MimetypesFileTypeMap().getContentType(filename);
        // 设置contenttype，即告诉客户端所发送的数据属于什么类型
        response.setHeader("Content-type",type);
        // 设置编码
        String hehe = new String(filename.getBytes("utf-8"), "iso-8859-1");
        // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
        response.setHeader("Content-Disposition", "attachment;filename=" + hehe);
        String filepath = cs_id + "/" + filename;
        FileUtil.download(filepath, response);
    }

    //下載acceptance的actions
    @RequestMapping(value = "/AcceptanceActions/{cs_id}/")
    public void downloadAcceptanceActions(@PathVariable("cs_id") String cs_id)throws IOException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        
        String filename = cs_id + "_Acceptance_actions.txt";
        String type = new MimetypesFileTypeMap().getContentType(filename);
        
        response.setHeader("Content-type",type);
        
        String hehe = new String(filename.getBytes("utf-8"), "iso-8859-1");
        
        response.setHeader("Content-Disposition", "attachment;filename=" + hehe);
        String filepath = cs_id + "/" + filename;
        FileUtil.download(filepath, response);
    }

    //下載question的actions
    @RequestMapping(value = "/QuestionActions/{cs_id}/")
    public void downloadQuestionActions(@PathVariable("cs_id") String cs_id)throws IOException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        
        String filename = cs_id + "_Question_actions.txt";
        String type = new MimetypesFileTypeMap().getContentType(filename);
        
        response.setHeader("Content-type",type);
        
        String hehe = new String(filename.getBytes("utf-8"), "iso-8859-1");
        
        response.setHeader("Content-Disposition", "attachment;filename=" + hehe);
        String filepath = cs_id + "/" + filename;
        FileUtil.download(filepath, response);
    }

    //下載rollcall的actions
    @RequestMapping(value = "/RollcallActions/{cs_id}/")
    public void downloadRollcallActions(@PathVariable("cs_id") String cs_id)throws IOException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        
        String filename = cs_id + "_Rollcall_actions.txt";
        String type = new MimetypesFileTypeMap().getContentType(filename);
        
        response.setHeader("Content-type",type);
        
        String hehe = new String(filename.getBytes("utf-8"), "iso-8859-1");
        
        response.setHeader("Content-Disposition", "attachment;filename=" + hehe);
        String filepath = cs_id + "/" + filename;
        FileUtil.download(filepath, response);
    }







}
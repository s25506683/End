package com.example.demo.util;

import com.example.demo.dao.AnnouncementDAO;
import com.example.demo.entity.Announcement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.util.ResetPwdMailTemplate;



@Service
public class MailService {

    @Autowired
    AnnouncementDAO dao;

    @Autowired
    ResetPwdMailTemplate resetpwdmailtemplate;


    private JavaMailSender mailSender;
 
    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
 
    public void prepareAndSend(String recipient, String new_password, String std_name) {
       MimeMessagePreparator messagePreparator = mimeMessage -> {
             MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
             messageHelper.setFrom("qrgomanager@gmail.com");
             messageHelper.setTo(recipient);
             messageHelper.setSubject("您的RollsCall系統密碼已經變更");
             resetpwdmailtemplate.setUserName(std_name);
             resetpwdmailtemplate.setNewPassword(new_password);
             messageHelper.setText(resetpwdmailtemplate.getNewMailTemplate(), true);
             //messageHelper.setText(resetpwdmailtemplate.getMailTemplate());
         };
         try {
             mailSender.send(messagePreparator);
             //System.out.println("sent");
         } catch (MailException e) {
             //System.out.println(e);
             // runtime exception; compiler will not force you to handle it
         }
    }



    public void prepareAndSendwithTeacher(String recipient, String message, int teacher_id) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
              MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
              messageHelper.setFrom("qrgomanager@gmail.com");
              messageHelper.setTo(recipient);
              messageHelper.setSubject("您的QRgo系統帳號為" + teacher_id + "的密碼已經變更");
              messageHelper.setText("您的新密碼為：" + message + "\n請儘速登入系統更改密碼！");
          };
          try {
              mailSender.send(messagePreparator);
              //System.out.println("sent");
          } catch (MailException e) {
              //System.out.println(e);
              // runtime exception; compiler will not force you to handle it
          }
     }


     public void sendAnnouncementToStudent(String recipient, int teacher_id, @RequestBody final Announcement announcement) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
              MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
              messageHelper.setFrom("qrgomanager@gmail.com");
              messageHelper.setTo(recipient);
              messageHelper.setSubject("您的課堂 " +  dao.findClassName(announcement.getCs_id())+ " 已新增新的公告");
              messageHelper.setText("新的公告內容為:\n\n" + announcement.getAt_content() + "\n\n請盡快登入系統確認！");
          };
          try {
              mailSender.send(messagePreparator);
              //System.out.println("sent");
          } catch (MailException e) {
              //System.out.println(e);
              // runtime exception; compiler will not force you to handle it
          }
     }


}
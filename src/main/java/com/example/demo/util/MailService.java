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

import com.example.demo.util.MailTemplate.ResetPwdMailTemplate;
import com.example.demo.util.MailTemplate.EditAnnouncementTemplate;
import com.example.demo.util.MailTemplate.NewAnnouncementTemplate;



@Service
public class MailService {

    @Autowired
    AnnouncementDAO dao;

    @Autowired
    ResetPwdMailTemplate resetpwdmailtemplate;

    @Autowired
    NewAnnouncementTemplate newannouncementtemplate;

    @Autowired
    EditAnnouncementTemplate editannouncementtemplate;


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
            // messageHelper.setText(resetpwdmailtemplate.getMailTemplate());
        };
        try {
            mailSender.send(messagePreparator);
            // System.out.println("sent");
        } catch (MailException e) {
            // System.out.println(e);
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
            // System.out.println("sent");
        } catch (MailException e) {
            // System.out.println(e);
            // runtime exception; compiler will not force you to handle it
        }
    }

    public void sendAnnouncementToStudent(String[] recipient, String teacher_id, @RequestBody Announcement announcement) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            //String[] Bcc = {"s25506683@gmail.com", "lisa293341@gmail.com"};
            messageHelper.setFrom("qrgomanager@gmail.com");
            messageHelper.setTo(teacher_id);
            messageHelper.setBcc(recipient);
            messageHelper.setSubject("您的課堂 " + dao.findClassName(announcement.getCs_id()) + " 已新增新的公告");
            newannouncementtemplate.setAtTitle(announcement.getAt_title());
            newannouncementtemplate.setAtContent(announcement.getAt_content());
            newannouncementtemplate.setCsName(announcement.getCs_name());
            messageHelper.setText(newannouncementtemplate.getNewMailTemplate(), true);

        };
        try {
            mailSender.send(messagePreparator);
            // System.out.println("sent");
        } catch (MailException e) {
              //System.out.println(e);
              // runtime exception; compiler will not force you to handle it
          }
     }

     public void UpdateAnnouncementToStudent(String[] recipient, String teacher_mail, @RequestBody Announcement announcement) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            //String[] Bcc = {"s25506683@gmail.com", "lisa293341@gmail.com"};
            messageHelper.setFrom("qrgomanager@gmail.com");
            messageHelper.setTo(teacher_mail);
            messageHelper.setBcc(recipient);
            messageHelper.setSubject("您的課堂 " + dao.findClassName(announcement.getCs_id()) + " 已修改新的公告");
            editannouncementtemplate.setCsName(announcement.getCs_name());
            //editannouncementtemplate.setCsName("111");
            messageHelper.setText(editannouncementtemplate.getNewMailTemplate(), true);

        };
        try {
            mailSender.send(messagePreparator);
            // System.out.println("sent");
        } catch (MailException e) {
              //System.out.println(e);
              // runtime exception; compiler will not force you to handle it
          }
     }


}
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
import com.example.demo.util.MailTemplate.NewAnnouncementTemplate;



@Service
public class MailService {

    @Autowired
    AnnouncementDAO dao;

    @Autowired
    ResetPwdMailTemplate resetpwdmailtemplate;

    @Autowired
    NewAnnouncementTemplate newannouncementtemplate;


    private final JavaMailSender mailSender;

    @Autowired
    public MailService(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void prepareAndSend(final String recipient, final String new_password, final String std_name) {
        final MimeMessagePreparator messagePreparator = mimeMessage -> {
            final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
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
        } catch (final MailException e) {
            // System.out.println(e);
            // runtime exception; compiler will not force you to handle it
        }
    }

    public void prepareAndSendwithTeacher(final String recipient, final String message, final int teacher_id) {
        final MimeMessagePreparator messagePreparator = mimeMessage -> {
            final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("qrgomanager@gmail.com");
            messageHelper.setTo(recipient);
            messageHelper.setSubject("您的QRgo系統帳號為" + teacher_id + "的密碼已經變更");
            messageHelper.setText("您的新密碼為：" + message + "\n請儘速登入系統更改密碼！");
        };
        try {
            mailSender.send(messagePreparator);
            // System.out.println("sent");
        } catch (final MailException e) {
            // System.out.println(e);
            // runtime exception; compiler will not force you to handle it
        }
    }

    public void sendAnnouncementToStudent(final String recipient, final int teacher_id, final String at_title, final String at_content, final String cs_name,
            @RequestBody final Announcement announcement) {
        final MimeMessagePreparator messagePreparator = mimeMessage -> {
            final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("qrgomanager@gmail.com");
            messageHelper.setTo(recipient);
            messageHelper.setSubject("您的課堂 " + dao.findClassName(announcement.getCs_id()) + " 已新增新的公告");
            newannouncementtemplate.setAtTitle(at_title);
            newannouncementtemplate.setAtContent(at_content);
            newannouncementtemplate.setCsName(cs_name);
            messageHelper.setText(newannouncementtemplate.getNewMailTemplate(), true);

        };
        try {
            mailSender.send(messagePreparator);
            // System.out.println("sent");
        } catch (final MailException e) {
              //System.out.println(e);
              // runtime exception; compiler will not force you to handle it
          }
     }


}
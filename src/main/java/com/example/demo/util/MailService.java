package com.example.demo.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private JavaMailSender mailSender;
 
    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
 
    public void prepareAndSend(String recipient, String message, int std_id) {
       MimeMessagePreparator messagePreparator = mimeMessage -> {
             MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
             messageHelper.setFrom("qrgomanager@gmail.com");
             messageHelper.setTo(recipient);
             messageHelper.setSubject("您的QRgo系統學號為" + std_id + "的密碼已經變更");
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
}
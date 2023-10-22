package com.yimingliao.mshivebackend.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Calendo
 * @version 1.0
 * @description TODO
 * @date 2023/10/16 18:52
 */
@Component
@Slf4j
public class EmailSendUtil {

    @Value("${constants.server-name}")
    private String serverName;

    //注意包名，不能写成MailSender等
    @Autowired
    private JavaMailSender javaMailSender;

    public String sendOTPMail(String otp, Integer duration, String from, String to) {
        String text = serverName + ": " + otp + "\n" + "This OTP Code is available in "
                + duration + " minutes, please DO NOT expose it to others."
                + " If your self does not operate this event, please ignore it.";
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("OTP Code");
        simpleMailMessage.setText(text);
        //发送邮件
        javaMailSender.send(simpleMailMessage);
        log.info("sent email to: " + to);
        return otp;
    }

    public String sendOTPHtmlMail(String otp, Integer duration, String from, String to, String htmlFilePath) throws MessagingException, IOException {
        //Create Message to Allocate the basic info of Email: form, to, subject
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject("OTP Code");
        String htmlContent = new String(Files.readAllBytes(Paths.get(htmlFilePath)));
        mimeMessageHelper.setText(htmlContent, true);
        javaMailSender.send(mimeMessage);
        log.info("sent email to: " + to);
        return otp;
    }

    public String sendOTPDynamicHtmlMail(String otp, Integer duration, String from, String to, String htmlFilePath) throws MessagingException, IOException {
        String htmlContent = new String(Files.readAllBytes(Paths.get(htmlFilePath)));
        Document jsoupDoc = Jsoup.parse(htmlContent);
        for (Element elementsByClass : jsoupDoc.getElementsByClass("OTP-Code")) {
            System.out.println(elementsByClass);
            if (null != elementsByClass) {
                elementsByClass.text(otp);
            }
        }
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject("OTP Code");

        mimeMessageHelper.setText(jsoupDoc.html(), true);
        javaMailSender.send(mimeMessage);
        log.info("sent email to: " + to);
        return otp;
    }

}

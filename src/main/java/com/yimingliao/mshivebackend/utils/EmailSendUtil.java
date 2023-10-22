package com.yimingliao.mshivebackend.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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

    public String sendOTPDynamicHtmlMail(String otp, Integer duration, String from, String to, String htmlFilePath) throws MessagingException, IOException {
        //Read Template, and Add Customized Variables
        String htmlContent = new String(Files.readAllBytes(Paths.get(htmlFilePath)));
        Document jsoupDoc = Jsoup.parse(htmlContent);
        for (Element elementsByClass : jsoupDoc.getElementsByClass("OTP-Code")) {
            System.out.println(elementsByClass);
            if (null != elementsByClass) {
                elementsByClass.text(otp);
            }
        }
        for (Element elementsByClass : jsoupDoc.getElementsByClass("OTP-duration")) {
            System.out.println(elementsByClass);
            if (null != elementsByClass) {
                elementsByClass.text(duration + " MINUTES");
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

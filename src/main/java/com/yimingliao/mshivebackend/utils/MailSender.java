package com.yimingliao.mshivebackend.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author Calendo
 * @version 1.0
 * @description TODO
 * @date 2023/10/16 18:52
 */
@Component
@Slf4j
public class MailSender {

    @Value("${constants.server-name}")
    private String serverName;

    //需要在yml里加上spring: main: allow-bean-definition-overriding: true
    @Autowired
    private JavaMailSender javaMailSender;

    public String sendOTPMail(String otp, String from, String to) {
        String text = serverName + ": " + otp + "\n" + "15分钟内有效，请勿将验证码泄露给他人，如非本人操作请忽略。";
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("NO_REPLY_MsHive");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("OTP Code");
        simpleMailMessage.setText(text);
        //发送邮件
        javaMailSender.send(simpleMailMessage);
        log.info("sent to: " + to);
        return otp;
    }

}

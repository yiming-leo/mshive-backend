package com.yimingliao.mshivebackend.service.redis.impl;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.service.redis.IOTPCodeService;
import com.yimingliao.mshivebackend.utils.EmailSendUtil;
import com.yimingliao.mshivebackend.utils.OTPCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Calendo
 * @version 1.0
 * @description OTP code related
 * @date 2023/10/20 22:11
 */

@Service
@Slf4j
public class OTPCodeServiceImpl implements IOTPCodeService {

    @Autowired
    private OTPCode otpCode;

    @Autowired
    private EmailSendUtil emailSendUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public R OTPCodeSenderByEmail(String userHttpIp, Integer length, Integer duration, String from, String to) throws Exception {
        String code = otpCode.generateOTPCode(length);
        String otpCode = emailSendUtil.sendOTPMail(code, duration, from, to);
        //inject validate code (key-value pair) to redis set 10min
        //code==otpCode防止被注入
        if (!Objects.equals(code, otpCode)) {
            return R.success(404, "Something wrong, try again", new Date());
        }
        redisTemplate.opsForValue().set(userHttpIp, otpCode, duration, TimeUnit.MINUTES);
        return R.success(200, "Code Sent", new Date());
    }

    @Override
    public R OTPCodeSenderByHtmlEmail(String userHttpIp, Integer length,
                                      Integer duration, String from, String to, String htmlFilePath)
            throws MessagingException, IOException {
        String code = otpCode.generateOTPCode(length);
        String otpCode = emailSendUtil.sendOTPHtmlMail(code, duration, from, to, htmlFilePath);
        //inject validate code (key-value pair) to redis set 10min
        //code==otpCode防止被注入
        if (!Objects.equals(code, otpCode)) {
            return R.success(404, "Something wrong, try again", new Date());
        }
        redisTemplate.opsForValue().set(userHttpIp, otpCode, duration, TimeUnit.MINUTES);
        return R.success(200, "Code Sent", new Date());
    }

    @Override
    public R OTPCodeSenderByDynamicHtmlEmail(String userHttpIp, Integer length,
                                      Integer duration, String from, String to, String htmlFilePath)
            throws MessagingException, IOException {
        String code = otpCode.generateOTPCode(length);
        String otpCode = emailSendUtil.sendOTPDynamicHtmlMail(code, duration, from, to, htmlFilePath);
        //inject validate code (key-value pair) to redis set 10min
        //code==otpCode防止被注入
        if (!Objects.equals(code, otpCode)) {
            return R.success(404, "Something wrong, try again", new Date());
        }
        redisTemplate.opsForValue().set(userHttpIp, otpCode, duration, TimeUnit.MINUTES);
        return R.success(200, "Code Sent", new Date());
    }

    @Override
    public R OTPCodeValidate(String userHttpIp, String userOTP) throws Exception {
        //Insert User IP and Return Value
        String RedisValue = redisTemplate.opsForValue().get(userHttpIp);
        //validate RedisValue & userOTP
        if ((userOTP != null && RedisValue != null) && userOTP.equals(RedisValue)) {
            return R.success(200, "Validate Success!", new Date());
        } else {
            return R.error(404, "Validate Failed!", new Date());
        }
    }
}

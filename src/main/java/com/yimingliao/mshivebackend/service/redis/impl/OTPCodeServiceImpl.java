package com.yimingliao.mshivebackend.service.redis.impl;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.service.redis.IOTPCodeService;
import com.yimingliao.mshivebackend.utils.MailSender;
import com.yimingliao.mshivebackend.utils.OTPCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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
    private MailSender mailSender;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public R OTPCodeSenderFromEmail(String userHttpIp, Integer length, Integer duration, String from, String to) {
        //you can get IP and sth related to browser to generate a list of dashboards in front-end
        //---------------------------IP SERVICE INJECT-------------------------------
        String code = otpCode.generateOTPCode(length);
        String otpCode = mailSender.sendOTPMail(code, from, to);
        //inject validate code (key-value pair) to redis set 10min
        //code==otpCode防止被注入
        if (Objects.equals(code, otpCode)) {
            redisTemplate.opsForValue().set(userHttpIp, otpCode, duration, TimeUnit.MINUTES);
            return R.success(200, "Code Sent", new Date());
        } else {
            return R.success(404, "Something wrong, try again", new Date());
        }
    }

    @Override
    public R OTPCodeValidate(String userHttpIp, String userOTP) {
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

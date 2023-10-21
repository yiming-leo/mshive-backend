package com.yimingliao.mshivebackend.controller.redis;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.service.redis.impl.OTPCodeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author Calendo
 * @version 1.0
 * @description 使用Redis进行管理登录注册业务的控制器
 * @date 2023/10/21 14:55
 */
@RestController
@Slf4j
public class LoginRegisterController {

    @Autowired
    private OTPCodeServiceImpl otpCodeService;

    //Send OTP Code from email
    @GetMapping("/otp_send_email")
    public R sendOTPCodeByEmail(HttpServletRequest request, @RequestParam(name = "emailAddress") String to) {
        String from = "2479711422@qq.com";
        Integer length = 6;
        Integer duration = 10;
        //String remoteAddr = request.getRemoteAddr();
        String remoteHost = request.getRemoteHost();
        int remotePort = request.getRemotePort();
        log.info("Connect to: " + remoteHost + " " + remotePort);
        try {
            return otpCodeService.OTPCodeSenderByEmail(remoteHost, length, duration, from, to);
        } catch (Exception e) {
            //防止Redis项目没启动所以Try
            log.error(e.getMessage());
            return R.error(500, e.getMessage(), new Date());
        }
    }

}

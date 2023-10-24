package com.yimingliao.mshivebackend.controller.version1.otp;

import com.yimingliao.mshivebackend.common.R;
import com.yimingliao.mshivebackend.service.redis.impl.OTPCodeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/v1/otp")
public class OTPController {

    @Autowired
    private OTPCodeServiceImpl otpCodeService;

    //Send OTP Code from email

    @GetMapping("/send_email")
    public R sendOTPCodeByDynamicHtmlEmail(HttpServletRequest request, @RequestParam(name = "email_address") String to) {
        String from = "2479711422@qq.com";
        String htmlFilePath = "src/main/resources/static/mail_template/otp_send/otp.html";
        Integer length = 6;
        Integer duration = 15;
        String remoteAddr = request.getRemoteAddr();
        log.info("Connect to: " + remoteAddr);
        try {
            return otpCodeService.OTPCodeSenderByDynamicHtmlEmail(remoteAddr, length, duration, from, to, htmlFilePath);
        } catch (Exception e) {
            //防止Redis项目没启动所以Try
            log.error(e.getMessage());
            return R.error(500, e.getMessage(), new Date());
        }
    }

    @GetMapping("/validate_email")
    public R validateOTPCodeByEmail(HttpServletRequest request, @RequestParam(name = "otp_code") String otp) {
        String remoteAddr = request.getRemoteAddr();
        log.info("Connect to: " + remoteAddr);
        try {
            return otpCodeService.OTPCodeValidate(remoteAddr, otp);
        } catch (Exception e) {
            //防止Redis项目没启动所以Try
            log.error(e.getMessage());
            return R.error(500, e.getMessage(), new Date());
        }
    }

}

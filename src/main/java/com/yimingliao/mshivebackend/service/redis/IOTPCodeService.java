package com.yimingliao.mshivebackend.service.redis;

import com.yimingliao.mshivebackend.common.R;

/**
 * @author Calendo
 * @version 1.0
 * @description OTP code related
 * @date 2023/10/20 22:10
 */
public interface IOTPCodeService {
    R OTPCodeSenderByDynamicHtmlEmail(Integer length, Integer duration, String from, String to, String htmlFilePath) throws Exception;

    R OTPCodeValidate(String emailAddr, String userOTP) throws Exception;

}

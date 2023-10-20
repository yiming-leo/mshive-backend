package com.yimingliao.mshivebackend.service.redis;

import com.yimingliao.mshivebackend.common.R;

/**
 * @author Calendo
 * @version 1.0
 * @description OTP code related
 * @date 2023/10/20 22:10
 */
public interface IOTPCodeService {

    R OTPCodeSenderFromEmail(String userHttpIdentity, Integer length, Integer duration, String from, String to);

    R OTPCodeValidate(String userHttpIp, String userOTP);

}

package com.yimingliao.mshivebackend.service.redis;

import com.yimingliao.mshivebackend.common.R;

/**
 * @author Calendo
 * @version 1.0
 * @description OTP code related
 * @date 2023/10/20 22:10
 */
public interface IOTPCodeService {

    R OTPCodeSenderByEmail(String userHttpIdentity, Integer length, Integer duration, String from, String to) throws Exception;

    R OTPCodeValidate(String userHttpIp, String userOTP) throws Exception;

}

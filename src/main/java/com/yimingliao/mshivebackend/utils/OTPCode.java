package com.yimingliao.mshivebackend.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author Calendo
 * @version 1.0
 * @description TODO
 * @date 2023/10/16 19:10
 */
@Component
public class OTPCode {

    /**
    * @param length 4~16位随机字母+数字码
    * @return java.lang.String 生成的随机数
    * @author Calendo
    * @date 2023/10/16 19:25
    */
    public String generateOTPCode(int length) {
        if (length > 16) {
            length = 16;
        } else if (length < 4) {
            length = 4;
        }
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(chars[random.nextInt(chars.length)]);
        }
        return code.toString();
    }
}

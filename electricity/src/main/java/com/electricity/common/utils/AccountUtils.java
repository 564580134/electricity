package com.electricity.common.utils;

import java.util.Random;

/**
 * @Description: 随机账号生成
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

public class AccountUtils {

    private static final int LENGTH = 7;

    public static String getRandomAccount() {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < LENGTH; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }
}

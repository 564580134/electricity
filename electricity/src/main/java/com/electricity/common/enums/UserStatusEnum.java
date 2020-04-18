package com.electricity.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 用户状态
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Getter
@AllArgsConstructor
public enum UserStatusEnum {

    /**
     * 账号正常,可登录
     */
    ALLOW_LOGIN(0, "账号正常,可登录"),
    /**
     * 账号已被平台禁止登录,请联系管理员
     */
    NO_LOGIN(1, "账号已被平台禁止登录,请联系管理员"),
    /**
     * 账号未删除,可登录
     */
    NOT_DELETE(0, "账号未删除,可登录"),
    /**
     * 账号已被平台删除,无法登录,请联系管理员
     */
    HAVE_DELETE(1, "账号已被平台删除,无法登录,请联系管理员");

    private final Integer code;
    private final String desc;
}

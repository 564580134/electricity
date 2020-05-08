package com.electricity.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @Description: Response枚举
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Getter
@AllArgsConstructor
public enum  ResponseEnum {

    /**
     * 响应成功
     */
    SUCCESS(200, "成功"),
    /**
     * 响应失败
     */
    ERROR(400, "失败"),
    /**
     * 系统异常
     */
    EXCEPTION(500, "系统异常"),
    /**
     * 未登录，需要登录
     */
    NEED_LOGIN(401, "未登录,需要登录"),
    /**
     * 登录信息已过期
     */
    OVERDUE_LOGIN(402, "登录信息已过期"),
    /**
     * 无权限访问
     */
    NOT_PERMISSION(403, "无访问权限");

    private final Integer code;
    private final String desc;
}

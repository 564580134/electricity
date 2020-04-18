package com.electricity.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;



/**
 * @Description: 异常信息枚举
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Getter
@AllArgsConstructor
public enum ExceptionEnum {

    /**
     * Mysql新增异常
     */
    MYSQL_INSERT_EXCEPTION(10, "Mysql新增异常"),
    /**
     * Mysql修改异常
     */
    MYSQL_UPDATE_EXCEPTION(11, "Mysql修改异常"),
    /**
     * Mysql删除异常
     */
    MYSQL_DELETE_EXCEPTION(12, "Mysql删除异常"),
    /**
     * 添加用户角色异常
     */
    INSERT_USER_ROLE_EXCEPTION(13, "添加用户角色异常"),
    /**
     * 删除用户角色异常
     */
    DELETE_USER_ROLE_EXCEPTION(14, "删除用户角色异常"),
    /**
     * Excel导入数据异常
     */
    EXCEL_CHECK_DATE_EXCEPTION(15, "Excel导入数据异常"),
    /**
     * Excel数据重复导入
     */
    EXCEL_DATA_REPEAT_IMPORT_EXCEPTION(16, "Excel数据重复导入"),
    /**
     * FTP文件上传异常
     */
    FTP_UPLOAD_FILE_EXCEPTION(17, "FTP文件上传异常"),
    /**
     * 数据获取异常
     */
    MYSQL_QUERY_EXCEPTION(18, "数据获取异常");


    private Integer code;

    private String message;
}

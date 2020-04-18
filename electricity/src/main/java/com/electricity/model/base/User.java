package com.electricity.model.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description: User
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "user")
public class User implements Serializable {

    /**
     * 账号id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 账号是否可用 0:可用 1:禁用
     */
    private Integer status;

    /**
     * 账号是否删除 0:未删除 1:已删除
     */
    private Integer isDelete;

    /**
     * 组织id
     */
    private String organizationId;

    /**
     * 密码(MD5加密)
     */
    @JsonIgnore
    private String password;

    /**
     * 盐值
     */
    @JsonIgnore
    private String salt;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GTM+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GTM+8")
    private Date updateTime;

    /**
     * 用户角色
     */
    private List<Role> roles;

    /**
     * 所属组织
     */
    private Organization organization;

    /**
     * 用户权限
     */
    private List<Permission> userPermissionList;

    /**
     * 头像
     */
    @Transient
    private String headImg;

    @Transient
    private String organizationName;
}

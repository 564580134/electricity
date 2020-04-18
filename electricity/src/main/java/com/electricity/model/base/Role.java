package com.electricity.model.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * @Description: Role
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "role")
public class Role implements Serializable {

    /**
     * 角色Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色标题
     */
    private String roleTitle;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 所属组织
     */
    private String organizationId;

    /**
     * 角色权限
     */
    private List<Permission> permissionList;

    /**
     * 组织信息
     */
    private Organization organization;

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
}

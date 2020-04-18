package com.electricity.model.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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

/**
 * @Description: Permission
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@ApiModel(value = "permission", description = "权限对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "permission")
public class Permission implements Serializable {

    /**
     * 权限id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "权限id")
    private Integer permissionId;

    /**
     * 权限名称
     */
    @ApiModelProperty(value = "权限名称")
    private String permissionName;

    /**
     * 权限类型 1:目录 2:菜单 3:按钮
     */
    @ApiModelProperty(value = "权限类型 0:一级目录 1:二级目录 2:菜单 3:按钮")
    private Integer permissionType;

    /**
     * 权限值
     */
    @ApiModelProperty(value = "权限值")
    private String permissionValue;

    /**
     * 权限状态 0:禁止 1:正常
     */
    @ApiModelProperty(value = "权限状态 0:禁止 1:正常")
    private Integer permissionStatus;

    /**
     * 所属上级
     */
    @ApiModelProperty(value = "所属上级")
    private Integer superiorId;

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

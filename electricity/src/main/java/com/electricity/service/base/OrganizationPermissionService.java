package com.electricity.service.base;


import com.electricity.model.base.Permission;

import java.util.List;

/**
 * @Description: OrganizationPermissionService
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/
public interface OrganizationPermissionService {


    /**
     * 添加组织权限
     *
     * @param organizationId 组织Id
     * @param permissionIds  权限Id
     */
    void insertRolePermission(String organizationId, Integer[] permissionIds);

    /**
     * 根据组织编号查询权限信息
     *
     * @param organizationId 组织编号
     * @return list
     */
    List<Permission> selectOrgPermissionByOrgId(String organizationId);
}

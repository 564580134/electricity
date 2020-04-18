package com.electricity.service.base;

/**
 * @Description: RolePermissionService
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/
public interface RolePermissionService {

    /**
     * 添加角色权限
     *
     * @param roleId        角色Id
     * @param permissionIds 权限Id
     */
    void insertRolePermission(Integer roleId, Integer[] permissionIds);
}

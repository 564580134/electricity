package com.electricity.service.base;

/**
 * @Description: UserPermissionService
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/
public interface UserPermissionService {

    /**
     * 添加用户权限
     *
     * @param userId        用户id
     * @param permissionIds 权限id
     */
    void insertUserPermission(Integer userId, Integer[] permissionIds);
}

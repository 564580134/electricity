package com.electricity.service.base;

/**
 * @Description: UserRoleService
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/
public interface UserRoleService {

    /**
     * 添加用户角色
     *
     * @param userId  用户id
     * @param roleIds 角色id
     */
    void insertUserRole(Integer userId, Integer[] roleIds);
}

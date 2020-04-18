package com.electricity.service.base;

import com.electricity.model.base.Role;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description: RoleService
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

public interface RoleService {

    /**
     * 判断角色是否存在
     *
     * @param example 条件
     * @return true: 存在 false: 不存在
     */
    boolean isExist(Example example);

    /**
     * 查询角色根据角色Id
     *
     * @param roleId 角色Id
     * @return role
     */
    Role findRoleByRoleId(Integer roleId);

    /**
     * 查询角色根据条件
     *
     * @param roleTitle      角色标题
     * @param organizationId 组织id
     * @return list
     */
    List<Role> findRoleByCondition(String roleTitle, String organizationId);

    /**
     * 查询角色根据组织Id
     *
     * @param organizationId 组织Id
     * @return list
     */
    List<Role> findRoleByOrganizationId(Integer organizationId);

    /**
     * 添加角色
     *
     * @param role 角色信息
     */
    void insertRole(Role role);

    /**
     * 修改角色
     *
     * @param role 角色信息
     */
    void updateRole(Role role);

    /**
     * 删除角色
     *
     * @param roleIds 角色Id数组
     */
    void deleteRole(Integer[] roleIds);

}

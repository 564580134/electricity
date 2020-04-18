package com.electricity.mapper.base;

import com.electricity.model.base.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * @Description: RoleMapper
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/


public interface RoleMapper extends Mapper<Role> {

    /**
     * 查询角色根据条件
     *
     * @param roleTitle      角色标题
     * @param organizationId 组织id
     * @return list
     */
    List<Role> findRoleByCondition(@Param("roleTitle") String roleTitle, @Param("organizationId") String organizationId);

    /**
     * 查询角色根据角色Id
     *
     * @param roleId 角色Id
     * @return role
     */
    Role findRoleByRoleId(@Param("roleId") Integer roleId);

    /**
     * 查询角色根据组织Id
     *
     * @param organizationId 组织Id
     * @return list
     */
    List<Role> findRoleByOrganizationId(@Param("organizationId") Integer organizationId);

    /**
     * 添加角色权限
     *
     * @param roleId       角色Id
     * @param permissionId 权限Id
     * @return int
     */
    int insertRolePermission(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

    /**
     * 删除角色权限
     *
     * @param roleId       角色Id
     * @param permissionId 权限Id
     * @return int
     */
    int deleteRolePermission(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);

}


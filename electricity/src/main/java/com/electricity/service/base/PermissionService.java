package com.electricity.service.base;

import com.electricity.model.base.Permission;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description: PermissionService
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

public interface PermissionService {

    /**
     * 判断权限是否存在
     *
     * @param example 条件
     * @return true: 存在 false: 不存在
     */
    boolean isExist(Example example);

    /**
     * 查询全部权限
     *
     * @return list
     */
    List<Permission> findPermissionAll();

    /**
     * 查询权限根据条件
     *
     * @param example 条件
     * @return list
     */
    List<Permission> findPermissionByExample(Example example);

    /**
     * 查询权限根据条件
     *
     * @param permissionName 权限名
     * @param userId         权限Id
     * @return
     */
    List<Permission> findPermission(String permissionName, Integer userId);

    /**
     * 新增权限
     *
     * @param permission 权限信息
     */
    void insertPermission(Permission permission);

    /**
     * 修改权限
     *
     * @param permission 权限信息
     */
    void updatePermission(Permission permission);

    /**
     * 删除权限
     *
     * @param permissionIds 权限Id数组
     */
    void deletePermission(Integer[] permissionIds);


    /**
     * 根据角色编号查询权限信息
     *
     * @param roleId 角色编号
     */
    List<Permission> findPermissionByRoleId(Integer roleId);

    /**
     * 获取权限树
     */
    List<Permission> permissionZTree();
}

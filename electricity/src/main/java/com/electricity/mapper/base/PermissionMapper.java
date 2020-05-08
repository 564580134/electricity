package com.electricity.mapper.base;

import com.electricity.model.base.Permission;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Description: PermissionMapper
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/


public interface PermissionMapper extends Mapper<Permission> {

    /**
     * 查询权限根据Id
     *
     * @param permissionId 权限Id
     * @return list
     */
    List<Integer> findPermissionById(@Param("permissionId") Integer permissionId);


    /**
     * 权限查询权限
     *
     * @param map 条件
     * @return
     */
    List<Permission> findPermission(Map<String, Object> map);

    /**
     * 根据角色编号查询权限
     *
     * @param roleId 角色编号
     * @return
     */
    List<Permission> findPermissionByRoleId(Integer roleId);
}

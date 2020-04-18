package com.electricity.mapper.base;


import com.electricity.model.base.Organization;
import com.electricity.model.base.Permission;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Description: OrganizationMapper
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/
public interface OrganizationMapper extends Mapper<Organization> {

    /**
     * 添加组织权限
     *
     * @param organizationId 组织Id
     * @param permissionId   权限Id
     * @return int
     */
    int insertOrganizationPermission(@Param("organizationId") String organizationId, @Param("permissionId") Integer permissionId);

    /**
     * 删除组织权限
     *
     * @param organizationId 组织Id
     * @param permissionId   权限Id
     * @return int
     */
    int deleteOrganizationPermission(@Param("organizationId") String organizationId, @Param("permissionId") Integer permissionId);

    /**
     * 根据组织编号查询权限
     *
     * @param organizationId 组织编号
     * @return list
     */
    List<Permission> selectOrgPermissionByOrgId(String organizationId);

    /**
     * 查询组织
     *
     * @param organizationName 组织名称
     * @param userId           当前用户
     * @param organizationId   组织编号
     * @return
     */
    List<Organization> findOrganizations(String organizationName, Integer userId, String organizationId);
}

package com.electricity.serviceimpl.base;


import com.electricity.common.enums.ExceptionEnum;
import com.electricity.common.exception.CustomException;
import com.electricity.mapper.base.OrganizationMapper;
import com.electricity.model.base.Permission;
import com.electricity.service.base.OrganizationPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: UserRoleMapper
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class OrganizationPermissionServiceImpl implements OrganizationPermissionService {

    @Autowired
    private OrganizationMapper organizationMapper;


    @Override
    public void insertRolePermission(String organizationId, Integer[] permissionIds) {
        // 删除角色对应的所有权限
        organizationMapper.deleteOrganizationPermission(organizationId, null);
        // 重新新增角色分配的权限
        for (Integer permissionId : permissionIds) {
            int result = organizationMapper.insertOrganizationPermission(organizationId, permissionId);
            if (result == 0) {
                throw new CustomException(ExceptionEnum.MYSQL_INSERT_EXCEPTION);
            }
        }

    }

    @Override
    public List<Permission> selectOrgPermissionByOrgId(String organizationId) {
        return organizationMapper.selectOrgPermissionByOrgId(organizationId);
    }
}

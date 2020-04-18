package com.electricity.serviceimpl.base;


import com.electricity.common.enums.ExceptionEnum;
import com.electricity.common.exception.CustomException;
import com.electricity.mapper.base.RoleMapper;
import com.electricity.service.base.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: RolePermissionServiceImpl
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Service
@Transactional(rollbackFor = Exception.class)
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void insertRolePermission(Integer roleId, Integer[] permissionIds) {
        // 删除角色对应的所有权限
        roleMapper.deleteRolePermission(roleId, null);
        // 重新新增角色分配的权限
        for (Integer permissionId : permissionIds) {
            int result = roleMapper.insertRolePermission(roleId, permissionId);
            if (result == 0) {
                throw new CustomException(ExceptionEnum.MYSQL_INSERT_EXCEPTION);
            }
        }
    }
}

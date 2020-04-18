package com.electricity.serviceimpl.base;

import com.electricity.common.enums.ExceptionEnum;
import com.electricity.common.exception.CustomException;
import com.electricity.mapper.base.PermissionMapper;
import com.electricity.mapper.base.RoleMapper;
import com.electricity.model.base.Permission;
import com.electricity.service.base.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: PermissionServiceImpl
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean isExist(Example example) {
        Permission permission = permissionMapper.selectOneByExample(example);
        // true: 存在 false: 不存在
        return permission != null;
    }

    @Override
    public List<Permission> findPermissionAll() {
        return permissionMapper.selectAll();
    }

    @Override
    public List<Permission> findPermissionByExample(Example example) {
        return permissionMapper.selectByExample(example);
    }

    @Override
    public List<Permission> findPermission(String permissionName, Integer userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("permissionName", permissionName);
        map.put("userId", userId);
        return permissionMapper.findPermission(map);
    }

    @Override
    public void insertPermission(Permission permission) {
        int result = permissionMapper.insertSelective(permission);
        if (result == 0) {
            throw new CustomException(ExceptionEnum.MYSQL_INSERT_EXCEPTION);
        }
    }

    @Override
    public void updatePermission(Permission permission) {
        int result = permissionMapper.updateByPrimaryKeySelective(permission);
        if (result == 0) {
            throw new CustomException(ExceptionEnum.MYSQL_UPDATE_EXCEPTION);
        }
    }

    @Override
    public void deletePermission(Integer[] permissionIds) {
        for (Integer permissionId : permissionIds) {
            List<Integer> permissionIdList = permissionMapper.findPermissionById(permissionId);
            if (permissionIdList != null) {
                for (Integer id : permissionIdList) {
                    permissionMapper.deleteByPrimaryKey(id);
                }
            }
            permissionMapper.deleteByPrimaryKey(permissionId);
            roleMapper.deleteRolePermission(null, permissionId);
        }
    }
}


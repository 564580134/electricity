package com.electricity.serviceimpl.base;

import com.electricity.common.enums.ExceptionEnum;
import com.electricity.common.exception.CustomException;
import com.electricity.mapper.base.PermissionMapper;
import com.electricity.mapper.base.RoleMapper;
import com.electricity.model.base.Permission;
import com.electricity.service.base.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @Description: PermissionServiceImpl
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    final PermissionMapper permissionMapper;

    final RoleMapper roleMapper;

    public PermissionServiceImpl(PermissionMapper permissionMapper, RoleMapper roleMapper) {
        this.permissionMapper = permissionMapper;
        this.roleMapper = roleMapper;
    }

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

    @Override
    public List<Permission> findPermissionByRoleId(Integer roleId) {
        return permissionMapper.findPermissionByRoleId(roleId);
    }


    @Override
    public List<Permission> permissionZTree() {
        // 获取所有的权限数据
        List<Permission> permissions = permissionMapper.selectAll();
        // 定义 目录、菜单、按钮,并放入对应数据
        List<Permission> directoryLists = new ArrayList<>();
        List<Permission> menuLists = new ArrayList<>();
        List<Permission> buttonLists = new ArrayList<>();
        permissions.forEach((Permission permission) -> {
            if (permission.getPermissionType() == 1)
                directoryLists.add(permission);
        });
        permissions.forEach((Permission permission) -> {
            if (permission.getPermissionType() == 2)
                menuLists.add(permission);
        });

        // 循环遍历第一层 目录
        for (int i = 0; i < directoryLists.size(); i++) {
            directoryLists.get(i).setPermissionList(GetMenuListByDirectoryId(directoryLists.get(i).getPermissionId(),menuLists));
        }
        return directoryLists;
    }

    public List<Permission> GetMenuListByDirectoryId(Integer directoryId, List<Permission> permissions) {
        List<Permission> menuList = new ArrayList<>();
        permissions.forEach(
                permission -> {
                    if (permission.getSuperiorId() == directoryId) {
                        menuList.add(permission);
                    }
                }
        );
        log.info("目录:" + directoryId + ",菜单:" + menuList);
        return menuList;
    }

}


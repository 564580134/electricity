package com.electricity.serviceimpl.base;


import com.electricity.common.enums.ExceptionEnum;
import com.electricity.common.exception.CustomException;
import com.electricity.mapper.base.RoleMapper;
import com.electricity.mapper.base.UserMapper;
import com.electricity.model.base.Role;
import com.electricity.service.base.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description: RoleServiceImpl
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;

    @Autowired
    UserMapper userMapper;


    @Override
    public boolean isExist(Example example) {
        Role role = roleMapper.selectOneByExample(example);
        // true: 存在 false: 不存在
        return role != null;
    }


    @Override
    public List<Role> findRoleByCondition(String roleTitle, String organizationId) {
        return roleMapper.findRoleByCondition(roleTitle, organizationId);
    }

    @Override
    public void insertRole(Role role) {
        int result = roleMapper.insertSelective(role);
        if (result == 0) {
            throw new CustomException(ExceptionEnum.MYSQL_INSERT_EXCEPTION);
        }
    }

    @Override
    public void updateRole(Role role) {
        int result = roleMapper.updateByPrimaryKeySelective(role);
        if (result == 0) {
            throw new CustomException(ExceptionEnum.MYSQL_UPDATE_EXCEPTION);
        }
    }

    @Override
    public void deleteRole(Integer[] roleIds) {
        for (Integer roleId : roleIds) {
            int resultRole = roleMapper.deleteByPrimaryKey(roleId);
            if (resultRole == 0) {
                throw new CustomException(ExceptionEnum.MYSQL_DELETE_EXCEPTION);
            } else {
                roleMapper.deleteRolePermission(roleId, null);
                userMapper.deleteUserRoleByRoleId(roleId);
            }
        }
    }

    @Override
    public List<Role> selectRoleAll() {
        return roleMapper.selectAll();
    }
}

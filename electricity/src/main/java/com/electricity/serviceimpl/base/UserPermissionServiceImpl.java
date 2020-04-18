package com.electricity.serviceimpl.base;

import com.electricity.common.enums.ExceptionEnum;
import com.electricity.common.exception.CustomException;
import com.electricity.mapper.base.UserMapper;
import com.electricity.service.base.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: UserPermissionServiceImpl
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Service
@Transactional(rollbackFor = Exception.class)
public class UserPermissionServiceImpl implements UserPermissionService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void insertUserPermission(Integer userId, Integer[] permissionIds) {
        // 删除用户对应的所有权限
        userMapper.deleteUserPermission(userId, null);
        // 重新新增用户分配的权限
        for (Integer permissionId : permissionIds) {
            int result = userMapper.insertUserPermission(userId, permissionId);
            if (result == 0) {
                throw new CustomException(ExceptionEnum.MYSQL_INSERT_EXCEPTION);
            }
        }
    }
}

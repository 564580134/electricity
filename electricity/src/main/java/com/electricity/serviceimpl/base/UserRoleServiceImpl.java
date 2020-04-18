package com.electricity.serviceimpl.base;


import com.electricity.common.enums.ExceptionEnum;
import com.electricity.common.exception.CustomException;
import com.electricity.mapper.base.UserMapper;
import com.electricity.service.base.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: UserRoleServiceImpl
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/
@Service
@Transactional(rollbackFor = Exception.class)
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void insertUserRole(Integer userId, Integer[] roleIds) {
        // 删除用户所有角色
        userMapper.deleteUserRoleByUserId(userId);
        for (Integer roleId : roleIds) {
            // 新增用户角色
            int resultInsertUserRole = userMapper.insertUserRole(userId, roleId);
            if (resultInsertUserRole == 0) {
                throw new CustomException(ExceptionEnum.MYSQL_INSERT_EXCEPTION);
            }
        }
    }
}

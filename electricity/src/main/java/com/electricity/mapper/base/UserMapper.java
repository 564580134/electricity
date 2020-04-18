package com.electricity.mapper.base;

import com.electricity.model.base.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;
/**
 * @Description: UserMapper
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

public interface UserMapper extends Mapper<User> {

    /**
     * 查询用户根据Id
     *
     * @param userId 用户Id
     * @return user
     */
    User findUserById(@Param("userId") Integer userId);

    /**
     * 查询用户根据账号
     *
     * @param account 账号
     * @param userId  userId
     * @return user
     */
    User findUserByAccount(@Param("account") String account, @Param("userId") Integer userId);

    /**
     * 查询用户根据条件
     *
     * @param userName 用户名
     * @return list
     */
    List<User> findUserByCondition(@Param("userName") String userName, @Param("userId") Integer userId, @Param("organizationId") String organizationId);


    /**
     * *新增用户权限
     *
     * @param userId       用户id
     * @param permissionId 权限id
     * @return int
     */
    int insertUserPermission(@Param("userId") Integer userId, @Param("permissionId") Integer permissionId);

    /**
     * 删除用户权限
     *
     * @param userId       用户id
     * @param permissionId 权限id
     */
    void deleteUserPermission(@Param("userId") Integer userId, @Param("permissionId") Integer permissionId);

    /**
     * 添加用户角色
     *
     * @param userId 用户Id
     * @param roleId 角色Id
     * @return int
     */
    int insertUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    /**
     * 删除用户角色根据用户Id
     *
     * @param userId 用户Id
     * @return int
     */
    int deleteUserRoleByUserId(@Param("userId") Integer userId);


    /**
     * 删除用户角色根据角色Id
     *
     * @param roleId 角色Id
     * @return int
     */
    int deleteUserRoleByRoleId(@Param("roleId") Integer roleId);

    /**
     * 查询用户信息
     *
     * @param map 查询条件
     * @return list
     */
    List<User> findUserByExample(Map<String, Object> map);

    /**
     * 返回用户数量
     *
     * @param map 条件
     * @return int
     */
    Integer findUserCountByExample(Map<String, Object> map);
}

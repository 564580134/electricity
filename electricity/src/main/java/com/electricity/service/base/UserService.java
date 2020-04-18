package com.electricity.service.base;

import com.electricity.model.base.User;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @Description: UserService
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

public interface UserService {

    /**
     * 查询用户根据Id
     *
     * @param userId 用户Id
     * @return user
     */
    User findUserById(Integer userId);

    /**
     * 查询用户根据账号
     *
     * @param account 账号
     * @param userId  用户Id
     * @return user
     */
    User findUserByAccount(String account, Integer userId);

    /**
     * 查询用户根据条件
     *
     * @param example 条件数据集
     * @return list
     */
    List<User> findUserByExample(Example example);

    /**
     * 判断用户是否存在
     *
     * @param account 账号
     * @param userId  用户Id
     * @return true：存在 false：不存在
     */
    boolean isExist(String account, Integer userId);

    /**
     * 验证手机号是否正确
     *
     * @param phone 手机号
     */
    void checkPhone(String phone);

    /**
     * 新增用户
     *
     * @param userName       用户名称
     * @param password       密码
     * @param phone          电话
     * @param email          邮箱
     * @param sex            性别
     * @param organizationId 组织id
     */
    void insertUser(String userName, String password, String phone, String email, String sex, String organizationId);

    /**
     * 编辑用户
     *
     * @param userId         用户id
     * @param userName       用户名称
     * @param phone          电话
     * @param email          邮箱
     * @param sex            性别
     * @param organizationId 组织id
     */
    void updateUser(Integer userId, String userName, String phone, String email, String sex, String organizationId, Integer status);

    /**
     * 删除用户(逻辑删除)
     *
     * @param user 用户信息
     */
    void deleteUser(User user);

    /**
     * 查询商户数据
     *
     * @param map 条件
     * @return list
     */
    List<User> findUserByConditions(Map<String, Object> map);

    /**
     * 查询商户数量
     *
     * @param map 条件
     * @return int
     */
    Integer findUserCountByExample(Map<String, Object> map);
}

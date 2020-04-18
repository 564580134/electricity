package com.electricity.serviceimpl.base;

import com.electricity.common.enums.ExceptionEnum;
import com.electricity.common.exception.CustomException;
import com.electricity.common.utils.AccountUtils;
import com.electricity.common.utils.Md5Utils;
import com.electricity.mapper.base.UserMapper;
import com.electricity.model.base.User;
import com.electricity.service.base.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: UserServiceImpl
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private static final int PHONE_NUM_LENGTH = 11;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserById(Integer userId) {
        User user = userMapper.findUserById(userId);
        return user;
    }

    @Override
    public User findUserByAccount(String account, Integer userId) {
        User user = userMapper.findUserByAccount(account, userId);
        return user;
    }

    @Override
    public List<User> findUserByExample(Example example) {
        return userMapper.selectByExample(example);

    }

    @Override
    public boolean isExist(String account, Integer userId) {
        User user = findUserByAccount(account, userId);
        // true: 存在 false: 不存在
        return user != null;
    }

    @Override
    public void insertUser(String userName, String password, String phone, String email, String sex, String organizationId) {
        String account;
        User user;
        if (isExist(phone, null)) {
            throw new CustomException(userName + "，联系电话:" + phone + " 已存在");
        }
        // 手机号正则校验
        checkPhone(phone);

        do {
            // 随机生成账号
            account = AccountUtils.getRandomAccount();
            // 根据生成的账号查找用户
            user = findUserByAccount(account, null);
        } while (user != null);

        // 随机生成盐值
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        password = Md5Utils.encryptPassword(password, salt);
        // 创建User对象
        user = new User()
                .setAccount(account)
                .setUserName(userName)
                .setPassword(password)
                .setSalt(salt)
                .setSex(sex)
                .setPhone(phone)
                .setEmail(email)
                .setOrganizationId(organizationId)
                .setStatus(0)
                .setIsDelete(0);
        //判断数据库非空字段
        if (null != userName && null != password && null != user.getSex() && null != phone) {
            // 存入数据库
            int resultUser = userMapper.insertSelective(user);
            if (resultUser == 0) {
                // 添加失败, 抛出异常
                throw new CustomException(ExceptionEnum.MYSQL_INSERT_EXCEPTION);
            }
        }
    }

    @Override
    public void checkPhone(String phone) {
        // 手机号正则表达式
        String phoneReg = "^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$";
        //数据null校验
        if (null != phone && !phone.isEmpty()) {
            // 手机号正则校验
            if (phone.length() != PHONE_NUM_LENGTH) {
                throw new CustomException("手机号应为11位数");
            } else {
                Pattern p = Pattern.compile(phoneReg);
                Matcher m = p.matcher(phone);
                boolean isMatch = m.matches();
                if (!isMatch) {
                    throw new CustomException("请填入正确的手机号");
                }
            }
        }
    }

    @Override
    public void updateUser(Integer userId, String userName, String phone, String email, String sex, String organizationId, Integer status) {
        User user = new User()
                .setUserId(userId)
                .setUserName(userName)
                .setPhone(phone)
                .setEmail(email)
                .setSex(sex)
                .setStatus(status)
                .setOrganizationId(organizationId);
        int userResult = userMapper.updateByPrimaryKeySelective(user);
        if (userResult == 0) {
            // 修改失败，抛出异常
            throw new CustomException(ExceptionEnum.MYSQL_UPDATE_EXCEPTION);
        }
    }

    @Override
    public void deleteUser(User user) {
        int result = userMapper.updateByPrimaryKeySelective(user);
        if (result == 0) {
            // 修改失败,抛出异常
            throw new CustomException(ExceptionEnum.MYSQL_UPDATE_EXCEPTION);
        }
    }



    @Override
    public List<User> findUserByConditions(Map<String, Object> map) {
        return userMapper.findUserByExample(map);
    }

    @Override
    public Integer findUserCountByExample(Map<String, Object> map) {
        return userMapper.findUserCountByExample(map);
    }


}

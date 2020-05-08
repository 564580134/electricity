package com.electricity.controller.base;


import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import com.electricity.model.base.User;
import com.electricity.service.base.UserPermissionService;
import com.electricity.service.base.UserRoleService;
import com.electricity.service.base.UserService;
import com.github.pagehelper.PageInfo;
import com.electricity.common.annotation.RequiresPermissions;
import com.electricity.common.enums.UserStatusEnum;
import com.electricity.common.exception.ServerResponse;
import com.electricity.common.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: UserController
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Api(tags = "用户账号模块")
@RestController
@RequestMapping("/user")
public class UserController {

    final UserService userService;

    final UserRoleService userRoleService;

    final UserPermissionService userPermissionService;

    public UserController(UserService userService, UserRoleService userRoleService, UserPermissionService userPermissionService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.userPermissionService = userPermissionService;
    }

    @ApiOperation(value = "根据条件查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", type = "string"),
            @ApiImplicitParam(name = "pageNum", value = "当前页", type = "string"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", type = "string"),
            @ApiImplicitParam(name = "token", value = "token", type = "string")
    })
    @GetMapping("/findUserByCondition")
    @RequiresPermissions(permissions = {"system_manage:user_manage:user_manage"})
    public ServerResponse findUserByCondition(@RequestParam(value = "userName", required = false) String userName,
                                              @RequestParam(value = "pageNum", defaultValue = "0", required = false) Integer pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                              @RequestParam(value = "token") String token) {
        // 验证参数
        if (token == null) {
            return ServerResponse.createByErrorMessage("未获取到token");
        }
        // 获取当前用户
        User currentUser = JwtUtils.unsign(token, User.class);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("请先登录");
        }
        // 创建查询
        Map<String, Object> map = new HashMap<>();
        if (pageNum > 0) {
            pageNum = (pageNum - 1) * pageSize;
        }
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        if (!StringUtils.isBlank(userName)) {
            map.put("userName", userName + "%");
        }
        // 查询
        List<User> list = userService.findUserByConditions(map);
        // 总数
        Integer total = userService.findUserCountByExample(map);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        pageInfo.setTotal(new Long(total));
        pageInfo.setList(list);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @ApiOperation(value = "查询某个用户是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号/手机号/邮箱", required = true, type = "string"),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, type = "integer")
    })
    @GetMapping("/isExist")
    public ServerResponse<Boolean> isExist(@RequestParam("account") String account,
                                           @RequestParam("userId") Integer userId) {
        // true: 存在 false: 不存在
        return ServerResponse.createBySuccess(userService.isExist(account, userId));
    }

    @ApiOperation(value = "添加用户角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, type = "integer"),
            @ApiImplicitParam(name = "roleIds", value = "角色id", required = true, type = "integer")
    })
    @PostMapping("/insertUserRole")
    @RequiresPermissions(permissions = {"system_manage:user_manage:user_manage:user_role"})
    public ServerResponse insertUserRole(@RequestParam("userId") Integer userId,
                                         @RequestParam("roleIds") Integer[] roleIds) {
        userRoleService.insertUserRole(userId, roleIds);
        return ServerResponse.createBySuccessMessage("用户角色保存成功");
    }


    @ApiOperation(value = "新增用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名称", required = true, type = "string"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, type = "string"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, type = "string"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, type = "string"),
            @ApiImplicitParam(name = "sex", value = "性别", required = true, type = "string"),
            @ApiImplicitParam(name = "organizationId", value = "组织id", required = true, type = "string")
    })
    @PostMapping("/insertUser")
    @RequiresPermissions(permissions = {"system_manage:user_manage:insert"})
    public ServerResponse insertUser(@RequestParam("userName") String userName,
                                     @RequestParam("password") String password,
                                     @RequestParam("phone") String phone,
                                     @RequestParam("email") String email,
                                     @RequestParam("sex") String sex,
                                     @RequestParam("organizationId") String organizationId) {
        userService.insertUser(userName, password, phone, email, sex, organizationId);
        return ServerResponse.createBySuccessMessage("新增用户成功");
    }

    @ApiOperation(value = "编辑用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, type = "integer"),
            @ApiImplicitParam(name = "userName", value = "用户名称", required = true, type = "string"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, type = "string"),
            @ApiImplicitParam(name = "email", value = "邮箱", required = true, type = "string"),
            @ApiImplicitParam(name = "sex", value = "性别", required = true, type = "string"),
            @ApiImplicitParam(name = "organizationId", value = "组织id", required = true, type = "string"),
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, type = "string"),
            @ApiImplicitParam(name = "status", value = "用户状态 0:可用 1:禁用", required = true, type = "string")
    })
    @PostMapping("/updateUser")
    @RequiresPermissions(permissions = {"system_manage:user_manage:update"})
    public ServerResponse updateUser(@RequestParam("userId") Integer userId,
                                     @RequestParam("userName") String userName,
                                     @RequestParam("phone") String phone,
                                     @RequestParam("email") String email,
                                     @RequestParam("sex") String sex,
                                     @RequestParam("organizationId") String organizationId,
                                     @RequestParam("status") String status) {

        userService.updateUser(userId, userName, phone, email, sex, organizationId, (status.equals("可用")) ? 0 : 1);
        return ServerResponse.createBySuccessMessage("修改成功");
    }

    @ApiOperation(value = "删除用户信息")
    @ApiImplicitParam(name = "userIds", value = "用户id", required = true, type = "integer")
    @PostMapping("/deleteUser")
    @RequiresPermissions(permissions = {"system_manage:user_manage:delete"})
    public ServerResponse deleteUser(@RequestParam("userIds") Integer[] userIds) {
        for (Integer userId : userIds) {
            userService.deleteUser(new User().setUserId(userId).setIsDelete(UserStatusEnum.HAVE_DELETE.getCode()));
        }
        return ServerResponse.createBySuccessMessage("删除成功");
    }


    @ApiOperation(value = "根据用户id查询用户信息")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, type = "integer")
    @PostMapping("/findUserById")
    public ServerResponse findUserById(@RequestParam("userId") Integer userId) {


        return ServerResponse.createBySuccess(userService.findUserById(userId));
    }


    //    @ApiOperation(value = "根据用户Id查询用户权限信息")
//    @ApiImplicitParam(name = "userId", value = "用户Id", type = "integer")
//    @GetMapping("/findUserPermissionsByUserId")
//    public ServerResponse findUserPermissionsByUserId(@RequestParam("userId") Integer userId) {
//        return ServerResponse.createBySuccess("查询成功", userService.findUserById(userId).getUserPermissionList());
//    }
    //    @ApiOperation(value = "新增用户权限信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, type = "integer"),
//            @ApiImplicitParam(name = "permissionIds", value = "权限Id", required = true, type = "integer"),
//    })
//    @PostMapping("/insertUserPermission")
//    @RequiresPermissions(permissions = {"system_manage:user_manage:user_manage:user_permission"})
//    public ServerResponse insertRolePermission(@RequestParam("userId") Integer userId,
//                                               @RequestParam("permissionIds") Integer[] permissionIds) {
//        userPermissionService.insertUserPermission(userId, permissionIds);
//        return ServerResponse.createBySuccessMessage("新增用户权限成功");
//    }

}

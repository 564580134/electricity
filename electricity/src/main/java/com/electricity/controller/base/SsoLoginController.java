package com.electricity.controller.base;


import com.electricity.model.base.User;
import com.electricity.service.base.UserService;
import com.electricity.common.enums.UserStatusEnum;
import com.electricity.common.exception.ServerResponse;
import com.electricity.common.utils.JwtUtils;
import com.electricity.common.utils.Md5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * @Description: SsoLoginController
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/

@Api(tags = "登录注册模块")
@Slf4j
@RestController
@RequestMapping("/ssoLogin")
public class SsoLoginController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号/手机号/邮箱", required = true, type = "string"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, type = "string")
    })
    @PostMapping("/login")
    public ServerResponse<User> login(@RequestParam("account") String account,
                                      @RequestParam("password") String password) {
        User user = userService.findUserByAccount(account, null);
        log.info("[用户登录] 当前登录用户: {}", user);
        if (user == null) {
            // 账号不存在
            return ServerResponse.createByErrorMessage("该账号不存在,请重新输入");
        } else if (!user.getPassword().equals(Md5Utils.encryptPassword(password, user.getSalt()))) {
            // 账号或密码错误
            return ServerResponse.createByErrorMessage("您输入的账号或密码错误,请重新输入");
        } else {
            //user.setHeadImg("http://www.ebring.com.cn:9003/vote_images/kebe.jpg");
            return UserServerResponse(user);
        }
    }

    static ServerResponse<User> UserServerResponse(User user) {
        if (user.getStatus().equals(UserStatusEnum.NO_LOGIN.getCode())) {
            // 账号禁止登录
            return ServerResponse.createByErrorMessage(UserStatusEnum.NO_LOGIN.getDesc());
        } else if (user.getIsDelete().equals(UserStatusEnum.HAVE_DELETE.getCode())) {
            // 账号已删除
            return ServerResponse.createByErrorMessage(UserStatusEnum.HAVE_DELETE.getDesc());
        } else {
            // 登录成功
            String token = JwtUtils.sign(new User().setOrganizationId(user.getOrganizationId())
                    .setUserId(user.getUserId())
                    .setRoles(user.getRoles()), 24L * 3600L * 1000L);
            return ServerResponse.createBySuccess(token, user);
        }
    }
}

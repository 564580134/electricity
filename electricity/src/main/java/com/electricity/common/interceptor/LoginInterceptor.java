package com.electricity.common.interceptor;

import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import com.electricity.model.base.User;
import com.electricity.service.base.UserService;
import com.electricity.common.enums.ResponseEnum;
import com.electricity.common.enums.UserStatusEnum;
import com.electricity.common.exception.ServerResponse;
import com.electricity.common.utils.JackSonUtils;
import com.electricity.common.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Description: 登录拦截器
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String requestType = "OPTIONS";

        // 判断是否预请求
        if (requestType.equals(request.getMethod())) {
            return true;
        }
        // 获取Token
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isBlank(authorization)) {
            setServletResponse(response, ServerResponse.createByErrorCodeMessage(ResponseEnum.NEED_LOGIN.getCode(), ResponseEnum.NEED_LOGIN.getDesc()));
            return false;
        }
        // Token转为实体
        User user = JwtUtils.unsign(authorization, User.class);
        if (user == null) {
            setServletResponse(response, ServerResponse.createByErrorCodeMessage(ResponseEnum.OVERDUE_LOGIN.getCode(), ResponseEnum.OVERDUE_LOGIN.getDesc()));
            return false;
        }
        // 查找用户信息
        user = userService.findUserById(user.getUserId());
        if (user.getStatus().equals(UserStatusEnum.NO_LOGIN.getCode())) {
            setServletResponse(response, ServerResponse.createByErrorCodeMessage(UserStatusEnum.NO_LOGIN.getCode(), UserStatusEnum.NO_LOGIN.getDesc()));
            return false;
        }
        if (user.getIsDelete().equals(UserStatusEnum.HAVE_DELETE.getCode())) {
            setServletResponse(response, ServerResponse.createByErrorCodeMessage(UserStatusEnum.HAVE_DELETE.getCode(), UserStatusEnum.HAVE_DELETE.getDesc()));
            return false;
        }
        request.setAttribute("userInfo", user);
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    private static void setServletResponse(HttpServletResponse response, ServerResponse serverResponse) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        // 状态码 401: 需要授权
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JackSonUtils.beanToJsonStr(serverResponse));
        printWriter.flush();
        printWriter.close();
    }
}

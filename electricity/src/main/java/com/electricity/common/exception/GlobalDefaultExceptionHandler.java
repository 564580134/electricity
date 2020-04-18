package com.electricity.common.exception;


import com.electricity.common.utils.JackSonUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/**
 * @Description: 全局异常处理类
 * @Author: LiuRunYong
 * @Date: 2020/4/1
 **/
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public void errorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        e.printStackTrace();
        response.setContentType("application/json;charset=utf-8");
        /**状态码 500: 系统内部异常 */
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JackSonUtils.beanToJsonStr(ServerResponse.createByErrorInfo(e.getMessage(), request.getRequestURI())));
        printWriter.flush();
        printWriter.close();
    }
}


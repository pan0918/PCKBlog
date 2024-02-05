package com.pck.handler.security;

import com.alibaba.fastjson.JSON;
import com.pck.domain.ResponseResult;
import com.pck.enums.AppHttpCodeEnum;
import com.pck.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 设置授权失败时响应给前端的JSON格式
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // 打印异常信息
        accessDeniedException.printStackTrace();

        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        // 响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}

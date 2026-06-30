package com.orderSystem.common;

import com.orderSystem.entity.Result;
import com.orderSystem.exception.BaseException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            writeResponse(response);
            return false;
        }
        String token = auth.substring(7);
        int userId;
        try {
            userId = JwtUtils.jwtParse(token);
            BaseContext.setCurrentUser(userId);
        } catch (Exception e) {
            writeResponse(response);
            return false;
        }
        return true;
    }
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, BaseException ex)
            throws Exception {
            BaseContext.removeNowUser();
    }
    private void writeResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":401,\"message\":\"未登录或 token 已过期\"}");           // 写入响应体
    }
}

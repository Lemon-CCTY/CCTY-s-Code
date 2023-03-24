package com.nit.schoolorder.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.nit.schoolorder.common.Constants;
import com.nit.schoolorder.entity.Admin;
import com.nit.schoolorder.exception.ServiceException;

import com.nit.schoolorder.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: CCTY
 * @Date: 2023/3/6 20:29
 **/

/*设置通过token的拦截器*/
//@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private IAdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");//从http请求中取出token
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(Constants.CODE_401, "无token，请重新登录");
        }
        // 获取token中的userId
       Long adminId;
        try {
            adminId = Long.valueOf(JWT.decode(token).getAudience().get(0));
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_401, "token验证失败，请重新登录！");
        }
        //根据token中的userId查询数据库
        Admin admin = adminService.findByAdminId(adminId);
//        System.out.println(userService.findByUserId(userId));
        if (admin == null) {
            throw new ServiceException(Constants.CODE_401, "用户不存在，请重新登录！");
        }

        // 用户密码加签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(admin.getAdminPwd())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_401, "token验证失败，请重新登录！");
        }
        return true;
    }
}

package com.nit.schoolorder.utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nit.schoolorder.entity.Admin;
import com.nit.schoolorder.service.IAdminService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * @Author: CCTY
 * @Date: 2023/3/6 20:10
 **/
@Component
public class TokenUtils {
    /*把TokenUtils注册为SpringBoot的一个Bean,通过Resource方式把userService对象引入，
     * 后台项目启动把userService对象赋值给staticUserService静态对象
     * 为什么要这样赋值？
     * 因为getCurrentUser当前方法为静态方法，通过静态方法获取数据必须引用的是静态对象*/
    private static IAdminService staticUserService;
    @Resource
    private IAdminService adminService;

    @PostConstruct
    public void setUserService() {
        staticUserService = adminService;
    }


    public static String genToken(Long adminId, String sign) {
        return JWT.create().withAudience(String.valueOf(adminId)) // 将 user id 保存到 token 里面
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) //2h后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }

    /*获取当前登录的用户信息*/
    public static Admin getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                String adminId = JWT.decode(token).getAudience().get(0);//解析令牌
                return staticUserService.findByAdminId(Long.valueOf(adminId));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}

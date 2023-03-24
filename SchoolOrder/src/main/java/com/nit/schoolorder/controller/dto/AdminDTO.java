package com.nit.schoolorder.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: CCTY
 * @Date: 2023/2/20 14:48
 * DTO:接受前端登录请求的参数，用来做层与层之间的传递
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
    /*AdminDTO是接受前端传过来参数写的类，Admin是根据数据库结构表结构写的类*/
    private Long adminId;
    private String adminName;
    private String adminPwd;
    private String avatarUrl;
    private String token;
    private Long phone;
}

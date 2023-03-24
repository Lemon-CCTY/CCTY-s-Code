package com.nit.schoolorder.controller.dto;

import lombok.Data;

/**
 * @Author: CCTY
 * @Date: 2023/3/6 17:23
 **/
@Data
public class AdminPwdDto {
    private Long adminId;
    private String adminPwd;
    private String newAdminPwd;
}

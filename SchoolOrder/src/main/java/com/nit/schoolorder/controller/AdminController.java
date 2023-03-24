package com.nit.schoolorder.controller;

import cn.hutool.core.util.StrUtil;
import com.nit.schoolorder.common.Constants;
import com.nit.schoolorder.common.Result;
import com.nit.schoolorder.controller.dto.AdminDTO;
import com.nit.schoolorder.controller.dto.AdminPwdDto;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import com.nit.schoolorder.service.*;
import com.nit.schoolorder.entity.Admin;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author CCTY
 * @since 2023-03-22
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private IAdminService adminService;
    //登录
    @PostMapping("/login")
    public Result login(@RequestBody AdminDTO adminDTO) {
        Long adminId = adminDTO.getAdminId();
        String adminPwd = adminDTO.getAdminPwd();
        if (StrUtil.isBlankIfStr(new Long(adminId)) || StrUtil.isBlank(adminPwd)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        AdminDTO DTO = adminService.login(adminDTO);
        return Result.success(DTO);
    }
    //注册
    @PostMapping("/register")
    public Result register(@RequestBody AdminDTO adminDTO) {
        Long adminId = adminDTO.getAdminId();
        String adminPwd = adminDTO.getAdminPwd();
        if (StrUtil.isBlankIfStr(new Long(adminId)) || StrUtil.isBlank(adminPwd)) {
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(adminService.register(adminDTO));
    }
    //修改个人信息
    @PostMapping("/updateAdminInfo")
    public Result updateAdminInfo(@RequestBody AdminDTO adminDTO) {
        adminService.updateAdminInfo(adminDTO);
        System.out.println("修改成功");
        return Result.success();
    }
    //修改密码
    @PostMapping("/updateAdminPwd")
    public Result updateAdminPwd(@RequestBody AdminPwdDto adminPwdDto) {
        adminService.updateAdminPwd(adminPwdDto);
        return Result.success();
    }

    //根据id查询
    @GetMapping("/{adminId}")
    public Admin findOne(@PathVariable Long adminId) {
        return adminService.getById(adminId);
    }


}


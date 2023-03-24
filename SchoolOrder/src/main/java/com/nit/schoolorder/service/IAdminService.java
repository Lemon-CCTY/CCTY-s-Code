package com.nit.schoolorder.service;

import com.nit.schoolorder.controller.dto.AdminDTO;
import com.nit.schoolorder.controller.dto.AdminPwdDto;
import com.nit.schoolorder.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CCTY
 * @since 2023-03-22
 */
public interface IAdminService extends IService<Admin> {

    void updateAdminPwd(AdminPwdDto adminPwdDto);

    void updateAdminInfo(AdminDTO adminDTO);

    Object register(AdminDTO adminDTO);

    AdminDTO login(AdminDTO adminDTO);

    Admin findByAdminId(Long adminId);
}

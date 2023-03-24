package com.nit.schoolorder.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nit.schoolorder.common.Constants;
import com.nit.schoolorder.controller.dto.AdminDTO;
import com.nit.schoolorder.controller.dto.AdminPwdDto;
import com.nit.schoolorder.entity.Admin;
import com.nit.schoolorder.exception.ServiceException;
import com.nit.schoolorder.mapper.AdminMapper;
import com.nit.schoolorder.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nit.schoolorder.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CCTY
 * @since 2023-03-22
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public void updateAdminPwd(AdminPwdDto adminPwdDto) {
        int update = adminMapper.updatePwd(adminPwdDto);
        if (update < 1) {
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
    }
    @Override
    public void updateAdminInfo(AdminDTO adminDTO) {
        int update = adminMapper.updateAdminInfo(adminDTO);
        if (update < 1) {
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
    }

    @Override
    public Admin register(AdminDTO adminDTO) {
        Admin one = getAdminInfo(adminDTO);
        if (one == null) {
            one = new Admin();
            BeanUtil.copyProperties(adminDTO, one, true);
            save(one);
        } else {
            throw new ServiceException(Constants.CODE_500, "用户已存在");
        }
        return one;
    }

    @Override
    public AdminDTO login(AdminDTO adminDTO) {
        //校验参数
        Admin one = getAdminInfo(adminDTO);
        if (one != null) {
            BeanUtil.copyProperties(one, adminDTO, true);
//            设置token
            String token = TokenUtils.genToken(one.getAdminId(), one.getAdminPwd());
            adminDTO.setToken(token);
            return adminDTO;
        } else {
            throw new ServiceException(Constants.CODE_505, "用户名或密码错误");
        }
    }

    @Override
    public Admin findByAdminId(Long adminId) {
        Admin admin =adminMapper.findByAdminId(adminId);
        return admin;
    }

    private Admin getAdminInfo(AdminDTO adminDTO) {
        //用户校验
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id", adminDTO.getAdminId());//字段要跟数据库名一样
        queryWrapper.eq("admin_pwd", adminDTO.getAdminPwd());
        Admin one;
        try {
            one = getOne(queryWrapper);
        } catch (Exception e) {
//            LOG.error(e);
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }
}

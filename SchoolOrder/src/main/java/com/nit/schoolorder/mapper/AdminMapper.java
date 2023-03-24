package com.nit.schoolorder.mapper;

import com.nit.schoolorder.controller.dto.AdminDTO;
import com.nit.schoolorder.controller.dto.AdminPwdDto;
import com.nit.schoolorder.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author CCTY
 * @since 2023-03-22
 */
public interface AdminMapper extends BaseMapper<Admin> {
    @Select("select * from admin where admin_id=#{adminId}")
    Admin findByAdminId(Long adminId);

    @Update("update admin set admin_pwd = #{newAdminPwd} where admin_id=#{adminId} and admin_pwd=#{adminPwd}")
    int updatePwd(AdminPwdDto adminPwdDto);

    @Update("update admin set avatar_url = #{avatarUrl},admin_name=#{adminName},phone=#{phone} where admin_id=#{adminId}")
    int updateAdminInfo(AdminDTO adminDTO);
}

package com.nit.schoolorder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author CCTY
 * @since 2023-03-22
 */
@Getter
@Setter
  @ApiModel(value = "Admin对象", description = "")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("管理员id（登录账号名")
      private Long adminId;

      @ApiModelProperty("管理员名称")
      private String adminName;

      @ApiModelProperty("管理员账号密码")
      private String adminPwd;

      @ApiModelProperty("手机号")
      private Long phone;

      @ApiModelProperty("头像设置")
      private String avatarUrl;


}

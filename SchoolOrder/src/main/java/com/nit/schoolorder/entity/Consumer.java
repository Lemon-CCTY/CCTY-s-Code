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
 * @since 2023-03-20
 */
@Getter
@Setter
  @ApiModel(value = "Consumer对象", description = "")
public class Consumer implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("消费者id（登录账号名")
      private Long consumerId;

      @ApiModelProperty("消费者名称")
      private String consumerName;

      @ApiModelProperty("消费者账号密码")
      private String consumerPwd;

      @ApiModelProperty("手机号")
      private Long phone;

      @ApiModelProperty("地址")
      private String address;

      @ApiModelProperty("头像设置")
      private String avatarUrl;

      @ApiModelProperty("订单id，用于关联订单")
      private Integer orderId;


}

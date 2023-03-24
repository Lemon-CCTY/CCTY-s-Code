package com.nit.schoolorder.entity;

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
 * @since 2023-03-21
 */
@Getter
@Setter
  @ApiModel(value = "Seller对象", description = "")
public class Seller implements Serializable {

    private static final long serialVersionUID = 1L;

      private Integer id;

      @ApiModelProperty("商家id")
      private Integer sellerId;

      @ApiModelProperty("商家登录密码")
      private String sellerPwd;

      @ApiModelProperty("商家名称")
      private String sellerName;

      @ApiModelProperty("手机号")
      private Long sellerPhone;

      @ApiModelProperty("商品id")
      private Integer goodsId;

      @ApiModelProperty("0为待审核，1为已审核")
      private Boolean isPass;

      @ApiModelProperty("所属食堂id")
      private Integer canteenId;

      @ApiModelProperty("店铺头像")
      private String avatarUrl;


}

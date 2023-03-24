package com.nit.schoolorder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
  @ApiModel(value = "Gorder对象", description = "")
public class Gorder implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("订单id")
      private String orderId;

      @ApiModelProperty("订单创建时间")
      private LocalDateTime createTime;

      @ApiModelProperty("支付时间")
      private LocalDateTime paidTime;

      @ApiModelProperty("订单到达时间")
      private LocalDateTime arrivedTime;

      @ApiModelProperty("订单评价的外键	")
      private Integer evaluationsId;

      @ApiModelProperty("消费者id")
      private List<Consumer> consumerList;

      @ApiModelProperty("商家id")
      private List<Seller> sellerList;


}

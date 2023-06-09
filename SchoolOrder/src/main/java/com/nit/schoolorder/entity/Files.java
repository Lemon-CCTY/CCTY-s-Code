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
 * @since 2023-03-08
 */
@Getter
@Setter
@ApiModel(value = "File对象", description = "")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("文件名称")
    private String name;

    @ApiModelProperty("文件类型")
    private String type;

    @ApiModelProperty("文件大小")
    private Long size;

    @ApiModelProperty("文件md5")
    private String md5;

    @ApiModelProperty("下载链接")
    private String url;

    @ApiModelProperty("是否删除（逻辑删除")
    private Boolean isDelete;

    @ApiModelProperty("是否禁用链接")
    private Boolean enable;


}

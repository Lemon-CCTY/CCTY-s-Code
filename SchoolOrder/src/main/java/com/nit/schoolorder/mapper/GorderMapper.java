package com.nit.schoolorder.mapper;

import com.nit.schoolorder.entity.Gorder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author CCTY
 * @since 2023-03-21
 */
public interface GorderMapper extends BaseMapper<Gorder> {

    List<Gorder> selectAllOrder();
}

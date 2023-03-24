package com.nit.schoolorder.service.impl;

import com.nit.schoolorder.entity.Files;
import com.nit.schoolorder.mapper.FileMapper;
import com.nit.schoolorder.service.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CCTY
 * @since 2023-03-08
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, Files> implements IFileService {

}

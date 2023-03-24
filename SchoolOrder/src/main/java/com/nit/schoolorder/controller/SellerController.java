package com.nit.schoolorder.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.nit.schoolorder.service.*;
import com.nit.schoolorder.entity.Seller;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author CCTY
 * @since 2023-03-21
 */
@RestController
@RequestMapping("/seller")
        public class SellerController {
    
       @Resource
        private ISellerService sellerService;
        //新增或修改
        @PostMapping
        public Boolean save(@RequestBody Seller seller){
        return sellerService.saveOrUpdate(seller);
        }
        //删除
       @DeleteMapping("/{id}")
        public Boolean delete(@PathVariable Integer id){
        return sellerService.removeById(id);
        }
        //查询所有
        @GetMapping
        public List<Seller> findAll(){
        return sellerService.list();
        }
       //根据id查询
       @GetMapping("/{id}")
        public Seller findOne(@PathVariable Integer id){
        return sellerService.getById(id);
        }
        //分页查询
        @GetMapping("/page")
        public Page<Seller> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize){
        QueryWrapper<Seller> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");//id倒叙查看用户信息
        return sellerService.page(new Page<>(pageNum,pageSize));
        }

        }


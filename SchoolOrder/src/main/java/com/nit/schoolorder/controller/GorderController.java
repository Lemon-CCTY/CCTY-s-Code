package com.nit.schoolorder.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.nit.schoolorder.service.*;
import com.nit.schoolorder.entity.Gorder;

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
@RequestMapping("/gorder")
        public class GorderController {
    
       @Resource
        private IGorderService gorderService;
        //新增或修改
        @PostMapping
        public Boolean save(@RequestBody Gorder gorder){
        return gorderService.saveOrUpdate(gorder);
        }
        //删除
       @DeleteMapping("/{id}")
        public Boolean delete(@PathVariable Integer id){
        return gorderService.removeById(id);
        }
        //查询所有
        @GetMapping
        public List<Gorder> findAll(){
        return gorderService.list();
        }
       //根据id查询
       @GetMapping("/{id}")
        public Gorder findOne(@PathVariable Integer id){
        return gorderService.getById(id);
        }
        //分页查询
        @GetMapping("/page")
        public Page<Gorder> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize){
        QueryWrapper<Gorder> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return gorderService.page(new Page<>(pageNum,pageSize));
        }

        }


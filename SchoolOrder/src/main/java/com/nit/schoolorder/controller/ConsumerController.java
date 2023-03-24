package com.nit.schoolorder.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import com.nit.schoolorder.service.*;
import com.nit.schoolorder.entity.Consumer;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author CCTY
 * @since 2023-03-20
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Resource
    private IConsumerService consumerService;

    //新增或修改
    @PostMapping
    public Boolean save(@RequestBody Consumer consumer) {
        return consumerService.saveOrUpdate(consumer);
    }

    //批量删除
    @DeleteMapping("/del/batch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return consumerService.removeByIds(ids);
    }
    //删除
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return consumerService.removeById(id);
    }

    //查询所有
    @GetMapping
    public List<Consumer> findAll() {
        return consumerService.list();
    }

    //根据id查询
    @GetMapping("/{id}")
    public Consumer findOne(@PathVariable Integer id) {
        return consumerService.getById(id);
    }

    //分页查询
    @GetMapping("/page")
    public IPage<Consumer> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String consumerId,
                                @RequestParam(defaultValue = "") String consumerName,
                                @RequestParam(defaultValue = "") String phone,
                                @RequestParam(defaultValue = "") String address) {
        IPage<Consumer> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Consumer> queryWrapper = new QueryWrapper<>();
             /*
         default Children like(R column, Object val) {
        return this.like(true, column, val);
        like方法内的键要与数据库字段匹配
             }*/
        if (!"".equals(consumerId)) {
            queryWrapper.like("consumer_id", consumerId);
        }
        if (!"".equals(consumerName)) {
            queryWrapper.like("consumer_name", consumerName);
        }
        if (!"".equals(phone)) {
            queryWrapper.like("phone", phone);
        }
        if (!"".equals(address)) {
            queryWrapper.like("address", address);
        }
        //获取当前登录的用户名
//        User currentUser = TokenUtils.getCurrentUser();
//        System.out.println(currentUser.getUserName());
//        queryWrapper.orderByDesc("id");//id倒叙查看用户信息
        return consumerService.page(page, queryWrapper);
    }

    /*导出接口*/
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        //从数据库查询出所有数据
        List<Consumer> list = consumerService.list();
        //通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer= ExcelUtil.getWriter(filesUploadPath+"/用户信息.xlsx");
        //在内存操作，写出到浏览器(自行下载）
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
        writer.addHeaderAlias("id", "id");
        writer.addHeaderAlias("consumerId", "学号/工号");
        writer.addHeaderAlias("consumerName", "用户名");
        writer.addHeaderAlias("consumerPwd", "密码");
        writer.addHeaderAlias("phone", "手机号");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("avatarUrl", "头像");

        //一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);
        //设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);
        outputStream.close();
        writer.close();
    }

    /*导入*/
    @PostMapping("/import")
    public boolean imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //方式一：通过javabean方式读取excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list=reader.readAll(User.class);

        //方式二：忽略表头的中文，直接读取表的内容 注：导入数据不能为空
        List<List<Object>> list = reader.read(1);
        List<Consumer> consumers = CollUtil.newArrayList();
        for (List<Object> row : list) {
            Consumer consumer = new Consumer();
            consumer.setConsumerId(new Long(row.get(0).toString()));
            consumer.setConsumerName(row.get(1).toString());
            consumer.setPhone(new Long(row.get(2).toString()));
            consumer.setAddress(row.get(3).toString());
            consumers.add(consumer);
        }
        //saveBatch()批量插入
        consumerService.saveBatch(consumers);
        return true;
    }
}


package com.nit.schoolorder.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @Author: CCTY
 * @Date: 2023/2/13 21:52
 **/
//代码生成器
public class CodeGenerator {
    public static void main(String[] args) {
        generator();
    }

    private static void generator() {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/schoolorder?serverTimezone=GMT%2b8", "root", "1234")
                .globalConfig(builder -> {
                    builder.author("CCTY") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("G:\\project\\java\\SchoolOrder\\src\\main\\java\\"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.nit.schoolorder") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "G:\\project\\java\\SchoolOrder\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();//开启lonbok模型
//                    builder.mapperBuilder().enableMapperAnnotation().build();
                    builder.controllerBuilder().enableHyphenStyle()  // 开启驼峰转连字符
                            .enableRestStyle();  // 开启生成@RestController 控制器
                       /*设置需要生成的表名 .addInclude("表名")
                            这里可以继续添加表名生成数据库表中所有文件，要加什么通过addInclude添加
                            已添加的最好注释掉，否则再次运行又会重新覆盖
                            .*/
                    builder.//.addInclude("user")
                addInclude("admin").
                            addTablePrefix("t_", "c_"); // 设置过滤表前缀

                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}

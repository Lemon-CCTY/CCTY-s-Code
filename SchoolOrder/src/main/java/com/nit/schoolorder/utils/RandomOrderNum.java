package com.nit.schoolorder.utils;

import java.util.Random;

/**
 * @Author: CCTY
 * @Date: 2023/3/20 21:01
 **/
public class RandomOrderNum {
    public static void main(String[] args) throws Exception{
//        StringBuilder str = new StringBuilder();//定义变长字符串
//        Random random = new Random();
//        //随机生成数字，并添加到字符串
//        for (int i = 0; i < 8; i++) {
//            str.append(random.nextInt(10));
//        }
//        //将字符串转换为数字并输出
//        int num = Integer.parseInt(str.toString());
//        System.out.println(num);
        for (int i = 0; i <10 ; i++) {
            System.out.println(getRandomNickname(8));
        }
    }
    public static String getRandomNickname(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }

}

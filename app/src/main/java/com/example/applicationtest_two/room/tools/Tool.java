package com.example.applicationtest_two.room.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

// 设置笔记上架分页的时间
public class Tool {
    public static String getChineseDate(Date date, String type){
        // 由于Java的new date()函数获取的时间是别的时区的，获取到的时间和我们中国的时间相差8个小时，所以这里的时区要设置一下改为东八区
        // 更改时区
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        //创建一个格式化对象
        SimpleDateFormat sdf=new SimpleDateFormat();

        // 根据获取的type类型，返回不同的sdf结果
        if("年月日".equals(type))
            sdf=new SimpleDateFormat("yyyy年MM月dd日");
        else if("年月日小时分钟秒".equals(type))
            sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        else if("年月日小时分钟".equals(type))
            sdf=new SimpleDateFormat("yyyy年MM月dd日 HH点mm分");
        // 返回sdf时间结果
        return sdf.format(date);
    }
}

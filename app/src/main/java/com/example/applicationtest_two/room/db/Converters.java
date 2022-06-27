package com.example.applicationtest_two.room.db;

import androidx.room.TypeConverter;

import java.util.Date;

//TypeConverter，它将自定义类转换为一个已知的类型
public class Converters {
    //将数据库中timestamp类型转换成java的Date对象
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }
    //将java的Date对象转换成数据库中timestamp类型
    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}

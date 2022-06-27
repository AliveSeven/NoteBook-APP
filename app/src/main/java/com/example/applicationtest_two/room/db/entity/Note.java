package com.example.applicationtest_two.room.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

@Entity
public class Note {
    //PrimaryKey表示是字段id是主键，autoGenerate表示id的值会自动生成，不用用户指定
    @PrimaryKey(autoGenerate=true)
    private Long id;

    //NonNull表示字段note_name不能为空；ColumnInfo(name = "note_name")表示note表中的note_name字段对应Note类的noteName属性
    @NonNull
    @ColumnInfo(name = "note_name")
    public String noteName;

    // 笔记的创建时间
    @ColumnInfo(name = "register_date")
    public Date registerDate;

    //必须有一个无参数构造函数，room要使用它创建对象
    public Note() {}

    //@Ignore注解表示此构造函数room不需要使用，但是程序员可以使用它来创建Note对象
    @Ignore
    public Note(String noteName) {
        this.noteName = noteName;
    }

    //重写判断对象内容是否相等的方法
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;//先判断o是否为本对象，如果是就肯定是同一对象了，this 指向当前的对象
        if (o == null || getClass() != o.getClass()) return false;//再判断o是否为null，和o.类对象和本类对象是否一致
        Note note = (Note) o;//再把o对象强制转化为Note类对象

        // 类型相同, 比较内容是否相同
        if(id != note.id)
            return false;

        //查看两个对象的属性值是否相等,返回结果
        return id == note.id &&
                Objects.equals(noteName, note.noteName);
    }

    //get和set方法，自动生成
    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, noteName);
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
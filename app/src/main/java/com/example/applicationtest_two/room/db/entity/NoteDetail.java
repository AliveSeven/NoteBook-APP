package com.example.applicationtest_two.room.db.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

//设置外键（级联删除）和索引
@Entity(foreignKeys = @ForeignKey(entity = Note.class,parentColumns = "id",childColumns = "note_id", onDelete = CASCADE)
        ,indices = {@Index("note_id")})
public class NoteDetail {
    //设置主键，笔记细节id
    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name = "notedetail_id")
    private Long noteDetailId;

    //对应Note表的主键，
    @NonNull
    @ColumnInfo(name = "note_id")
    private Long noteId;

    //笔记详细信息的相关变量，定义为public
    @NonNull
    public String content;
    //@ColumnInfo(name = "_data")，指定该字段在表中的列的名字
    // 笔记的创建时间，这里其实可以删去，不要的，因为Note表里面已经有这个属性了，但是我懒得改了
    @ColumnInfo(name = "register_date")
    public Date registerDate;

    //设置笔记细节相关变量的Get和Set方法，自动生成即可
    public Long getNoteDetailId() {
        return noteDetailId;
    }

    public void setNoteDetailId(Long noteDetailId) {
        this.noteDetailId = noteDetailId;
    }

    @NonNull
    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(@NonNull Long noteId) {
        this.noteId = noteId;
    }

    //  笔记内容的get和set方法
    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    //   笔记创建时间的get和set方法
    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

}

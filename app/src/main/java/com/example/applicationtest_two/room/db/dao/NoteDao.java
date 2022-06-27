package com.example.applicationtest_two.room.db.dao;

import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.applicationtest_two.room.db.entity.Note;

import java.util.List;


@Dao
public interface NoteDao {

    //获取笔记最大的id（用于插入笔记记录后，再将笔记的详细信息插入到笔记详情表NoteDetail时，需要刚插入笔记的id）
    @Query("SELECT max(id) FROM Note")
    Long getMaxId();

    // 插入复数的Note
    @Insert
    void insert(Note... note);

    @Insert
    void insert(List<Note> note);

    //删除note表中所有记录
    @Query("DELETE FROM Note ")
    void deleteAll();

    //删除note表中所有主键在Note中的记录
    @Delete
    void delete(List<Note> note);

    //删除一条记录
    @Delete
    void delete(Note note);

    // 更新一条记录
    @Update
    void update(Note note);

    @Query("SELECT * FROM Note")
    List<Note> getAll();

    @Query("SELECT * FROM Note")
    public PagingSource<Integer, Note> loadAllForPaging();

    //带条件查询，用于recyclerView组件
    @Query("SELECT * FROM Note where note_name LIKE :noteName")
    public PagingSource<Integer, Note> loadAllForPagingByName(String noteName);

    @Query("SELECT * FROM Note WHERE note_name=:noteName")
    Note findByName(String noteName);

    @Query("SELECT count(*) FROM Note")
    Long getAllSize();

}

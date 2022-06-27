package com.example.applicationtest_two.room.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.applicationtest_two.room.db.entity.NoteDetail;


@Dao
public interface NoteDetailDao {

    @Insert
    void insert(NoteDetail... noteDetails);

    //根据笔记id查询
    @Query("SELECT * FROM NoteDetail WHERE note_id=:noteId")
    NoteDetail findByNoteId(Long noteId);

    //删除一条记录
    @Delete
    void delete(NoteDetail noteDetail);

    // 更新NoteDetail记录
    @Update
    void update(NoteDetail noteDetail);

}

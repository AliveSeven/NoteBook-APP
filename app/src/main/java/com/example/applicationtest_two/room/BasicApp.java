package com.example.applicationtest_two.room;
import android.app.Application;

import com.example.applicationtest_two.room.db.entity.Note;
import com.example.applicationtest_two.room.db.entity.NoteDetail;


//可以用于存放全局数据  要在AndroidManifest.xml的application标签中配置BasicApp
public class BasicApp extends Application {
    Note note;//全局数据
    NoteDetail noteDetail;//全局数据

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public NoteDetail getNoteDetail() {
        return noteDetail;
    }

    public void setNoteDetail(NoteDetail noteDetail) {
        this.noteDetail = noteDetail;
    }
}

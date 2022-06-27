package com.example.applicationtest_two.room.RecyclerView;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationtest_two.R;
import com.example.applicationtest_two.room.db.AppDatabase;
import com.example.applicationtest_two.room.db.dao.NoteDetailDao;
import com.example.applicationtest_two.room.db.entity.Note;
import com.example.applicationtest_two.room.db.entity.NoteDetail;
import com.example.applicationtest_two.room.tools.AppExecutors;
import com.example.applicationtest_two.room.tools.Tool;


//RecyclerView.ViewHolder用于将数据库中一条记录 绑定 到activity_note_recycler_view_item.xml中的TextView组件
public class NoteViewHolder extends RecyclerView.ViewHolder {
    // 声明变量
    private View itemView;
    private Note note;
    private TextView nameTextView;
    private TextView timeTextView;

    // NoteViewHodler方法
    public NoteViewHolder(View itemView) {
        super(itemView);
        // 赋值itemView
        this.itemView=itemView;
        // 通关组件id绑定相对于的组件TextView
        this.nameTextView = (TextView)itemView.findViewById(R.id.name);
        // 创建时间的time组件
        this.timeTextView = (TextView) itemView.findViewById(R.id.time);
    }

    //将数据库中一条记录的数据赋值给TextView组件
    //note会被RecyclerView自动赋值
    public void bindTo(Note note){
        this.note = note;
        if(note ==null)
            return;
        // 设置item笔记标题
        nameTextView.setText(note.getNoteName());
        // 设置item笔记的创建时间
        timeTextView.setText(Tool.getChineseDate(note.getRegisterDate(),"年月日小时分钟秒"));
    }

    //自动生成get和set方法
    public TextView getNameTextView() {
        return nameTextView;
    }

    public void setNameTextView(TextView nameTextView) {
        this.nameTextView = nameTextView;
    }

    public View getItemView() {
        return itemView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}

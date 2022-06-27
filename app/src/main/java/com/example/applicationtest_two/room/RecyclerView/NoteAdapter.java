package com.example.applicationtest_two.room.RecyclerView;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.example.applicationtest_two.R;
import com.example.applicationtest_two.room.BasicApp;
import com.example.applicationtest_two.room.NoteDetailShowActivity;
import com.example.applicationtest_two.room.db.entity.Note;
import com.example.applicationtest_two.room.tools.MyDialog;


//adapter：适配器，为view提供数据，
//PagingDataAdapter: RecyclerView的适配器，用于为RecyclerView组件提供数据并显示
public class NoteAdapter extends PagingDataAdapter<Note, NoteViewHolder> {
    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    //重写父类PagedListAdapter的onCreateViewHolder方法，创建代表单条数据记录显示界面的ViewHolder对象
    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_note_recycler_view_item, parent, false);

        //根据activity_note_recycler_view_item创建ViewHolder对象
        NoteViewHolder holder = new NoteViewHolder(view);

        //设置监听事件，当用户点击了某个note
        holder.getItemView().setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //将当前笔记信息保存到BasicApp中，使之变为全局数据，其他地方也可获得
                BasicApp basicApp=(BasicApp)parent.getContext().getApplicationContext();
                // 通关hold get到Note
                basicApp.setNote(holder.getNote());
                //跳转到显示笔记详情的activity
                parent.getContext().startActivity(new Intent(parent.getContext(), NoteDetailShowActivity.class));
            }
        });

        //设置监听事件，当用户长按了某个note，setOnLong 是长按
        holder.getItemView().setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //将当前笔记信息保存到BasicApp中，使之变为全局数据，其他地方也可获得
                BasicApp basicApp=(BasicApp)parent.getContext().getApplicationContext();
                basicApp.setNote(holder.getNote());
                //打开自定义对话框，让用户确认是否要删除当前note
                MyDialog myDialog = new MyDialog(parent.getContext(), "确定要删除吗？");
                myDialog.show();
                return true;
            }

        });
        return holder;
    }

    //将单条数据记录与显示单条数据的ViewHolder绑定在一块，实现将单条数据显示的功能
    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    //DiffUtil是android的一个工具类，是帮助我们在刷新RecyclerView时，计算新老数据集的差异，寻找出旧数据集变化到新数据集的最小变化量。
    // 并自动调用RecyclerView.Adapter的刷新方法，以完成高效刷新并伴有Item动画的效果
    private static DiffUtil.ItemCallback<Note> DIFF_CALLBACK =
        new DiffUtil.ItemCallback<Note>() {
            //判断新旧条数据记录是否相等，用id判断
            @Override
            public boolean areItemsTheSame(Note oldNote, Note newNote) {
                return oldNote.getId()== newNote.getId();
            }

            //判断新旧条数据记录内容是否相等
            @Override
            public boolean areContentsTheSame(Note oldNote, Note newNote) {
                return oldNote.equals(newNote);
            }
        };

};


package com.example.applicationtest_two.room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.applicationtest_two.MainActivity;
import com.example.applicationtest_two.R;
import com.example.applicationtest_two.room.db.AppDatabase;
import com.example.applicationtest_two.room.db.dao.NoteDao;
import com.example.applicationtest_two.room.db.dao.NoteDetailDao;
import com.example.applicationtest_two.room.db.entity.Note;
import com.example.applicationtest_two.room.db.entity.NoteDetail;
import com.example.applicationtest_two.room.tools.AppExecutors;
import com.example.applicationtest_two.room.tools.Tool;

import java.util.Date;

//动漫详细信息展示页面
public class NoteDetailShowActivity extends AppCompatActivity implements View.OnClickListener {
    //要展示的相关信息：笔记标题、笔记内容、笔记注册时间
    private EditText name , content;
    private TextView registeDate;
    // 声明要编辑事件的按钮为Button类型
    private Button donebutton,backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail_show);

        //根据id找到相应的组件,笔记标题
        name = (EditText) findViewById(R.id.name);
        // 笔记内容
        content = (EditText) findViewById(R.id.content);
        // 笔记注册日期
        registeDate = (TextView) findViewById(R.id.registe_date);
        // 完成按钮
        donebutton = (Button) findViewById(R.id.btn_done);
        // 按钮监听
        donebutton.setOnClickListener(this);
        // 返回按钮
        backbutton = (Button) findViewById(R.id.back2);
        // 按钮监听
        backbutton.setOnClickListener(this);

        //从BasicApp中获取全局数据note（代表刚才点击的笔记）
        // 该数据在NoteAdapter的onCreateViewHolder方法中的setOnClickListener获得
        BasicApp basicApp=(BasicApp)this.getApplicationContext();
        Note note =basicApp.getNote();
        //显示笔记的名字
        name.setText(note.getNoteName());
        //从线程池获取线程
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // 获取NoteDetailDao
                    NoteDetailDao noteDetailDao = AppDatabase.getInstance(getApplicationContext()).noteDetailDao();
                    // 根据笔记id查询笔记的详情NoteDetail
                    NoteDetail noteDetail = noteDetailDao.findByNoteId(note.getId());
                    // 显示笔记内容
                    content.setText(noteDetail.getContent());
                    // 使用到了自定义工具类的getChineseDate：将日期型数据输出为指定格式的中文日期字符串，输出的时间改为第八时区，即北京时间
                    registeDate.setText(Tool.getChineseDate(noteDetail.getRegisterDate(),"年月日小时分钟秒"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_done:
                // 从BasicApp中获取全局数据note（代表刚才点击的笔记）
                BasicApp basicApp2=(BasicApp)this.getApplicationContext();
                // 获取当前Note对象
                Note note2 =basicApp2.getNote();
                // 笔记注册时间
                note2.setRegisterDate(new Date());
                // 根据用户输入的信息创建笔记对象
                note2.setNoteName(name.getText().toString().trim());
                // 弹出提示，由于放在子线程会报错，这里放在主线程
                Toast.makeText(NoteDetailShowActivity.this, "修改记录成功", Toast.LENGTH_LONG).show();
                //从线程池获取线程
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // NoteDao对象
                            NoteDao noteDao = AppDatabase.getInstance(basicApp2).noteDao();
                            // 开始事务
                            AppDatabase.getInstance(getApplicationContext()).beginTransaction();
                            // 更新note标题
                            noteDao.update(note2);
                            // 获取NoteDetailDao
                            NoteDetailDao noteDetailDao = AppDatabase.getInstance(getApplicationContext()).noteDetailDao();
                            // 根据笔记id查询笔记的详情NoteDetail
                            NoteDetail noteDetail = noteDetailDao.findByNoteId(note2.getId());
                            //set笔记内容
                            noteDetail.setContent(content.getText().toString().trim());
                            // 注册时间
                            noteDetail.setRegisterDate(new Date());
                            //更新noteDetail内容
                            noteDetailDao.update(noteDetail);
                            //结束并提交事务
                            AppDatabase.getInstance(getApplicationContext()).setTransactionSuccessful();
                        }catch(Exception e){
                            e.printStackTrace();
                        }finally{
                            AppDatabase.getInstance(getApplicationContext()).endTransaction(); //处理完成
                            finish();
                            Intent intent=new Intent();
                            // 清空之前栈中所有的Activity，释放资源。
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            // 设置跳转的类
                            intent.setClass(NoteDetailShowActivity.this,MainActivity.class);
                            // 开始跳转
                            startActivity(intent);
                        }
                    }
                });
                break;
            case R.id.back2:
                // 按钮按下之后跳转到主页
                Intent intent2 = new Intent();
                // 清空之前栈中所有的Activity，释放资源。
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                // 设置跳转的类
                intent2.setClass(NoteDetailShowActivity.this,MainActivity.class);
                // 开始跳转
                startActivity(intent2);
                break;
        }
    }


}

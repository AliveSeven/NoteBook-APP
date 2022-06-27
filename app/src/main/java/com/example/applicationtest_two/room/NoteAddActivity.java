package com.example.applicationtest_two.room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Date;

public class NoteAddActivity extends AppCompatActivity implements View.OnClickListener {
    // 声明要增加的变量为EditText类型
    private EditText name, content ;
    // 声明要编辑事件的按钮为Button类型
    private Button button , button2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add);

        //根据id找到相对于的组件，笔记标题
        name = (EditText) findViewById(R.id.name);
        // 笔记内容
        content = (EditText)findViewById(R.id.content);
        // 按下按钮
        button = (Button) findViewById(R.id.button);
        // 设置监听
        button.setOnClickListener(this);
        // 返回按钮
        button2 = (Button) findViewById(R.id.back);
        // 按下按钮
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                // 弹出提示，由于放在子线程会报错，这里放在主线程
                Toast.makeText(NoteAddActivity.this, "保存记录成功", Toast.LENGTH_LONG).show();
                //根据用户输入的信息创建笔记对象
                Note note =new Note();
                // 注册时间
                note.setRegisterDate(new Date());
                // 设置笔记标题
                note.setNoteName(name.getText().toString().trim());
                //从线程池获取线程
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            NoteDao noteDao = AppDatabase.getInstance(getApplicationContext()).noteDao();
                            //开始事务
                            AppDatabase.getInstance(getApplicationContext()).beginTransaction();
                            //插入note表
                            noteDao.insert(note);
                            //获取刚插入note表时记录的id（因为id字段是自动增长，因此是最大的id）
                            Long maxId = noteDao.getMaxId();
                            //根据用户输入的信息创建笔记详情对象
                            NoteDetail noteDetail = new NoteDetail();
                            noteDetail.setNoteId(maxId);
                            //set笔记内容
                            noteDetail.setContent(content.getText().toString().trim());
                            // 注册时间
                            noteDetail.setRegisterDate(new Date());
                            // Dao对象
                            NoteDetailDao noteDetailDao = AppDatabase.getInstance(getApplicationContext()).noteDetailDao();
                            //插入NoteDetail表
                            noteDetailDao.insert(noteDetail);
                            //结束并提交事务
                            AppDatabase.getInstance(getApplicationContext()).setTransactionSuccessful();
                        }catch(Exception e){
                            e.printStackTrace();
                        }finally{
                            AppDatabase.getInstance(getApplicationContext()).endTransaction(); //处理完成
                            // 完成页面任务，回到之前的页面
                            finish();
                        }
                    }
                });
            case R.id.back:
                // 按钮按下之后跳转到主页
                Intent intent = new Intent();
                // 清空之前栈中所有的Activity，释放资源。
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                // 设置跳转的类
                intent.setClass(NoteAddActivity.this,MainActivity.class);
                // 开始跳转
                startActivity(intent);
                break;
        }
    }
}

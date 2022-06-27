package com.example.applicationtest_two.room.tools;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicationtest_two.R;
import com.example.applicationtest_two.room.BasicApp;
import com.example.applicationtest_two.room.NoteDetailShowActivity;
import com.example.applicationtest_two.room.db.AppDatabase;
import com.example.applicationtest_two.room.db.dao.NoteDao;

//自定义对话框
public class MyDialog extends Dialog {
    // 声明各个变量类型
    private String dialogName;
    private TextView tvMsg;
    private Button btnOK;
    private Button btnCancel;

    public MyDialog(Context context, String dialogName) {
        super(context);
        this.dialogName = dialogName;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题
        setContentView(R.layout.my_dialog);
        // 根据组件id绑定声明变量
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        btnOK = (Button) findViewById(R.id.btn_ok);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        tvMsg.setText(dialogName);  //设置自定义对话显示内容
        //为确定按钮设置点击事件
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 弹出提示，由于放在子线程会报错，这里放在主线程这里
                Toast.makeText(getContext(), "删除记录成功", Toast.LENGTH_LONG).show();
                //从线程池获取线程
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        // 设置一个布尔值作为中间项
                        boolean delete=false;
                        // 通关BasicApp获取全局数据note
                        BasicApp basicApp=(BasicApp)v.getContext().getApplicationContext();
                        try {
                            // 设置dao
                            NoteDao noteDao = AppDatabase.getInstance(basicApp).noteDao();
                            //删除note
                            //basicApp.getNote()获取全局数据note，
                            // 该note是在NoteAdapter的onCreateViewHolder的setOnLongClickListener中保存的
                            // 调用dao中的delete方法
                            noteDao.delete(basicApp.getNote());
                            delete=true;
                        } catch (Exception e) {
                            delete=false;
                            e.printStackTrace();
                        }
                    }
                });
                dismiss();//关闭当前对话框
            }
        });
        //为取消按钮设置点击事件
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();//关闭当前对话框
            }
        });
    }
}


package com.example.applicationtest_two.room;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

// 增加记录的Fragment
public class NoteAddFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置跳转Intent对象来协助Fragment和Activity的交互与通讯
        Intent intent=new Intent();
        // 清空之前栈中所有的Activity，释放资源。
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        // 设置跳转的类
        intent.setClass(getActivity(), NoteAddActivity.class);
        // 开始跳转
        startActivity(intent);
    }


}

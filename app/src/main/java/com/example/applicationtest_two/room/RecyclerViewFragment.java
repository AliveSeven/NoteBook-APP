package com.example.applicationtest_two.room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationtest_two.R;
import com.example.applicationtest_two.room.RecyclerView.NoteAdapter;
import com.example.applicationtest_two.room.RecyclerView.NoteViewModel;
import com.example.applicationtest_two.room.db.entity.Note;

// 记事本主页的Fragment类型
public class RecyclerViewFragment extends Fragment {
    // 声明变量
    private View view;
    private RecyclerView mRecyclerView;
    private NoteAdapter noteAdapter;
    NoteViewModel noteViewModel;
    public RecyclerViewFragment() {}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 设置记事本主页的Fragment绑定的Activity
        view=inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        // 创建Note的RecyclerView适配器
        noteAdapter=new NoteAdapter();
        // 根据note_recycler_view布局文件获得布局对象，即RecyclerView组件的view
        mRecyclerView=view.findViewById(R.id.note_recycler_view);
        //设置RecyclerView的适配器
        mRecyclerView.setAdapter(noteAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        //创建ViewModel，这里默认的ViewModel工厂会向NoteViewModel提供相应的SavedStateHandle，不需要额外添加代码。
        noteViewModel= new ViewModelProvider(this).get(NoteViewModel.class);

        //观察者模式，观察Note数据是否变化，如果变化，将自动更新RecyclerView的UI界面
        noteViewModel.getNotes().observe(getViewLifecycleOwner(), notes -> noteAdapter.submitData(this.getLifecycle(),(PagingData<Note>)notes));
        // 返回视图view
        return view;
    }

    //加载menu
    @Override
    public void  onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // 加载menu的页面
        inflater.inflate(R.menu.recyclerview_menu, menu);
        //应用栏上的搜索组件
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        //为搜索组件绑定事件处理
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {//提交搜索时被自动调用
                //按笔记名模糊查询
                noteViewModel.setNotes(query);
                //recyclerView观察数据
                //观察者模式，观察Note数据是否变化，如果变化，将自动更新RecyclerView的UI界面
                noteViewModel.getNotes().observe(getViewLifecycleOwner(), notes -> noteAdapter.submitData(RecyclerViewFragment.this.getLifecycle(),(PagingData<Note>)notes));
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {//代码变化时被自动调用
                return false;
            }
        });
    }

    @Override//重写方法，处理点击菜单项的事件
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {// 处理点击菜单项的事件
            case R.id.app_bar_add:
                // 导航action跳转到增加记录的Activity
                Navigation.findNavController(view).navigate(
                        R.id.action_navigation_home_to_noteAddActivity);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

}
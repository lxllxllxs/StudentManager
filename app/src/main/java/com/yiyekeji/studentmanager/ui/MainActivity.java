package com.yiyekeji.studentmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiyekeji.studentmanager.R;
import com.yiyekeji.studentmanager.adapter.StudentAdapter;
import com.yiyekeji.studentmanager.bean.Student;
import com.yiyekeji.studentmanager.db.DbUtil;
import com.yiyekeji.studentmanager.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lxl on 2016/12/13.
 */
public class MainActivity extends BaseActivity {
    @InjectView(R.id.edt_keyword)
    EditText edtKeyword;
    @InjectView(R.id.tv_query)
    TextView tvQuery;
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.iv_add)
    ImageView ivAdd;

    StudentAdapter mAdapter;
    List<Student> studentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();

    }

    private void initView() {
        mAdapter=new StudentAdapter(this,studentList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickLitener(new StudentAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                intent.putExtra("student", studentList.get(position));
                startActivity(intent);
            }
        });

        studentList= DbUtil.queryStudent("");
        mAdapter.notifyDataSetChanged(studentList);

    }

    @OnClick({R.id.tv_query, R.id.iv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_query:
                studentList.clear();
                mAdapter.notifyDataSetChanged();
                studentList= DbUtil.queryStudent(edtKeyword.getText().toString());
                mAdapter.notifyDataSetChanged(studentList);
                break;
            case R.id.iv_add:
                startActivity(AddStudentActivity.class);
                break;
        }
    }
}

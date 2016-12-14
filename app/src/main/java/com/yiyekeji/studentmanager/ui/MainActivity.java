package com.yiyekeji.studentmanager.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
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
    private static final int REQUEST_CODE = 0x11;
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
    @InjectView(R.id.tvEmptyView)
    TextView tvEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();

    }

    private void initView() {
        mAdapter = new StudentAdapter(this, studentList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setOnItemClickLitener(new StudentAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
               /* if ( mAdapter.getEdit()){
                    boolean isSelect=studentList.get(position).isSelect();
                    studentList.get(position).setSelect(!isSelect);
                    mAdapter.notifyDataSetChanged();
                }else{
                }*/
                Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                intent.putExtra("student", studentList.get(position));
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        mAdapter.setOnItemLongClickLitener(new StudentAdapter.OnItemLongClickLitener() {
            @Override
            public void onItemLongClick(View view, int position) {
         /*       if (!mAdapter.getEdit()){
                    mAdapter.setEdit();
                }
                showShortToast("longclick");*/
                showDelDialog(position);
            }
        });
        studentList = DbUtil.queryStudent("");
        mAdapter.notifyDataSetChanged(studentList);
    }

    public void showDelDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("删除确认");
        builder.setMessage("确定删除吗？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (DbUtil.delStudent(studentList.get(position))) {
                    showShortToast("删除成功！");
                    refresh();
                } else {
                    showShortToast("删除失败！");
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick({R.id.tv_query, R.id.iv_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_query:
                refresh();
                break;
            case R.id.iv_add:
                Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    private void refresh() {
        tvEmptyView.setVisibility(View.GONE);
        studentList.clear();
        mAdapter.notifyDataSetChanged();
        studentList = DbUtil.queryStudent(edtKeyword.getText().toString());
        if (studentList.size() < 1) {
            tvEmptyView.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged(studentList);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE == requestCode & resultCode == RESULT_OK) {
            refresh();
        }
    }

    /**
     * 连续点击两次 关闭
     */
    long[] mHits = new long[2];

    @Override
    public void onBackPressed() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        //实现左移，然后最后一个位置更新距离开机的时间，如果最后一个时间和最开始时间小于500，即双击
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        showShortToast("再次点击退出应用");
        if (mHits[0] >= (SystemClock.uptimeMillis() - 1000)) {
            System.exit(0);
        }
    }

}

package com.yiyekeji.studentmanager.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiyekeji.studentmanager.R;
import com.yiyekeji.studentmanager.ui.base.BaseActivity;
import com.yiyekeji.studentmanager.view.CircleImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lxl on 2016/12/13.
 */
public class AddStudentActivity extends BaseActivity {
    @InjectView(R.id.iv_head)
    CircleImageView ivHead;
    @InjectView(R.id.edt_name)
    EditText edtName;
    @InjectView(R.id.iv_man)
    ImageView ivMan;
    @InjectView(R.id.iv_female)
    ImageView ivFemale;
    @InjectView(R.id.edt_studentId)
    EditText edtStudentId;
    @InjectView(R.id.edt_phone)
    EditText edtPhone;
    @InjectView(R.id.edt_note)
    EditText edtNote;
    @InjectView(R.id.tv_cancle)
    TextView tvCancle;
    @InjectView(R.id.tv_confirm)
    TextView tvConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.tv_cancle, R.id.tv_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:
                break;
            case R.id.tv_confirm:
                break;
        }
    }
}

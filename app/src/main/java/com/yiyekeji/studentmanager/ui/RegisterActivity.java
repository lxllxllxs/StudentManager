package com.yiyekeji.studentmanager.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yiyekeji.studentmanager.R;
import com.yiyekeji.studentmanager.bean.Administrator;
import com.yiyekeji.studentmanager.db.DbUtil;
import com.yiyekeji.studentmanager.ui.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lxl on 2016/12/13.
 */
public class RegisterActivity extends BaseActivity {
    @InjectView(R.id.edt_name)
    EditText edtName;
    @InjectView(R.id.edt_pwd)
    EditText edtPwd;
    @InjectView(R.id.edt_confirmPwd)
    EditText edtConfirmPwd;
    @InjectView(R.id.tv_cancle)
    TextView tvCancle;
    @InjectView(R.id.tv_confirm)
    TextView tvConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.tv_cancle, R.id.tv_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:
                finish();
                break;
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(edtName.getText().toString())){
                    showShortToast("用户名不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(edtPwd.getText().toString())){
                    showShortToast("密码不能为空！");
                    return;
                }
                if (TextUtils.isEmpty(edtConfirmPwd.getText().toString())){
                    showShortToast("确认密码不能为空！");
                    return;
                }
                if (!edtConfirmPwd.getText().toString().equals(edtPwd.getText().toString())){
                    showShortToast("两次输入密码不一致！");
                    return;
                }
                createAdmin();
                break;
        }
    }


    private void createAdmin() {
        Administrator admin = new Administrator();
        admin.setName(edtName.getText().toString());
        admin.setPwd(edtPwd.getText().toString());
        DbUtil.createAdmin(admin);
        showShortToast("注册成功！");
        finish();
    }
}

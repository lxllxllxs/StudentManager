package com.yiyekeji.studentmanager.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yiyekeji.studentmanager.R;
import com.yiyekeji.studentmanager.db.DbUtil;
import com.yiyekeji.studentmanager.ui.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @InjectView(R.id.edt_name)
    EditText edtName;
    @InjectView(R.id.edt_pwd)
    EditText edtPwd;
    @InjectView(R.id.tv_signUp)
    TextView tvSignUp;
    @InjectView(R.id.tv_signIn)
    TextView tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.tv_signUp, R.id.tv_signIn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_signUp:
                startActivity(RegisterActivity.class);
                break;
            case R.id.tv_signIn:
                if (DbUtil.loginVerfity(edtName.getText().toString(), edtPwd.getText().toString())) {
                    startActivity(MainActivity.class);
                } else {
                    showShortToast("用户名或密码错误！");
                }
                break;
        }
    }
}

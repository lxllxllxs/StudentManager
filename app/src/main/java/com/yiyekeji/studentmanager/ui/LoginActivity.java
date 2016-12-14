package com.yiyekeji.studentmanager.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yiyekeji.studentmanager.R;
import com.yiyekeji.studentmanager.db.DbUtil;
import com.yiyekeji.studentmanager.ui.base.BaseActivity;
import com.yiyekeji.studentmanager.utils.SharedPreferencesUtils;

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
        initView();
    }

    private void initView() {
        edtName.setText((String)SharedPreferencesUtils.getParam(this,"name",""));
        edtPwd.setText((String)SharedPreferencesUtils.getParam(this,"pwd",""));
    }

    @OnClick({R.id.tv_signUp, R.id.tv_signIn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_signUp:
                startActivity(RegisterActivity.class);
                break;
            case R.id.tv_signIn:
                if (DbUtil.loginVerfity(edtName.getText().toString(), edtPwd.getText().toString())) {
                    SharedPreferencesUtils.setParam(this,"name",edtName.getText().toString());
                    SharedPreferencesUtils.setParam(this,"pwd",edtPwd.getText().toString());
                    startActivity(MainActivity.class);
                    finish();
                } else {
                    showShortToast("用户名或密码错误！");
                }
                break;
        }
    }
}

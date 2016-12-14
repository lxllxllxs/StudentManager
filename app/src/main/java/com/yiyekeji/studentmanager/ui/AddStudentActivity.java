package com.yiyekeji.studentmanager.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiyekeji.studentmanager.R;
import com.yiyekeji.studentmanager.bean.Student;
import com.yiyekeji.studentmanager.db.DbUtil;
import com.yiyekeji.studentmanager.ui.base.BaseActivity;
import com.yiyekeji.studentmanager.utils.LogUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lxl on 2016/12/13.
 */
public class AddStudentActivity extends BaseActivity {
    private static final int RESULT_LOAD_IMAGE = 0x123;
    @InjectView(R.id.iv_head)
    ImageView ivHead;
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
    @InjectView(R.id.edt_age)
    EditText edtAge;
    @InjectView(R.id.tvTitle)
    TextView tvTitle;
    private boolean isMan = true;
    private String sex = "男";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        ButterKnife.inject(this);
        initData();
        initView();
    }


    Student student2;

    private void initData() {
        student2 = getIntent().getParcelableExtra("student");
    }

    private void initView() {
        if (student2 != null) {
            if (!TextUtils.isEmpty(student2.getHeadImg())) {
                ivHead.setImageBitmap(BitmapFactory.decodeFile(student2.getHeadImg()));
            }
            tvTitle.setText("修改学生信息");
            edtStudentId.setText(student2.getStudentId());
            edtNote.setText(student2.getNote());
            edtAge.setText(student2.getAge());
            edtPhone.setText(student2.getPhone());
            edtName.setText(student2.getName());
            sex = student2.getSex();
            if (sex.equals("男")) {
                isMan = true;
                ivMan.setImageResource(R.mipmap.ic_select);
            } else {
                isMan = false;
                ivMan.setImageResource(R.mipmap.ic_noselect);
                ivFemale.setImageResource(R.mipmap.ic_select);
            }
        }
    }


    @OnClick({R.id.tv_cancle, R.id.tv_confirm, R.id.iv_man, R.id.iv_female, R.id.iv_head})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
                selectImg();
                break;
            case R.id.tv_cancle:
                finish();
                break;
            case R.id.tv_confirm:
                addOrUpdata();
                break;
            case R.id.iv_man:
                ivMan.setImageResource(R.mipmap.ic_select);
                ivFemale.setImageResource(R.mipmap.ic_noselect);
                sex = "男";
                break;
            case R.id.iv_female:
                sex = "女";
                ivFemale.setImageResource(R.mipmap.ic_select);
                ivMan.setImageResource(R.mipmap.ic_noselect);
                break;
        }
    }

    private void selectImg() {
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    Student student = new Student();

    private void addOrUpdata() {
        if (TextUtils.isEmpty(edtName.getText().toString())) {
            showShortToast("姓名不能为空！");
            return;
        }
        if (TextUtils.isEmpty(edtAge.getText().toString())) {
            showShortToast("年龄不能为空！");
            return;
        }
        if (TextUtils.isEmpty(edtStudentId.getText().toString())) {
            showShortToast("学号不能为空！");
            return;
        }
        student.setName(edtName.getText().toString());
        student.setAge(edtAge.getText().toString());
        student.setNote(edtNote.getText().toString());
        student.setPhone(edtPhone.getText().toString());
        student.setStudentId(edtStudentId.getText().toString());
        student.setSex(sex);

        if (student2 != null) {
            student.setId(student2.getId());
            DbUtil.upDataStudent(student);
            showShortToast("修改成功！");
        } else {
            boolean idSuccess = DbUtil.creatStudent(student);
            if (idSuccess) {
                showShortToast("创建成功！");
            } else {
                showShortToast("学号重复！");
                return;
            }
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            LogUtil.d("onActivity", picturePath);
            cursor.close();
            ivHead.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            student.setHeadImg(picturePath);

        }

    }
}

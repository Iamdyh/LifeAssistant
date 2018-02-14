package cn.com.dyhdev.lifeassistant.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.entity.User;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.ui
 * 文件名:     LoginActivity
 * 作者:       dyh
 * 时间:       2018/1/11 22:39
 * 描述:       注册
 */

public class RegisterActivity extends BaseActivity {

    private static final String TAG = "RegisterActivity";
    @BindView(R.id.id_register_et_user)
    EditText mEtUser;
    @BindView(R.id.id_et_age)
    EditText mEtAge;
    @BindView(R.id.id_et_desc)
    EditText mEtDesc;
    @BindView(R.id.id_radiogroup)
    RadioGroup mRGSex;
    @BindView(R.id.id_register_et_password)
    EditText mEtPassword;
    @BindView(R.id.id_ck_register_et_password)
    EditText mEtCkPassword;
    @BindView(R.id.id_et_email)
    EditText mEtEmail;




//    private EditText mEtUser;
//    private EditText mEtAge;
//    private EditText mEtDesc;
//    private RadioGroup mRGSex;
//    private EditText mEtPassword;
//    private EditText mEtCkPassword;
//    private EditText mEtEmail;
    private Button mBtnRegister;
    private boolean isMan = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

         initView();
    }

    private void initView() {
//        mEtUser = (EditText) findViewById(R.id.id_register_et_user);
//        mEtAge = (EditText) findViewById(R.id.id_et_age);
//        mEtDesc = (EditText) findViewById(R.id.id_et_desc);
//        mEtPassword = (EditText) findViewById(R.id.id_register_et_password);
//        mEtCkPassword = (EditText) findViewById(R.id.id_ck_register_et_password);
//        mEtEmail = (EditText) findViewById(R.id.id_et_email);
//        mRGSex = (RadioGroup) findViewById(R.id.id_radiogroup);

        mBtnRegister = (Button) findViewById(R.id.id_btn_register_re);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.id_btn_register_re:
                        getTextData();
                        break;
                }
            }
        });
    }

    private void getTextData() {
        //获取输入框的值
        String name = mEtUser.getText().toString().trim();
        String age = mEtAge.getText().toString().trim();
        String desc = mEtDesc.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        String password2 = mEtCkPassword.getText().toString().trim();
        String email = mEtEmail.getText().toString().trim();
        isDataEmpty(name, age, password, password2, email, desc);
    }

    /**
     * 判断数据是否为空
     *
     * @param name
     * @param age
     * @param password
     * @param password2
     * @param email
     * @param desc
     */
    private void isDataEmpty(String name, String age, String password, String password2, String email, String desc) {
        //判断是否为空,
        if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(age) &
                !TextUtils.isEmpty(password) & !TextUtils.isEmpty(password2) &
                !TextUtils.isEmpty(email)) {
            Log.i(TAG, "isDataEmpty: false");
            isPasswordMatched(name, age, password, password2, desc, email);
        } else {
            Log.i(TAG, "isDataEmpty: true");
            Toast.makeText(this, R.string.et_remind, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 设置用户信息
     *
     * @param name
     * @param age
     * @param password
     * @param isMan
     * @param desc
     * @param email
     */
    private void setUserData(String name, String age, String password, boolean isMan, String desc, String email) {
        //注册
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        user.setAge(Integer.parseInt(age));
        user.setEmail(email);
        user.setDesc(desc);
        user.setSex(isMan);
        registerUser(user);
    }


    /**
     * 注册账号
     *
     * @param user
     */
    private void registerUser(User user) {
        //调用Bomb的注册方法
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Toast.makeText(RegisterActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.i(TAG, "done: " + e.toString());
                    if (e.toString().contains("203")) {
                        Toast.makeText(RegisterActivity.this, R.string.register_failed_203, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


    /**
     * 判断密码是否匹配
     *
     * @param name
     * @param age
     * @param pass1
     * @param pass2
     * @param desc
     * @param eamil
     */
    private void isPasswordMatched(String name, String age, String pass1, String pass2, String desc, String eamil) {
        if (pass1.equals(pass2)) {
            isMan = setSex();
            desc = setDesc(desc);
            setUserData(name, age, pass1, isMan, desc, eamil);
        } else {
            Toast.makeText(this, R.string.password_mismatch, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断性别
     *
     * @return
     */
    private boolean setSex() {
        //判断性别
        mRGSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.id_rb_man) {
                    isMan = true;
                } else if (checkedId == R.id.id_rb_woman) {
                    isMan = false;
                }
            }
        });
        return isMan;
    }

    /**
     * 简介为空，则设置为默认值
     *
     * @param desc
     * @return
     */
    private String setDesc(String desc) {
        //判断简介是否为空,为空则赋为默认值
        if (TextUtils.isEmpty(desc)) {
            desc = getString(R.string.personal_desc_default);
        }
        return desc;
    }
}

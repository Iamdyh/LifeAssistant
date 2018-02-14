package cn.com.dyhdev.lifeassistant.ui;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.entity.User;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.ui
 * 文件名:     ForgetPasswordActivity
 * 作者:       dyh
 * 时间:       2018/1/17 22:32
 * 描述:       修改/忘记密码
 */

public class ForgetPasswordActivity extends BaseActivity{

    private static final String TAG = "ForgetPasswordActivity";
    @BindView(R.id.id_forget_et_email)
    EditText mEtEmail;
    @BindView(R.id.id_forget_btn_password)
    Button mBtnForget;

//    private EditText mEtNowPass;
//    private EditText mEtNewPass;
//    private EditText mEtNewPass2;
//    private EditText mEtEmail;
//    //    private Button mBtnModify;
//    private Button mBtnForget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);

//        initView();
    }

    @OnClick(R.id.id_forget_btn_password)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.id_forget_btn_password:
                getEmailData();
                break;
        }
    }

//    private void initView() {
//        mEtNowPass = (EditText) findViewById(R.id.id_now_password);
//        mEtNewPass = (EditText) findViewById(R.id.id_new_password);
//        mEtNewPass2 = (EditText) findViewById(R.id.id_new_password_2);
//        mEtEmail = (EditText) findViewById(R.id.id_forget_et_email);

//        mBtnModify = (Button)findViewById(R.id.id_btn_modify);
//        mBtnForget = (Button) findViewById(R.id.id_forget_btn_password);

//        mBtnModify.setOnClickListener(this);
//        mBtnForget.setOnClickListener(this);

//    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
////            case R.id.id_btn_modify:
////                //getPassData();
////                break;
//            case R.id.id_forget_btn_password:
//                getEmailData();
//                break;
//
//        }
//    }

//    private void getPassData(){
//        String now_pass = mEtNowPass.getText().toString().trim();
//        String new_pass = mEtNewPass.getText().toString().trim();
//        String new_pass2 = mEtNewPass2.getText().toString().trim();
//        checkPassData(now_pass, new_pass, new_pass2);
//
//    }

//    private void checkPassData(String old_pass, String new_pass, String new_pass2){
//        //判断是否为空
//        if(!TextUtils.isEmpty(old_pass) & ! TextUtils.isEmpty(new_pass) & !TextUtils.isEmpty(new_pass2)){
//            //判断两次密码是否一致
//            if(new_pass.equals(new_pass2)){
//                updatePassword(old_pass, new_pass);
//            }else{
//                Toast.makeText(this, R.string.password_mismatch, Toast.LENGTH_SHORT).show();
//            }
//        }else{
//            Toast.makeText(this, R.string.et_remind, Toast.LENGTH_SHORT).show();
//        }
//    }


    private void getEmailData() {
        final String email = mEtEmail.getText().toString().trim();
        forgetPassword(email);
    }

//    /**
//     * 修改密码
//     * @param old_pass
//     * @param new_pass
//     */
//    private void updatePassword(String old_pass, String new_pass){
//        //使用Bmob的方法修改密码
//
//        User.updateCurrentUserPassword(old_pass, new_pass, new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e == null) {
//                    Toast.makeText(ForgetPasswordActivity.this, R.string.password_modify_success, Toast.LENGTH_SHORT).show();
//                    finish();
//                } else {
//
//                    Toast.makeText(ForgetPasswordActivity.this, R.string.password_modify_failed, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    /**
     * 忘记密码，通过邮箱重置
     *
     * @param email
     */
    private void forgetPassword(final String email) {
        if (!TextUtils.isEmpty(email)) {
            //调用Bmob的方法
            User.resetPasswordByEmail(email, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Toast.makeText(ForgetPasswordActivity.this, getString(R.string.email_have_send) + email, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgetPasswordActivity.this, R.string.email_error, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, R.string.et_remind, Toast.LENGTH_SHORT).show();
        }

    }


}

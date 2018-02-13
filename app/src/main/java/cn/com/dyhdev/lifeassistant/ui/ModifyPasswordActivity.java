package cn.com.dyhdev.lifeassistant.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.entity.User;

import static cn.com.dyhdev.lifeassistant.ui.LoginActivity.myUser;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.ui
 * 文件名:     ModifyPasswordActivity
 * 作者:       dyh
 * 时间:       2018/2/13 12:09
 * 描述:       修改密码类
 */

public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ModifyPasswordActivity";
    private EditText mEtNowPass;
    private EditText mEtNewPass;
    private EditText mEtNewPass2;
    private Button mBtnModify;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        initView();
    }

    private void initView() {
        mEtNowPass = (EditText)findViewById(R.id.id_now_password);
        mEtNewPass = (EditText)findViewById(R.id.id_new_password);
        mEtNewPass2 = (EditText)findViewById(R.id.id_new_password_2);
        mBtnModify = (Button)findViewById(R.id.id_btn_modify);
        mBtnModify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_btn_modify:
                getPassData();
                break;
        }
    }

    /**
     * 获取数据
     */
    private void getPassData(){
        String now_pass = mEtNowPass.getText().toString().trim();
        String new_pass = mEtNewPass.getText().toString().trim();
        String new_pass2 = mEtNewPass2.getText().toString().trim();
        checkPassData(now_pass, new_pass, new_pass2);

    }

    private void checkPassData(String old_pass, String new_pass, String new_pass2) {
        //判断是否为空
        if (!TextUtils.isEmpty(old_pass) & !TextUtils.isEmpty(new_pass) & !TextUtils.isEmpty(new_pass2)) {
            //判断两次密码是否一致
            if (new_pass.equals(new_pass2)) {
                Log.d(TAG, "checkPassData: 1");
                updatePassword(old_pass, new_pass);
            } else {
                Toast.makeText(this, R.string.password_mismatch, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.et_remind, Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 修改密码
     * @param old_pass
     * @param new_pass
     */
    private void updatePassword(String old_pass, String new_pass){
        //使用Bmob的方法修改密码

        User.updateCurrentUserPassword(old_pass, new_pass, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(ModifyPasswordActivity.this, R.string.password_modify_success, Toast.LENGTH_SHORT).show();
                    finish();
                } else {

                    Toast.makeText(ModifyPasswordActivity.this, R.string.password_modify_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

package cn.com.dyhdev.lifeassistant.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.entity.User;
import cn.com.dyhdev.lifeassistant.utils.SharedUtils;
import cn.com.dyhdev.lifeassistant.utils.StaticClass;
import cn.com.dyhdev.lifeassistant.utils.Utils;
import cn.com.dyhdev.lifeassistant.view.CustomDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.ui
 * 文件名:     LoginActivity
 * 作者:       dyh
 * 时间:       2018/1/11 22:39
 * 描述:       登录
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.id_login_et_user)
    EditText mEtName;
    @BindView(R.id.id_login_et_password)
    EditText mEtPass;
    @BindView(R.id.id_checkbox)     //是否记住密码
            CheckBox mCheckBox;
    @BindView(R.id.id_btn_login)
    Button mBtnLogin;
    @BindView(R.id.id_btn_register)
    Button mBtnRegister;
    @BindView(R.id.id_tv_fgpassword)  //忘记密码
            TextView mTVForget;
    @BindView(R.id.id_login_profile_image)
    CircleImageView mLoginProfileImage;

    private String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private List<String> mPermissionList = new ArrayList<>();
    private boolean mShowRequestPermission = true;//用户是否禁止权限

    private String username;
    private String password;

    public static User myUser;

    private CustomDialog dialog;    //自定义dialog

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        autoObtainPermission();
        myUser = new User();
        initView();
    }

    private void initView() {

        dialog = new CustomDialog(this, 100, 100, R.layout.dialog_loading, R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);

        //设置选中的状态, 默认为false
        boolean isCheck = SharedUtils.getBoolean(this, StaticClass.REMEMBER_PASS, false);
        mCheckBox.setChecked(isCheck);
        if (isCheck) {
            mEtName.setText(SharedUtils.getString(this, StaticClass.USERNAME_KEY, StaticClass.DEFAULT_VALUE));
            mEtPass.setText(SharedUtils.getString(this, StaticClass.PASSWORD_KEY, StaticClass.DEFAULT_VALUE));
            mLoginProfileImage.setImageBitmap(Utils.getImage(this));
        }


    }

    @OnClick({R.id.id_btn_login, R.id.id_btn_register, R.id.id_tv_fgpassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_btn_login:
                getUserData();
                break;
            case R.id.id_btn_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.id_tv_fgpassword:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
        }
    }



    /**
     * 获得用户的数据
     */
    private void getUserData() {
        username = mEtName.getText().toString();
        password = mEtPass.getText().toString();
        doLogin(username, password);
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     */
    private void doLogin(String username, String password) {
        //判断是否为空
        if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(password)) {

            dialog.show();

//            User user = new User();
            myUser.setUsername(username);
            myUser.setPassword(password);
            myUser.login(new SaveListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    dialog.dismiss();
                    //登录成功
                    if (e == null) {
                        //判断邮箱是否验证
                        if (user.getEmailVerified()) {
                            //验证成功，跳到主页
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.email_not_verify, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, R.string.et_remind, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveStatus();
    }

    /**
     * 当用户输入用户名和密码，但是不点击登录，而是直接退出了
     */
    private void saveStatus() {
        //保存状态
        SharedUtils.putBoolean(this, StaticClass.REMEMBER_PASS, mCheckBox.isChecked());

        //是否记住用户名和密码
        if (mCheckBox.isChecked()) {
            //记住用户名和密码
            SharedUtils.putString(this, StaticClass.USERNAME_KEY, mEtName.getText().toString().trim());
            SharedUtils.putString(this, StaticClass.PASSWORD_KEY, mEtPass.getText().toString().trim());
        } else {
            //删除保存的内容
            SharedUtils.deleteData(this, StaticClass.USERNAME_KEY);
            SharedUtils.deleteData(this, StaticClass.PASSWORD_KEY);
        }
    }


    private void autoObtainPermission(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //判断哪些权限未授予
            mPermissionList.clear();
            for(int i = 0; i < permissions.length; i++){
                if(ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED){
                    //未授予的权限存储到mPerrrmissionList中
                    mPermissionList.add(permissions[i]);
                }
            }

            //判断是否为空
            if(mPermissionList.isEmpty()){
                //未授予的权限为空，表示都授予了
            }else{
                //请求权限方法
                String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                for(int i = 0; i < grantResults.length; i++){
                    if(grantResults[i] != PackageManager.PERMISSION_GRANTED){

                        finish();
//                        //判断是否勾选禁止后不再询问
//                        boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, permissions[i]);
//                        if(showRequestPermission){
//                            autoObtainPermission(); //重新申请权限
//                            return;
//                        }else{
//                            mShowRequestPermission = false;
//                        }
                    }
                }

                break;
            default:
                break;
        }
    }
}

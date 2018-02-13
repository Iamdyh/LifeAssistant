package cn.com.dyhdev.lifeassistant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.entity.User;
import cn.com.dyhdev.lifeassistant.ui.LoginActivity;
import cn.com.dyhdev.lifeassistant.ui.ModifyPasswordActivity;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.fragment
 * 文件名:     UserFragment
 * 作者:       dyh
 * 时间:       2018/1/6 14:39
 * 描述:       个人中心
 */

public class UserFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "UserFragment";
    private TextView mTVEditUserInfo;
    private EditText mEditUserName;
    private EditText mEditUserAge;
    private EditText mEditUserSex;
    private EditText mEditUserDesc;
    private Button mBtnModifyPass;
    private Button mBtnModifyInfo;
    private Button mBtnExit;

    private String userName;
    private String userAge;
    private String userSex;
    private String userDesc;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        initView(view);
        return view;
    }

    private void initView(View view){
        mTVEditUserInfo = (TextView)view.findViewById(R.id.id_edit_userinfo);
        mTVEditUserInfo.setOnClickListener(this);
        mEditUserName = (EditText) view.findViewById(R.id.id_edit_uername);
        mEditUserAge = (EditText) view.findViewById(R.id.id_edit_userage);
        mEditUserSex = (EditText) view.findViewById(R.id.id_edit_usersex);
        mEditUserDesc = (EditText) view.findViewById(R.id.id_edit_userdesc);

        //默认是不可点击的
        setEnabled(false);

        //设置具体的值
        User userInfo = BmobUser.getCurrentUser(User.class);
        mEditUserName.setText(userInfo.getUsername());
        mEditUserAge.setText(userInfo.getAge() + "");
        mEditUserSex.setText(userInfo.isSex() ? "男" : "女");
        mEditUserDesc.setText(userInfo.getDesc());


        mBtnModifyInfo = (Button)view.findViewById(R.id.id_btn_check_modify);
        mBtnModifyInfo.setOnClickListener(this);

        mBtnModifyPass = (Button)view.findViewById(R.id.id_btn_modify_password);
        mBtnModifyPass.setOnClickListener(this);

        mBtnExit = (Button)view.findViewById(R.id.id_btn_exit);
        mBtnExit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //编辑资料
            case R.id.id_edit_userinfo:
                setEnabled(true);
                mBtnModifyInfo.setVisibility(View.VISIBLE);
                break;
            case R.id.id_btn_check_modify:
                Log.d(TAG, "onClick: 点击");
                getUserData();
                break;
            //修改密码
            case R.id.id_btn_modify_password:
                startActivity(new Intent(getActivity(), ModifyPasswordActivity.class));
                break;

            //退出登录
            case R.id.id_btn_exit:
                logout();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }

    /**
     * 获取输入的值
     */
    private void getUserData(){
        userName = mEditUserName.getText().toString();
        userAge = mEditUserAge.getText().toString();
        userSex = mEditUserSex.getText().toString();
        userDesc = mEditUserDesc.getText().toString();

        if(!TextUtils.isEmpty(userName) & !TextUtils.isEmpty(userAge) & !TextUtils.isEmpty(userSex)){
            //更新属性
            Log.d(TAG, "getUserData: 数据不为空");
            updateUserInfo(userName, userAge, userSex, userDesc);
        }else{
            Toast.makeText(getActivity(), R.string.et_remind, Toast.LENGTH_SHORT).show();
        }

    }
    private void updateUserInfo(String name, String age, String sex, String desc){
        User user = new User();
        user.setUsername(name);
        user.setAge(Integer.parseInt(age));
        if(sex.equals("男")){
            user.setSex(true);
        }else{
            user.setSex(false);
        }
        if(!TextUtils.isEmpty(desc)){
            user.setDesc(desc);
        }else{
            user.setDesc(getString(R.string.personal_desc_default));
        }

        BmobUser bmobUser = BmobUser.getCurrentUser();
        user.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    //修改成功
                    Log.d(TAG, "done: 修改成功");
                    setEnabled(false);
                    mBtnModifyInfo.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), R.string.userinfo_modify_success, Toast.LENGTH_SHORT).show();
                }else{
                    Log.d(TAG, "done: 修改失败");
                    Toast.makeText(getActivity(), R.string.userinfo_modify_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**
     * 控制焦点
     * @param isEnabled
     */
    private void setEnabled(boolean isEnabled){
        mEditUserName.setEnabled(isEnabled);
        mEditUserAge.setEnabled(isEnabled);
        mEditUserSex.setEnabled(isEnabled);
        mEditUserDesc.setEnabled(isEnabled);
    }
    /**
     * 退出登录
     */
    private void logout(){
        //清除缓存用户对象
        User.logOut();
        //现在的currentUser是null了
        BmobUser currentUser = User.getCurrentUser();

    }
}

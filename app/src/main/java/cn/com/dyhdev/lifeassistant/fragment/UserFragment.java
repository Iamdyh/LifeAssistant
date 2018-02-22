package cn.com.dyhdev.lifeassistant.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.entity.User;
import cn.com.dyhdev.lifeassistant.retrofit.RetrofitUtils;
import cn.com.dyhdev.lifeassistant.ui.ExpressActivity;
import cn.com.dyhdev.lifeassistant.ui.LoginActivity;
import cn.com.dyhdev.lifeassistant.ui.ModifyPasswordActivity;
import cn.com.dyhdev.lifeassistant.ui.PhoneActivity;
import cn.com.dyhdev.lifeassistant.utils.PhotoUtils;
import cn.com.dyhdev.lifeassistant.utils.SharedUtils;
import cn.com.dyhdev.lifeassistant.utils.Utils;
import cn.com.dyhdev.lifeassistant.view.CustomDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.fragment
 * 文件名:     UserFragment
 * 作者:       dyh
 * 时间:       2018/1/6 14:39
 * 描述:       个人中心
 */

public class UserFragment extends Fragment {

    private static final String TAG = "UserFragment";
    //个人头像
    @BindView(R.id.id_profile_image)
    CircleImageView mImage;

    @BindView(R.id.id_edit_userinfo)
    TextView mTVEditUserInfo;
    @BindView(R.id.id_edit_uername)
    EditText mEditUserName;
    @BindView(R.id.id_edit_usersex)
    EditText mEditUserSex;
    @BindView(R.id.id_edit_userage)
    EditText mEditUserAge;
    @BindView(R.id.id_edit_userdesc)
    EditText mEditUserDesc;
    @BindView(R.id.id_btn_check_modify)
    Button mBtnModifyInfo;
    @BindView(R.id.id_tv_logistics_query)
    TextView mTvLogisticsQuery;
    @BindView(R.id.id_tv_location_query)
    TextView mTvLocationQuery;
    @BindView(R.id.id_btn_modify_password)
    Button mBtnModifyPass;
    @BindView(R.id.id_btn_exit)
    Button mBtnExit;
    Unbinder unbinder;





    private Button mBtnPhoto;
    private Button mBtnAlbum;
    private Button mBtnCancel;

    private String userName;
    private String userAge;
    private String userSex;
    private String userDesc;

//    private CircleImageView mImage;

    private CustomDialog mDialog;  //自定义dialog


    public static final int CAMERA_REQUEST_CODE = 1000;
    public static final int ALBUM_REQUEST_CODE = 1001;
    public static final int RESULT_REQUEST_CODE = 1002;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 1003;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 1004;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;
    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {

//        mImage = (CircleImageView) view.findViewById(R.id.id_profile_image);
//        mImage.setOnClickListener(this);
//
//
//        mTVEditUserInfo = (TextView) view.findViewById(R.id.id_edit_userinfo);
//        mTVEditUserInfo.setOnClickListener(this);
//        mEditUserName = (EditText) view.findViewById(R.id.id_edit_uername);
//        mEditUserAge = (EditText) view.findViewById(R.id.id_edit_userage);
//        mEditUserSex = (EditText) view.findViewById(R.id.id_edit_usersex);
//        mEditUserDesc = (EditText) view.findViewById(R.id.id_edit_userdesc);

        //个人信息默认是不可点击的
        setEnabled(false);

        //设置具体的值
        User userInfo = BmobUser.getCurrentUser(User.class);
        mEditUserName.setText(userInfo.getUsername());
        mEditUserAge.setText(userInfo.getAge() + "");
        mEditUserSex.setText(userInfo.isSex() ? "男" : "女");
        mEditUserDesc.setText(userInfo.getDesc());


//        mBtnModifyInfo = (Button) view.findViewById(R.id.id_btn_check_modify);
//        mBtnModifyInfo.setOnClickListener(this);
//
//        mBtnModifyPass = (Button) view.findViewById(R.id.id_btn_modify_password);
//        mBtnModifyPass.setOnClickListener(this);
//
//        mBtnExit = (Button) view.findViewById(R.id.id_btn_exit);
//        mBtnExit.setOnClickListener(this);

        //设置头像

        Bitmap bitmap = Utils.getImage(getActivity());
        showImage(bitmap);

        mDialog = new CustomDialog(getActivity(), 0, 0,
                R.layout.dialog_photo, R.style.pop_anim_style, Gravity.BOTTOM, 0);
        //提示框以外点击无效
        mDialog.setCancelable(false);

        //拍一张照片
        mBtnPhoto = mDialog.findViewById(R.id.id_btn_camera);
        mBtnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                autoObtainCameraPermission();
            }
        });

        //从相册中取出照片
        mBtnAlbum = mDialog.findViewById(R.id.id_btn_album);
        mBtnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                toAlbum();
                autoObtainStoragePermission();
            }
        });

        //取消
        mBtnCancel = mDialog.findViewById(R.id.id_btn_cancel);

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });


    }

    @OnClick({R.id.id_edit_userinfo, R.id.id_profile_image, R.id.id_btn_check_modify,R.id.id_tv_logistics_query, R.id.id_tv_location_query, R.id.id_btn_modify_password, R.id.id_btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //编辑资料
            case R.id.id_edit_userinfo:
                setEnabled(true);
                mBtnModifyInfo.setVisibility(View.VISIBLE);
                break;
            //换头像
            case R.id.id_profile_image:
                mDialog.show();
                break;

            //修改资料
            case R.id.id_btn_check_modify:
                getUserData();
                break;
            //快递查询
            case R.id.id_tv_logistics_query:
                startActivity(new Intent(getActivity(), ExpressActivity.class));
                break;
            //手机号查询
            case R.id.id_tv_location_query:
                startActivity(new Intent(getActivity(), PhoneActivity.class));
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
     * 动态申请sdcard读写权限
     */
    private void autoObtainStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                PhotoUtils.openAlbum(this, ALBUM_REQUEST_CODE);
                mDialog.dismiss();
            }
        } else {
            PhotoUtils.openAlbum(this, ALBUM_REQUEST_CODE );
            mDialog.dismiss();
        }
    }

    /**
     * 申请访问相机权限
     */
    private void autoObtainCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                    Toast.makeText(getActivity(), getString(R.string.permission_deny), Toast.LENGTH_SHORT).show();

                }
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
            } else {//有权限直接调用系统相机拍照
                if (hasSdcard()) {
                    imageUri = Uri.fromFile(fileUri);
                    //通过FileProvider创建一个content类型的Uri
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        imageUri = FileProvider.getUriForFile(getActivity(), "cn.com.dyhdev.lifeassistant.fileprovider", fileUri);
                    }
                    PhotoUtils.takePicture(this, imageUri, CAMERA_REQUEST_CODE);
                    mDialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), R.string.no_sdcard, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: ");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            //通过FileProvider创建一个content类型的Uri
                            imageUri = FileProvider.getUriForFile(getActivity(), "cn.com.dyhdev.lifeassistant.fileprovider", fileUri);
                        }
                        PhotoUtils.takePicture(this, imageUri, CAMERA_REQUEST_CODE);
                    } else {
                        Toast.makeText(getActivity(), R.string.no_sdcard, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.open_camera_permission, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openAlbum(this, ALBUM_REQUEST_CODE);
                } else {
                    Toast.makeText(getActivity(), R.string.open_sdcard_permission, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                //相机数据
                case CAMERA_REQUEST_CODE:
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri,1, 1, OUTPUT_X, OUTPUT_Y, RESULT_REQUEST_CODE);
                    break;
                //相册数据
                case ALBUM_REQUEST_CODE:
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(getActivity(), data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            newUri = FileProvider.getUriForFile(getActivity(), "cn.com.dyhdev.lifeassistant.fileprovider", new File(newUri.getPath()));
                        }
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, RESULT_REQUEST_CODE);
                    } else {
                        Toast.makeText(getActivity(), R.string.no_sdcard, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case RESULT_REQUEST_CODE:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, getActivity());
                    showImage(bitmap);
                    break;


            }
        }
    }

    /**
     * 展示头像
     * @param bitmap
     */
    private void showImage(Bitmap bitmap){
        if (bitmap != null) {
            mImage.setImageBitmap(bitmap);
        }
    }


    /**
     * 获取输入的值
     */
    private void getUserData() {
        userName = mEditUserName.getText().toString();
        userAge = mEditUserAge.getText().toString();
        userSex = mEditUserSex.getText().toString();
        userDesc = mEditUserDesc.getText().toString();

        if (!TextUtils.isEmpty(userName) & !TextUtils.isEmpty(userAge) & !TextUtils.isEmpty(userSex)) {
            //更新属性
            Log.d(TAG, "getUserData: 数据不为空");
            updateUserInfo(userName, userAge, userSex, userDesc);
        } else {
            Toast.makeText(getActivity(), R.string.et_remind, Toast.LENGTH_SHORT).show();
        }

    }

    private void updateUserInfo(String name, String age, String sex, String desc) {
        User user = new User();
        user.setUsername(name);
        user.setAge(Integer.parseInt(age));
        if (sex.equals("男")) {
            user.setSex(true);
        } else {
            user.setSex(false);
        }
        if (!TextUtils.isEmpty(desc)) {
            user.setDesc(desc);
        } else {
            user.setDesc(getString(R.string.personal_desc_default));
        }

        BmobUser bmobUser = BmobUser.getCurrentUser();
        user.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //修改成功
                    Log.d(TAG, "done: 修改成功");
                    setEnabled(false);
                    mBtnModifyInfo.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), R.string.userinfo_modify_success, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "done: 修改失败");
                    Toast.makeText(getActivity(), R.string.userinfo_modify_failed, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 控制焦点
     *
     * @param isEnabled
     */
    private void setEnabled(boolean isEnabled) {
        mEditUserName.setEnabled(isEnabled);
        mEditUserAge.setEnabled(isEnabled);
        mEditUserSex.setEnabled(isEnabled);
        mEditUserDesc.setEnabled(isEnabled);
    }

    /**
     * 退出登录
     */
    private void logout() {
        //清除缓存用户对象
        User.logOut();
        //现在的currentUser是null了
        BmobUser currentUser = User.getCurrentUser();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Utils.saveImage(getActivity(), mImage);
        unbinder.unbind();
    }


}

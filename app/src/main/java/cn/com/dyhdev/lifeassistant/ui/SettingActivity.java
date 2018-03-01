package cn.com.dyhdev.lifeassistant.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.entity.Version;
import cn.com.dyhdev.lifeassistant.retrofit.RetrofitUtils;
import cn.com.dyhdev.lifeassistant.utils.JsonUtils;
import cn.com.dyhdev.lifeassistant.utils.SharedUtils;
import cn.com.dyhdev.lifeassistant.utils.StaticClass;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.view
 * 文件名:     SettingActivity
 * 作者:       dyh
 * 时间:       2018/1/6 15:12
 * 描述:       设置类
 */

public class SettingActivity extends BaseActivity {

    private static final String TAG = "SettingActivity";

    @BindView(R.id.id_setting_speak_switch)
    Switch mSettingSpeakSwitch;
    @BindView(R.id.id_setting_ll_speak)
    LinearLayout mSettingLlSpeak;
    @BindView(R.id.id_setting_ll_update)
    LinearLayout mSettingLlUpdate;
    @BindView(R.id.id_version_code_tv)
    TextView mVersionCodeTv;

    private String versionName;
    private int versionCode;
    private String url;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        boolean isSpeak = SharedUtils.getBoolean(this, "isSpeak", false);
        mSettingSpeakSwitch.setChecked(isSpeak);

        try {
            getVersionNameAndCode();
            mVersionCodeTv.setText(getString(R.string.version_name_default) + versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @OnClick({R.id.id_setting_speak_switch, R.id.id_setting_ll_speak, R.id.id_setting_ll_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //语音开关
            case R.id.id_setting_speak_switch:
                //切换开关
                mSettingSpeakSwitch.setSelected(!mSettingSpeakSwitch.isSelected());
                //保存状态
                SharedUtils.putBoolean(this, "isSpeak", mSettingSpeakSwitch.isChecked());
                break;
            //版本更新
            /**
             * 1、请求服务器的配置文件，拿到versionCode；
             * 2、比较；
             * 3、Dialog提示；
             * 4、跳转到更新界面。
             */
            case R.id.id_setting_ll_update:

                getVersionData();

                break;
            case R.id.id_setting_ll_speak:
                break;
        }
    }


    private void getVersionData(){
        RetrofitUtils.doVersionGetRequest(StaticClass.VERSION_URL, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    final String res = response.body().string();
                    Log.d(TAG, "onResponse: " + res);

                    final Version version = JsonUtils.parsingVersionJson(res);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int code = version.getVersionCode();
                            if(code > versionCode){
                                url = version.getUrl();
                                showUpdateDialog(version.getContent());
                            }else{
                                Toast.makeText(SettingActivity.this, "当前是最新版本", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 展示升级提示框
     */
    private void showUpdateDialog(String content){
        new AlertDialog.Builder(this)
                .setTitle("有新版本！")
                .setMessage(content)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SettingActivity.this, UpdateActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    /**
     * 获取版本号/Code
     */
    private void getVersionNameAndCode() throws PackageManager.NameNotFoundException {
        PackageManager pm = getPackageManager();
        PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
        versionCode = info.versionCode;
        versionName = info.versionName;
    }

}

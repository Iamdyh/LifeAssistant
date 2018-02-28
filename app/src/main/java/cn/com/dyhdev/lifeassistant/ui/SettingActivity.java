package cn.com.dyhdev.lifeassistant.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.utils.SharedUtils;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.view
 * 文件名:     SettingActivity
 * 作者:       dyh
 * 时间:       2018/1/6 15:12
 * 描述:       设置类
 */

public class SettingActivity extends BaseActivity {
    @BindView(R.id.id_setting_speak_switch)
    Switch mSettingSpeakSwitch;
    @BindView(R.id.id_setting_ll_speak)
    LinearLayout mSettingLlSpeak;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        boolean isSpeak = SharedUtils.getBoolean(this, "isSpeak", false);
        mSettingSpeakSwitch.setChecked(isSpeak);

    }

    @OnClick({R.id.id_setting_speak_switch, R.id.id_setting_ll_speak})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_setting_speak_switch:
                //切换开关
                mSettingSpeakSwitch.setSelected(!mSettingSpeakSwitch.isSelected());
                //保存状态
                SharedUtils.putBoolean(this, "isSpeak", mSettingSpeakSwitch.isChecked());

                break;
            case R.id.id_setting_ll_speak:
                break;
        }
    }
}

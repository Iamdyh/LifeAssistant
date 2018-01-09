package cn.com.dyhdev.lifeassistant.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.utils.SharedUtils;
import cn.com.dyhdev.lifeassistant.utils.StaticClass;
import cn.com.dyhdev.lifeassistant.utils.Utils;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.view
 * 文件名:     SplashActivity
 * 作者:       dyh
 * 时间:       2018/1/8 23:22
 * 描述:       闪屏页:1、延时加载 2、判断程序是否第一次运行
 *             3、设置全屏主题(在Mainfest中SplashActivity的Theme设为NoTitleTheme)
 *             4、设置字体
 */

public class SplashActivity extends AppCompatActivity {

    private TextView mSplashTV;

    private  Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case StaticClass.HANDLE_SPLASH:
                    if(isFirstRun()){
                        //第一次运行就跳转到引导页
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    }else{
                        //不是第一次运行就跳转到首页
//                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    }
                    //去掉闪屏页
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    private void initView(){
        //延时2000ms
        mHandler.sendEmptyMessageDelayed(StaticClass.HANDLE_SPLASH, 2000);

        mSplashTV =(TextView) findViewById(R.id.id_tv_splash);

        //设置字体
//        Utils.setTextFont(this, mSplashTV);
    }

    /**
     * 判断程序是否第一次运行
     * @return
     */
    private boolean isFirstRun(){
        boolean isFirstRun = SharedUtils.getBoolean(this, StaticClass.SHARED_IS_FIRST_RUN, true);
        if(isFirstRun){
            //更改标记位，标记已经运行过程序
            SharedUtils.putBoolean(this, StaticClass.SHARED_IS_FIRST_RUN, false);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 禁止返回键，使用户获得更好体验
     */
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}

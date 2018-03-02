package cn.com.dyhdev.lifeassistant.application;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

//腾讯bugly
import com.baidu.mapapi.SDKInitializer;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;
import cn.com.dyhdev.lifeassistant.utils.NetworkUtils;
import cn.com.dyhdev.lifeassistant.utils.StaticClass;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.application
 * 文件名:     BaseApplication
 * 作者:       dyh
 * 时间:       2018/1/3 23:56
 * 描述:       Application
 */

public class BaseApplication extends Application {



    //用于程序初始化工作
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化腾讯bugly：logcat中TAG = CrashReportInfo
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APPID, true);

        //初始化Bomb
        Bmob.initialize(getApplicationContext(), StaticClass.BOMB_APPID);

        //初始化科大讯飞
        // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
        // 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=" + StaticClass.TTS_APPID);

        //初始化百度地图
        SDKInitializer.initialize(getApplicationContext());


    }




}

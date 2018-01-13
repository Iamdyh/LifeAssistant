package cn.com.dyhdev.lifeassistant.application;

import android.app.Application;

//腾讯bugly
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;
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
        Bmob.initialize(this, StaticClass.BOMB_APPID);
    }
}

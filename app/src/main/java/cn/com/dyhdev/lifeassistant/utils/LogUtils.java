package cn.com.dyhdev.lifeassistant.utils;

import android.util.Log;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.utils
 * 文件名:     LogUtils
 * 作者:       dyh
 * 时间:       2018/1/8 22:27
 * 描述:       Log封装类
 */

public class LogUtils {
    public static final boolean DEBUG = true;
    public static final String TAG = "LifeAssistant";

    public static void d(String string){
        if (DEBUG){
            Log.d(TAG, string);
        }
    }
    public static void i(String string){
        if(DEBUG){
            Log.i(TAG, string);
        }
    }

    public static void w(String string){
        if(DEBUG){
            Log.w(TAG, string);
        }
    }

    public static void e(String string){
        if (DEBUG){
            Log.e(TAG, string);
        }
    }
}


package cn.com.dyhdev.lifeassistant.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.utils
 * 文件名:     SharedUtils
 * 作者:       dyh
 * 时间:       2018/1/8 22:45
 * 描述:       SharedPreferences封装类
 */

public class SharedUtils {
    public static final String NAME = "config";

    //存储String类型的值
    public static void putString(Context context, String key, String value){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }
    //获取String类型的值, defValue为默认值
    public static String getString(Context context, String key, String defValue){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    //存储int类型的值
    public static void putInt(Context context, String key, int value){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }
    //获取int类型的值
    public static int getInt(Context context, String key, int defValue){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    public static void putBoolean(Context context, String key, boolean value){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    //删除单个数据
    public static void deleteData(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    //删除全部数据
    public static void deleteAll(Context context){
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}

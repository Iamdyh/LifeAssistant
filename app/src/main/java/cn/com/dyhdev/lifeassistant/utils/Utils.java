package cn.com.dyhdev.lifeassistant.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.utils
 * 文件名:     Utils
 * 作者:       dyh
 * 时间:       2018/1/4 0:10
 * 描述:       工具类
 */

public class Utils {

    /**
     * 设置字体
     * @param context
     * @param textView
     */
    public static void setTextFont(Context context, TextView textView){
        Typeface fontType = Typeface.createFromAsset(context.getAssets(), "fonts/xxx");
        textView.setTypeface(fontType);
    }
}

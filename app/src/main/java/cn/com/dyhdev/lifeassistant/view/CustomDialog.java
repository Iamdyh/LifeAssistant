package cn.com.dyhdev.lifeassistant.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import cn.com.dyhdev.lifeassistant.R;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.view
 * 文件名:     CustomDialog
 * 作者:       dyh
 * 时间:       2018/1/21 11:57
 * 描述:       自定义Dialog
 */

public class CustomDialog extends Dialog{

    /**
     * 定义模板
     * @param context
     * @param layout
     * @param style
     */
    public CustomDialog(@NonNull Context context, int layout, int style) {

        this(context, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                layout, style, Gravity.CENTER);
    }

    /**
     * 定义属性
     * @param context
     * @param width
     * @param height
     * @param layout
     * @param style
     * @param gravity
     * @param anim
     */
    public CustomDialog(@NonNull Context context, int width, int height, int layout, int style, int gravity, int anim) {
        super(context, style);

        //设置属性
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = width;
        layoutParams.height = height;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(anim);
    }

    /**
     * 实例
     * @param context
     * @param width
     * @param height
     * @param layout
     * @param style
     * @param gravity
     */
    public CustomDialog(@NonNull Context context, int width, int height, int layout, int style, int gravity) {
       this(context, width, height, layout, style, gravity, R.style.pop_anim_style);
    }
}

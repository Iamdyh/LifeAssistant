package cn.com.dyhdev.lifeassistant.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

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

    //保存头像
    public static void saveImage(Context context, CircleImageView image){
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //将Bitmap压缩成字节数组输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //利用Base64将字节数组输出流转换成String
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String string = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        //将string保存在sharedutils中
        SharedUtils.putString(context, "image", string);
    }

    //获取头像
    public static Bitmap getImage(Context context){
        String imageString = SharedUtils.getString(context, "image", "");
        if(!imageString.equals("")){
            //利用Base64将String转换
            byte[] bytes = Base64.decode(imageString, Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            //生成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
            return bitmap;
        }
        return null;
    }
}

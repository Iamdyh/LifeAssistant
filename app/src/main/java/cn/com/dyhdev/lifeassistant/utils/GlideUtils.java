package cn.com.dyhdev.lifeassistant.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.utils
 * 文件名:     GlideUtils
 * 作者:       dyh
 * 时间:       2018/2/27 13:04
 * 描述:       图片加载工具类
 */

public class GlideUtils {

    /**
     * 加载图片(原大小)
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImageView(Context context, String url, ImageView imageView){
        Glide.with(context).load(url).into(imageView);
    }

    /**
     * 加载图片(指定大小)
     * @param context
     * @param url
     * @param width
     * @param height
     * @param imageView
     */
    public static void loadImageViewBySize(Context context, String url, int width, int height, ImageView imageView){
        Glide.with(context).load(url).override(width, height).into(imageView);
    }

    /**
     * 加载图片出错时，给一个默认图片
     * @param context
     * @param url
     * @param loadImage
     * @param errorImage
     * @param imageView
     */
    public static void loadImageViewError(Context context, String url, int loadImage, int errorImage, ImageView imageView){
        Glide.with(context).load(url).placeholder(loadImage).error(errorImage).into(imageView);
    }

    /**
     * 加载图片，使用缓存ji'zh
     * @param context
     * @param url
     * @param target
     */
    public static void loadImageViewCache(Context context, String url, SimpleTarget<Bitmap> target){
        Glide.with(context).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(target);

    }
}

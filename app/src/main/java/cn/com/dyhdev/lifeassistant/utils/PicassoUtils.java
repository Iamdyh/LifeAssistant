package cn.com.dyhdev.lifeassistant.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.utils
 * 文件名:     PicassoUtils
 * 作者:       dyh
 * 时间:       2018/2/23 0:35
 * 描述:       图片加载工具类
 */

public class PicassoUtils {

    /**
     * 加载图片（原大小）
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImageView(Context context, String url, ImageView imageView){
        Picasso.with(context).load(url).into(imageView);
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
        Picasso.with(context).load(url).resize(width, height).centerCrop().into(imageView);
    }

    /**
     * 加载图片(默认图片)
     * @param context
     * @param url
     * @param loadImg
     * @param errorImg
     * @param imageView
     */
    public static void loadImageViewDefalut(Context context, String url, int loadImg, int errorImg, ImageView imageView){
        Picasso.with(context).load(url).placeholder(loadImg).error(errorImg).into(imageView);
    }

    /**
     * 裁剪图片
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImageViewCrop(Context context, String url, ImageView imageView){
        Picasso.with(context).load(url).transform(new CropSquareTransformation()).into(imageView);
    }

    /**
     * 按比例裁剪（矩形）
     */
    public static class CropSquareTransformation implements Transformation{
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap bitmap = Bitmap.createBitmap(source, x, y, size,size);
            if(bitmap != source){
                source.recycle();
            }
            return bitmap;
        }

        @Override
        public String key() {
            return "flag";
        }
    }
}

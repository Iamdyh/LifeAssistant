package cn.com.dyhdev.lifeassistant.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.entity.Beauty;
import cn.com.dyhdev.lifeassistant.retrofit.OnItemClickListener;
import cn.com.dyhdev.lifeassistant.utils.GlideUtils;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.adapter
 * 文件名:     BeautyAdapter
 * 作者:       dyh
 * 时间:       2018/2/27 13:27
 * 描述:       美女图片适配器
 */

public class BeautyAdapter extends RecyclerView.Adapter<BeautyAdapter.ViewHolder>{

    private Context mContext;
    private List<Beauty> mList;
    private OnItemClickListener mOnItemClickListener;

    public BeautyAdapter(Context context, List<Beauty> list){
        this.mContext = context;
        this.mList = list;

    }

    public void setOnItemClickLister(OnItemClickListener onItemClickLister){
        this.mOnItemClickListener = onItemClickLister;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_girls_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //动态设置每个Item的高度

        final int screenWidth = setScreenWidth();
        String url = mList.get(position).getUrl();
        GlideUtils.loadImageViewCache(mContext, url, new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                    int width = resource.getWidth();
                    int height = resource.getHeight();
                    int realHeight = screenWidth * height / width / 2;
                   // mList.get(holder.getAdapterPosition()).setHeight(realHeight);
                    ViewGroup.LayoutParams lp = holder.imageView.getLayoutParams();
                    lp.height = realHeight;
                    if(width < screenWidth / 2){
                        lp.width = screenWidth / 2;
                    }
                    holder.imageView.setImageBitmap(resource);
            }
        });

        if(mOnItemClickListener != null){
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
            holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(position);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public int setScreenWidth(){
        WindowManager windowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        final int screenWidth = dm.widthPixels;
        return screenWidth;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        ViewHolder(View view){
            super(view);
            imageView = (ImageView)view.findViewById(R.id.id_beauty_img);

        }
    }
}

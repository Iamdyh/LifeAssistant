package cn.com.dyhdev.lifeassistant.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.entity.ITNews;
import cn.com.dyhdev.lifeassistant.retrofit.OnItemClickListener;
import cn.com.dyhdev.lifeassistant.utils.PicassoUtils;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.adapter
 * 文件名:     ITNewsAdapter
 * 作者:       dyh
 * 时间:       2018/2/22 18:45
 * 描述:       IT资讯适配器
 */

public class ITNewsAdapter extends RecyclerView.Adapter<ITNewsAdapter.ItNewsViewHolder>{

    private List<ITNews> mList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private int width=230, height=230;

    public ITNewsAdapter(List<ITNews> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void setOnItemClickLister(OnItemClickListener onItemClickLister){
        this.mOnItemClickListener = onItemClickLister;
    }
    @Override
    public ItNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_it_news_item, parent, false);
        ItNewsViewHolder viewHolder = new ItNewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItNewsViewHolder holder, final int position) {
        holder.mTvTitle.setText(mList.get(position).getTitle());
        holder.mTvSource.setText(mContext.getString(R.string.news_source) + mList.get(position).getSource());
        holder.mTvTime.setText(mList.get(position).getTime());
        PicassoUtils.loadImageViewBySize(mContext, mList.get(position).getPicUrl(), width, height, holder.mImageView);
        if(mOnItemClickListener != null){
            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position);
                }
            });
            holder.mLayout.setOnLongClickListener(new View.OnLongClickListener() {
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

    public class ItNewsViewHolder extends RecyclerView.ViewHolder{

        LinearLayout mLayout;
        ImageView mImageView;
        TextView mTvTitle;
        TextView mTvSource;
        TextView mTvTime;

        public ItNewsViewHolder(View view){
            super(view);
            mLayout = (LinearLayout)view.findViewById(R.id.id_layout_news_item);
            mImageView = (ImageView)view.findViewById(R.id.id_news_img);
            mTvTitle = (TextView) view.findViewById(R.id.id_news_tv_title);
            mTvSource = (TextView)view.findViewById(R.id.id_news_tv_source);
            mTvTime = (TextView)view.findViewById(R.id.id_news_tv_time);
        }

    }

}

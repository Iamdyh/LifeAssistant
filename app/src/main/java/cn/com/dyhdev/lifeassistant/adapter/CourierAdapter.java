package cn.com.dyhdev.lifeassistant.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.entity.CourierDean;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.adapter
 * 文件名:     CourierAdapter
 * 作者:       dyh
 * 时间:       2018/2/20 15:20
 * 描述:       快递信息的适配器
 */

public class CourierAdapter extends RecyclerView.Adapter<CourierAdapter.ViewHolder> {


    private List<CourierDean> mList;


    public CourierAdapter(List<CourierDean> list){
        this.mList = list;
    }

    public void updateData(List<CourierDean> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_courier_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //绑定数据
        holder.mTVRemark.setText(mList.get(position).getRemark());
        holder.mTVZone.setText(mList.get(position).getZone());
        holder.mTVDatetime.setText(mList.get(position).getDatetime());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTVRemark;
        TextView mTVZone;
        TextView mTVDatetime;

        public ViewHolder(View itemView){
            super(itemView);
            mTVRemark = (TextView)itemView.findViewById(R.id.id_tv_remark);
            mTVZone = (TextView)itemView.findViewById(R.id.id_tv_zone);
            mTVDatetime = (TextView)itemView.findViewById(R.id.id_tv_datetime);
        }
    }
}

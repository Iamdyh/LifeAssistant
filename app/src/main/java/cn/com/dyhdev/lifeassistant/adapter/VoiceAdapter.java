package cn.com.dyhdev.lifeassistant.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.entity.ChatMessage;
import cn.com.dyhdev.lifeassistant.entity.Message;
import cn.com.dyhdev.lifeassistant.utils.Utils;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.adapter
 * 文件名:     VoiceAdapter
 * 作者:       dyh
 * 时间:       2018/2/21 16:08
 * 描述:       聊天适配器
 */

public class VoiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    //左边
    public static final int TYPE_RECEIVED = 0;
    //右边
    public static final int TYPE_SEND = 1;

    private List<Message> mList;
    private Context mContext;


    public VoiceAdapter(List<Message> list, Context context) {
        this.mList = list;
        this.mContext = context;
    }

    /**
     * 添加数据并刷新界面
     * @param msg
     */
    public void addData(Message msg){
        mList.add(mList.size(), msg);
        notifyDataSetChanged();
        //notifyItemInserted(mList.size());
    }

    /**
     * 根据不同的类型返回不同的布局
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        Message message = mList.get(position);
        int type = message.getType();
        return type;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if(viewType == TYPE_SEND){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_right_item, parent, false);
            return new UserSendMsgViewHoder(view);
        }else if(viewType == TYPE_RECEIVED){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_left_item, parent, false);
            return new SiriSendMsgViewHoder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof UserSendMsgViewHoder){
            Message message = mList.get(position);
            ((UserSendMsgViewHoder) holder).mTVSendMsg.setText(message.getmContent());
            ((UserSendMsgViewHoder) holder).mImage.setImageBitmap(Utils.getImage(mContext));
        }else if(holder instanceof SiriSendMsgViewHoder){
            Message message = mList.get(position);
            ((SiriSendMsgViewHoder) holder).mTVSiriMsg.setText(message.getmContent());

        }
    }

    //定义两个内部类
    //右边
    public static class UserSendMsgViewHoder extends RecyclerView.ViewHolder{
        TextView mTVSendMsg;        //发送的消息
        CircleImageView mImage;     //用户头像

        public UserSendMsgViewHoder(View view){
            super(view);
            mTVSendMsg = (TextView)view.findViewById(R.id.id_voice_tv_send);
            mImage = (CircleImageView)view.findViewById(R.id.id_voice_profile_image);
        }
    }
    //左边
    public static class SiriSendMsgViewHoder extends RecyclerView.ViewHolder{
        TextView mTVSiriMsg;
        ImageView mImageSiri;

        public SiriSendMsgViewHoder(View view){
            super(view);
            mTVSiriMsg = (TextView)view.findViewById(R.id.id_voice_tv_siri);
            mImageSiri = (ImageView)view.findViewById(R.id.id_voice_image_siri);
        }
    }


}

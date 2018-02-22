package cn.com.dyhdev.lifeassistant.retrofit;

import android.view.View;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.retrofit
 * 文件名:     OnItemClickListener
 * 作者:       dyh
 * 时间:       2018/2/22 22:35
 * 描述:       RecyclerView的item点击事件的回调接口
 */

public interface OnItemClickListener {
    void onItemClick(int position);
    void onItemLongClick(int position);
}

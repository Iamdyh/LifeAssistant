package cn.com.dyhdev.lifeassistant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.adapter.ITNewsAdapter;
import cn.com.dyhdev.lifeassistant.adapter.VoiceAdapter;
import cn.com.dyhdev.lifeassistant.entity.ITNews;
import cn.com.dyhdev.lifeassistant.retrofit.OnItemClickListener;
import cn.com.dyhdev.lifeassistant.retrofit.RetrofitUtils;
import cn.com.dyhdev.lifeassistant.ui.WebViewActivity;
import cn.com.dyhdev.lifeassistant.utils.JsonUtils;
import cn.com.dyhdev.lifeassistant.utils.StaticClass;
import cn.com.dyhdev.lifeassistant.view.CustomDecoration;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.fragment
 * 文件名:     ArticleFragment
 * 作者:       dyh
 * 时间:       2018/1/6 14:33
 * 描述:       IT资讯
 */

public class ArticleFragment extends Fragment {

    private static final String TAG = "ArticleFragment";

    @BindView(R.id.id_news_swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.id_news_recyclerview)
    RecyclerView mRecyclerView;
    Unbinder unbinder;

    private ITNewsAdapter mAdapter;
    private List<ITNews> mCurrentList = new ArrayList<>();
    private List<ITNews> mLastList = new ArrayList<>();
    private String num = "30";       //默认加载30条数据

    private boolean isFirst = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_articals, null);
        unbinder = ButterKnife.bind(this, view);

        isFirst = true;
        getDataFromServer();

        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshNews();
            }
        });



        return view;
    }

    private void  refreshNews(){
        getDataFromServer();
        mSwipeRefresh.setRefreshing(false);
    }
    private void setRecyclerView(final List<ITNews> list){
        mAdapter = new ITNewsAdapter(list, getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new CustomDecoration(getActivity(), CustomDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mAdapter);
        //设置点击事件
        mAdapter.setOnItemClickLister(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "onItemClick: " + position);
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("title", mCurrentList.get(position).getTitle());
                intent.putExtra("url", mCurrentList.get(position).getUrl());
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(int position) {

            }
        });
    }

    private void getDataFromServer(){
        RetrofitUtils.doITNewsGetRequest(StaticClass.IT_NEWS_URL, StaticClass.NEWS_APPKEY, num, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    final String res = response.body().string();
                    Log.d(TAG, "onResponse: " + res);
                    mCurrentList = JsonUtils.parsingITNewsJson(res);

                    Log.d(TAG, "onResponse2: " + mCurrentList);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(isFirst){
                                isFirst = false;
                                mLastList = mCurrentList;
                                setRecyclerView(mCurrentList);
                                mAdapter.notifyDataSetChanged();
                            }else{

                                int count = compareData(mLastList, mCurrentList);
                                if(count == 0){
                                    Toast.makeText(getActivity(), "暂无新内容", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getActivity(), "已更新" + count + "条新闻", Toast.LENGTH_SHORT).show();
                                    mLastList = mCurrentList;
                                    setRecyclerView(mCurrentList);
                                    mAdapter.notifyDataSetChanged();
                                }

                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private int compareData(List<ITNews> last, List<ITNews> current){
        int count = 0;
        count = current.size() - last.size();
        if(count == 0){
            for(int i = 0; i < current.size(); i++){
                if(!last.get(i).getTitle().equals(current.get(i).getTitle())){
                    count++;
                }
            }
        }
        return count;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

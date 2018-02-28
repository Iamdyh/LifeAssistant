package cn.com.dyhdev.lifeassistant.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.adapter.BeautyAdapter;
import cn.com.dyhdev.lifeassistant.entity.Beauty;
import cn.com.dyhdev.lifeassistant.retrofit.OnItemClickListener;
import cn.com.dyhdev.lifeassistant.retrofit.RetrofitUtils;
import cn.com.dyhdev.lifeassistant.utils.GlideUtils;
import cn.com.dyhdev.lifeassistant.utils.JsonUtils;
import cn.com.dyhdev.lifeassistant.utils.StaticClass;
import cn.com.dyhdev.lifeassistant.view.CustomDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.fragment
 * 文件名:     PictureFragment
 * 作者:       dyh
 * 时间:       2018/1/6 14:36
 * 描述:       美女福利
 */

public class PictureFragment extends Fragment {

    private static final String TAG = "PictureFragment";

    @BindView(R.id.id_beauty_recyclerview)
    RecyclerView mBeautyRecyclerview;
    Unbinder unbinder;
    @BindView(R.id.id_beauty_refresh)
    SwipeRefreshLayout mBeautyRefresh;

    private List<Beauty> mList = new ArrayList<>();

    private BeautyAdapter mAdapter;

    private int num = 50;   //默认加载50张图片
    private String werfale = "福利";

    private CustomDialog mDialog;
    private PhotoView mDialogImg;
    private PhotoViewAttacher mPhotoViewAttacher;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture, null);
        unbinder = ButterKnife.bind(this, view);


        getBeautyImageData();



        mBeautyRefresh.setColorSchemeResources(R.color.colorPrimary);
        mBeautyRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshPictures();
            }
        });

        mDialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, R.layout.dialog_beauty,
                R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);

        mDialogImg = mDialog.findViewById(R.id.id_dialog_beauty_img);



        return view;
    }


    private void refreshPictures(){
        getBeautyImageData();
        mBeautyRefresh.setRefreshing(false);
    }

    private void setRecyclerView(final List<Beauty> list) {
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mAdapter = new BeautyAdapter(getActivity(), list);
        mBeautyRecyclerview.setLayoutManager(layoutManager);

        mBeautyRecyclerview.setAdapter(mAdapter);

        mAdapter.setOnItemClickLister(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                GlideUtils.loadImageView(getActivity(), list.get(position).getUrl(), mDialogImg);
                //缩放
                mPhotoViewAttacher = new PhotoViewAttacher(mDialogImg);
                //刷新
                mPhotoViewAttacher.update();
                mDialog.show();
            }

            @Override
            public void onItemLongClick(int position) {

            }
        });



    }



    private void getBeautyImageData() {
        RetrofitUtils.doBeautyImageGetRequest(StaticClass.BEAUTY_URL, werfale, num, new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    final String res = response.body().string();
                    mList = JsonUtils.parsingBeautyDataJson(res);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setRecyclerView(mList);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.id_beauty_refresh)
    public void onViewClicked() {
    }
}

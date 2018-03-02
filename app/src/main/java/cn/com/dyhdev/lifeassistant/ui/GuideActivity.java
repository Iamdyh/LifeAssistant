package cn.com.dyhdev.lifeassistant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.dyhdev.lifeassistant.R;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.ui
 * 文件名:     GuideActivity
 * 作者:       dyh
 * 时间:       2018/1/8 23:44
 * 描述:       引导页
 */

public class GuideActivity extends AppCompatActivity {

    //viewpager
    @BindView(R.id.id_guide_viewpager)
    ViewPager mViewPager;
    //小圆点
    @BindView(R.id.id_img_point_01)
    ImageView point_01;
    @BindView(R.id.id_img_point_02)
    ImageView point_02;
    @BindView(R.id.id_img_point_03)
    ImageView point_03;
    @BindView(R.id.id_img_point_04)
    ImageView point_04;
    //引导页跳过
    @BindView(R.id.id_img_jump)
    ImageView image_jump;



    //容器
    private List<View> mViewList = new ArrayList<>();

    View view_01;
    View view_02;
    View view_03;
    View view_04;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);



        ButterKnife.bind(this);
        initview();
    }

    /**
     * 初始化view
     */
    private void initview() {


        //设置小圆点的默认图片
        setPointImage(true, false, false, false);
        view_01 = View.inflate(this, R.layout.view_page_item_one, null);
        view_02 = View.inflate(this, R.layout.view_page_item_two, null);
        view_03 = View.inflate(this, R.layout.view_page_item_three, null);
        view_04 = View.inflate(this, R.layout.view_page_item_four, null);




        //给buttn设置点击事件
        view_04.findViewById(R.id.id_btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
            }
        });

        mViewList.add(view_01);
        mViewList.add(view_02);
        mViewList.add(view_03);
        mViewList.add(view_04);

        //设置适配器
        mViewPager.setAdapter(new GuideAdapter());

        //监听ViewPager滑动
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //pager切换，
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setPointImage(true, false, false, false);
                        setPointStatus(View.VISIBLE);
                        break;
                    case 1:
                        setPointImage(false, true, false, false);
                        setPointStatus(View.VISIBLE);
                        break;
                    case 2:
                        setPointImage(false, false, true, false);
                        setPointStatus(View.VISIBLE);
                        break;
                    case 3:
                        setPointImage(false, false, false, true);
                        setPointStatus(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    @OnClick(R.id.id_img_jump)
    public void onViewClicked(View view) {
        switch (view.getId()){

            case R.id.id_img_jump:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }

    }




    class GuideAdapter extends PagerAdapter {
        //设置item数量
        @Override
        public int getCount() {
            return mViewList.size();
        }

        //对比，是否滑动、切换
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        //添加item
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ((ViewPager) container).addView(mViewList.get(position));
            return mViewList.get(position);
        }
        //删除item


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            ((ViewPager) container).removeView(mViewList.get(position));
        }
    }

    /**
     * 设置小圆点的状态
     *
     * @param isSelect_01
     * @param isSelect_02
     * @param isSelect_03
     */
    private void setPointImage(boolean isSelect_01, boolean isSelect_02, boolean isSelect_03, boolean isSelect_04) {
        if (isSelect_01) {
            point_01.setBackgroundResource(R.drawable.point_on);
        } else {
            point_01.setBackgroundResource(R.drawable.point_off);
        }

        if (isSelect_02) {
            point_02.setBackgroundResource(R.drawable.point_on);
        } else {
            point_02.setBackgroundResource(R.drawable.point_off);
        }

        if (isSelect_03) {
            point_03.setBackgroundResource(R.drawable.point_on);
        } else {
            point_03.setBackgroundResource(R.drawable.point_off);
        }

        if (isSelect_04) {
//            point_01.setVisibility(View.GONE);
//            point_02.setVisibility(View.GONE);
//            point_03.setVisibility(View.GONE);
//            point_04.setVisibility(View.GONE);

        } else {
            point_04.setBackgroundResource(R.drawable.point_off);
        }
    }

    private void setPointStatus(int visibilityStatus) {

        image_jump.setVisibility(visibilityStatus);
        point_01.setVisibility(visibilityStatus);
        point_02.setVisibility(visibilityStatus);
        point_03.setVisibility(visibilityStatus);
        point_04.setVisibility(visibilityStatus);
    }
}

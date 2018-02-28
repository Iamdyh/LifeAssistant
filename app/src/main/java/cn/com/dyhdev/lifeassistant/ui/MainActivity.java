package cn.com.dyhdev.lifeassistant.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;



import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.dyhdev.lifeassistant.R;
import cn.com.dyhdev.lifeassistant.fragment.ArticleFragment;
import cn.com.dyhdev.lifeassistant.fragment.PictureFragment;
import cn.com.dyhdev.lifeassistant.fragment.UserFragment;
import cn.com.dyhdev.lifeassistant.fragment.VoiceFragment;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";


    @BindView(R.id.id_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.id_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.id_fab_setting)
    FloatingActionButton mFabSetting; //悬浮按钮

    private List<String> mTitles;        //标题数据源
    private List<Fragment> mFragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //去掉tablayout上的阴影
        getSupportActionBar().setElevation(0);

        initData();

        initView();

        //测试bugly
//        CrashReport.testJavaCrash();
    }

    /**
     * 初始化数据
     */
    private void initData(){
        mTitles = new ArrayList<>();

        mTitles.add(getString(R.string.it_articles));
        mTitles.add(getString(R.string.beauty_welfare));
        mTitles.add(getString(R.string.voice_chat));
        mTitles.add(getString(R.string.personal_center));

        mFragments = new ArrayList<>();

        mFragments.add(new ArticleFragment());
        mFragments.add(new PictureFragment());
        mFragments.add(new VoiceFragment());
        mFragments.add(new UserFragment());

    }

    /**
     * 初始化view
     */
    private void initView(){

//        mTabLayout = (TabLayout) findViewById(R.id.id_tabLayout);
//        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
//        mFabSetting = (FloatingActionButton) findViewById(R.id.id_fab_setting);

        //设置悬浮按钮点击事件
//        mFabSetting.setOnClickListener(this);
        //设置按钮默认隐藏
//        mFabSetting.setVisibility(View.GONE);

        //预加载viewpager
        mViewPager.setOffscreenPageLimit(mFragments.size());

        //viewpager滑动监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //选中状态
            @Override
            public void onPageSelected(int position) {
                //首页position为0,语音助手为2
                if(position == 2){
                    mFabSetting.setVisibility(View.GONE);
                }else{
                    mFabSetting.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //设置Adapter
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            //设置选中的item
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }
            //设置返回item的个数
            @Override
            public int getCount() {
                return mFragments.size();
            }
            //设置标题
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles.get(position);
            }
        });


        //绑定viewpager
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @OnClick(R.id.id_fab_setting)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_fab_setting:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}

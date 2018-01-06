package cn.com.dyhdev.lifeassistant.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.dyhdev.lifeassistant.R;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.fragment
 * 文件名:     ArticleFragment
 * 作者:       dyh
 * 时间:       2018/1/6 14:33
 * 描述:       微信精选
 */

public class ArticleFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_articals, null);
        return view;
    }
}

package com.example.beijingnews.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.beijingnews.domain.NewsCenterPagerBean;

import java.util.List;

public abstract class BaseFragment extends Fragment {

    // MainActivity
    public Activity context;

    /**
     * 当Fragment被创建的时候回调这个方法
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    /**
     * 当视图被创建的时候回调
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    /**
     * 让孩子实现自己的视图，达到自己特有的效果
     * @return
     */
    public abstract View initView();

    /**
     * 当Activity被创建之后被回调
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 1.如果子页面没有数据，联网请求数据，并且绑定到initView初始化的视图上
     * 2.有数据就直接绑定到initView初始化的视图上
     */
   public void initData(){
   };


}

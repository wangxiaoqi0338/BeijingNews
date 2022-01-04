package com.example.beijingnews.menudetailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.beijingnews.base.MenuDetailBasePager;
import com.example.beijingnews.utils.LogUtil;

/**
 * 专题详情页面
 */
public class TopicMenuDetailPager extends MenuDetailBasePager {

    private  TextView textView;
    public TopicMenuDetailPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("专题详情页面数据被详情化了");
        textView.setText("专题详情页面内容");
    }
}

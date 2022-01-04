package com.example.beijingnews.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.beijingnews.R;
import com.example.beijingnews.activity.MainActivity;

public class BasePager {

    /**
     * 上下文
     */
    public  final Context context;//MainActivity

    /**
     * 视图,代表各个不同的页面
     */
    public  View rootView;

    /**
     * 显示标题
     * @param context
     */
    public TextView tv_title;

    /**
     * 点击侧滑
     */
    public ImageButton ib_menu;

    /**
     * 加载各个子页面
     */
    public FrameLayout fl_content;

    public ImageButton ib_swich_list_grid;

    public BasePager(Context context){
        this.context = context;
        //构造方法一执行，视图就被初始化了
        rootView = initView();
    }

    /**
     * 用于初始化公共部分视图，并且初始化加载子视图的FrameLayout
     * @return
     */
    private View initView() {
        //基类的页面
        View view = View.inflate(context, R.layout.base_pager,null);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        ib_menu = (ImageButton) view.findViewById(R.id.ib_menu);
        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);
        ib_swich_list_grid = (ImageButton) view.findViewById(R.id.ib_swich_list_grid);
        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();//关<->开
            }
        });
        return view;
    }

    /**
     * 初始化数据，当孩子需要初始化数据，或者绑定数据，联网请求数据并且绑定的时候，重写该方法
     */
    public void initData(){

    }
}

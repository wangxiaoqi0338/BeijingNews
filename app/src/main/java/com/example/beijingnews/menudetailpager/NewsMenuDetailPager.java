package com.example.beijingnews.menudetailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.beijingnews.R;
import com.example.beijingnews.activity.MainActivity;
import com.example.beijingnews.base.MenuDetailBasePager;
import com.example.beijingnews.domain.NewsCenterPagerBean;
import com.example.beijingnews.menudetailpager.tabledetailpager.TabDetailPager;
import com.example.beijingnews.utils.LogUtil;
import com.google.android.material.tabs.TabLayout;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻详情页面
 */
public class NewsMenuDetailPager extends MenuDetailBasePager {

    @ViewInject(R.id.tabPageIndicator)
    private TabLayout tabPageIndicator;

    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;

    @ViewInject(R.id.ib_tab_next)
    private ImageButton ib_tab_next;

    /**
     * 页签页面的数据集合-数据
     */
    private List<NewsCenterPagerBean.DataEntity.ChildrenEntity> children;
    /**
     * 页签页面的集合-页面
     */
    private ArrayList<TabDetailPager> tabDetailPagers;


    public NewsMenuDetailPager(Context context, NewsCenterPagerBean.DataEntity dataEntity) {
        super(context);
        children = dataEntity.getChildren();
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.newsmenu_detail_pager, null);
        x.view().inject(NewsMenuDetailPager.this, view);
        //设置点击事件
        ib_tab_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("新闻详情页面数据被详情化了");

        //准备新闻详情页面的数据
        tabDetailPagers = new ArrayList<>();
        for(int i=0;i<children.size();i++){
            tabDetailPagers.add(new TabDetailPager(context,children.get(i)));
        }

        //设置viewPager的适配器
        viewPager.setAdapter(new MyNewsMenuDetailPagerAdapter());

        //viewpager和tabPageIndicator关联
        tabPageIndicator.setupWithViewPager(viewPager);
        //设置滚动模式
        tabPageIndicator.setTabMode(TabLayout.MODE_SCROLLABLE);

        //注意以后监听页面的变化， tabPageIndicator来监听页面的变化
        tabPageIndicator.addOnTabSelectedListener(new MyOnpageChangeListener());

//        for (int i = 0; i < tabPageIndicator.getTabCount(); i++) {
//            TabLayout.Tab tab = tabPageIndicator.getTabAt(i);
//            tab.setCustomView(getTabView(i));
//        }

    }

    public View getTabView(int position){
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item, null);
        TextView tv= (TextView) view.findViewById(R.id.textView);
        tv.setText(children.get(position).getTitle());
        ImageView img = (ImageView) view.findViewById(R.id.imageView);
        img.setImageResource(R.drawable.dot_focus);
        return view;
    }

    class MyOnpageChangeListener implements TabLayout.OnTabSelectedListener{

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if(tab.getPosition() == 0){
                //SlidingMenu可以全屏滑动
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
            }else{
                //SlidingMenu不可以全屏滑动
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
            }
        }

        /**
         * 根据传入的参数设置是否让slidingMenu可以滑动
         * @param touchmodeFullscreen
         */
        private void isEnableSlidingMenu(int touchmodeFullscreen) {
            MainActivity mainActivity = (MainActivity) context;
            mainActivity.getSlidingMenu().setTouchModeAbove(touchmodeFullscreen);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

    class MyNewsMenuDetailPagerAdapter extends PagerAdapter{

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return children.get(position).getTitle();
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            TabDetailPager tabDetailPager = tabDetailPagers.get(position);
            View rootview = tabDetailPager.rootView;
            //初始化数据
            tabDetailPager.initData();
            container.addView(rootview);
            return rootview;
        }

        @Override
        public int getCount() {
            return tabDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }


    }

}
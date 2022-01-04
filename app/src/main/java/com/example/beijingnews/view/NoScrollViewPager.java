package com.example.beijingnews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * 自定义不可以滑动的ViewPager
 */
public class NoScrollViewPager extends ViewPager {

    /**
     * 通常在代码中实例化的时候用该方法
     * @param context
     */
    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    /**
     * 在布局文件中使用该类的时候，实例化该类用该构造方法，这个方法不可少，少的话会崩溃
     * @param context
     * @param attrs
     */
    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 重写触摸事件，消费掉
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    //传递给最终的目标子事件处理
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false ;
    }
}

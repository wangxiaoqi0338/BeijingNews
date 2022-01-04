package com.example.beijingnews.menudetailpager.tablayout;

import android.content.Context;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;

public class tabLayoutIntercupt extends TabLayout {
    public tabLayoutIntercupt(@NonNull Context context) {
        super(context);
    }

    public boolean dispatchTouchEvent(MotionEvent ev){
        //要求父层视图不拦截事件
        //true:禁用拦截，禁用父层视图
        //false:不禁用
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}

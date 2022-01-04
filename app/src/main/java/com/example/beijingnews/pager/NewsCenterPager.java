package com.example.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.beijingnews.activity.MainActivity;
import com.example.beijingnews.base.BasePager;
import com.example.beijingnews.base.MenuDetailBasePager;
import com.example.beijingnews.domain.NewsCenterPagerBean;
import com.example.beijingnews.fragment.LeftmenuFragment;
import com.example.beijingnews.menudetailpager.InteractMenuDetailPager;
import com.example.beijingnews.menudetailpager.NewsMenuDetailPager;
import com.example.beijingnews.menudetailpager.PhotosMenuDetailPager;
import com.example.beijingnews.menudetailpager.TopicMenuDetailPager;
import com.example.beijingnews.utils.CacheUtils;
import com.example.beijingnews.utils.Contents;
import com.example.beijingnews.utils.LogUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class NewsCenterPager extends BasePager{
    /**
     * 左侧菜单对应的数据集合
     */
    private  List<NewsCenterPagerBean.DataEntity> data;

    /**
     * 详情页面的集合
     */
    private ArrayList<MenuDetailBasePager> detailBasePagers;
    /**
     * 起始时间
     */
    private long startTime;

    public NewsCenterPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("新闻中心数据被初始化了..");
        ib_menu.setVisibility(View.VISIBLE);
        //1.设置标题
        tv_title.setText("新闻中心");
        //2.联网请求，得到数据，创建视图
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        //3.把子视图添加到BasePager的FrameLayout中
        fl_content.addView(textView);
        //4.绑定数据
        textView.setText("新闻中心内容");
        //获取缓存数据 默认得到空字符串
        String saveJson = CacheUtils.getString(context,Contents.NEWSCANTER_PAGER_URL );
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }

        startTime = SystemClock.uptimeMillis();
        //联网请求数据
        //getDataFromNet();
        getDataFromByVolley();
    }

    /**
     * 使用Volley联网请求数据
     */
    private void getDataFromByVolley() {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, Contents.NEWSCANTER_PAGER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                long endTime = SystemClock.uptimeMillis();
                long passTime = endTime - startTime;
                LogUtil.e("passTime=="+passTime);
                LogUtil.e("使用Volley联网请求成功=="+result);
                //缓存数据
                CacheUtils.putString(context,Contents.NEWSCANTER_PAGER_URL,result);

                processData(result);
                //设置适配器
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("使用Volley联网请求失败=="+volleyError.getMessage());
            }
        }){ //解决乱码问题
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String parsed = new String(response.data,"UTF-8");
                    return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return super.parseNetworkResponse(response);
            }
        };

        //添加到队列
        queue.add(request);

    }

    /**
     * 使用xUtils3联网请求数据
     */
    private void getDataFromNet() {
        RequestParams params = new RequestParams(Contents.NEWSCANTER_PAGER_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("使用xUtils3联网请求成功=="+result);
                //缓存数据
                CacheUtils.putString(context,Contents.NEWSCANTER_PAGER_URL,result);

                processData(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("使用xUtils3联网请求失败=="+ex.getMessage());
            }
            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("使用xUtils3取消联网请求=="+cex.getMessage());
            }
            @Override
            public void onFinished() {
                LogUtil.e("使用xUtils3结束联网请求");
            }
        });
    }

    /**
     * 解析json数据和显示数据
     * @param json
     */
    private void processData(String json) {
        NewsCenterPagerBean bean = parseJson(json);
        String title = bean.getData().get(0).getChildren().get(1).getTitle();
        LogUtil.e("使用gson解析数据成功=="+title);

        //给左侧菜单传递数据
         data = bean.getData();

        MainActivity mainActivity = (MainActivity) context;
        LeftmenuFragment leftMenuFragment = mainActivity.getLeftmenuFragment();

        //添加详情页面
        detailBasePagers = new ArrayList<>();
        detailBasePagers.add(new NewsMenuDetailPager(context,data.get(0)));
        detailBasePagers.add(new TopicMenuDetailPager(context));
        detailBasePagers.add(new PhotosMenuDetailPager(context,data.get(2)));
        detailBasePagers.add(new InteractMenuDetailPager(context,data.get(2)));

        //把数据传递给左侧菜单
        leftMenuFragment.setData(data);



    }

    /**
     * 解析json数据:1.使用系统的API解析 2.使用第三方框架解析json数据 例如Gson.Fastjson
     * @param json
     * @return
     */
    private NewsCenterPagerBean parseJson(String json) {
        Gson gson = new Gson();
        NewsCenterPagerBean bean = gson.fromJson(json,NewsCenterPagerBean.class);
        return bean;
    }


    /**
     * 根据位置切换详情页面
     * @param position
     */
    public void swichPager(int position) {
        //1.设置标题
        tv_title.setText(data.get(position).getTitle());
        //2.移除之前内容
        fl_content.removeAllViews();
        //3.添加新内容
        MenuDetailBasePager detailBasePager = detailBasePagers.get(position);
        View rootView = detailBasePager.rootView;
        //初始化数据
        detailBasePager.initData();
        fl_content.addView(rootView);

        if(position == 2){
            //图组详情页面
            ib_swich_list_grid.setVisibility(View.VISIBLE);
            //设置点击事件
            ib_swich_list_grid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //1.得到图组详情页面对象
                    PhotosMenuDetailPager detailPager = (PhotosMenuDetailPager) detailBasePagers.get(2);
                    //2.调用图组对象的切换listview和GridView的方法
                    detailPager.swichListAndGrid(ib_swich_list_grid);
                }
            });

        }else {
            //其他页面
            ib_swich_list_grid.setVisibility(View.GONE);
        }
    }
}
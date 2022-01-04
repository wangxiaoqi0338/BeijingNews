package com.example.beijingnews.menudetailpager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.beijingnews.R;
import com.example.beijingnews.base.MenuDetailBasePager;
import com.example.beijingnews.domain.NewsCenterPagerBean;
import com.example.beijingnews.domain.PhotosMenuDetailPagerBean;
import com.example.beijingnews.utils.CacheUtils;
import com.example.beijingnews.utils.Contents;
import com.example.beijingnews.utils.LogUtil;
import com.google.gson.Gson;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

/**
 * 图组详情页面
 */
public class PhotosMenuDetailPager extends MenuDetailBasePager {

    private final NewsCenterPagerBean.DataEntity detailPagerData;
    @ViewInject(R.id.listview)
    private ListView listView;

    @ViewInject(R.id.gridview)
    private GridView gridView;

    private String url;
    private List<PhotosMenuDetailPagerBean.DataDTO.NewsDTO> news;
    private PhotosMenuDetailPagerAdapter adpter;
    private String path;



    public PhotosMenuDetailPager(Context context, NewsCenterPagerBean.DataEntity detailPagerData) {
        super(context);
        this.detailPagerData = detailPagerData;
    }

    @Override
    public View initView() {
       View view = View.inflate(context, R.layout.photos_menudetail_pager,null);
       x.view().inject(this,view);
       return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("图组详情页面数据被详情化了");
        url = Contents.BASE_URL+detailPagerData.getUrl();
        String saveJson = CacheUtils.getString(context,url);
        if(!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }
        getDataFromByVolley();
    }

    /**
     * 使用Volley联网请求数据
     */
    private void getDataFromByVolley() {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                LogUtil.e("使用Volley联网请求成功=="+result);
                //缓存数据
                CacheUtils.putString(context,url,result);

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
     * 解析和显示数据
     * @param json
     */
    private void processData(String json) {

        PhotosMenuDetailPagerBean bean = parsedJson(json);
        LogUtil.e("图组解析成功=="+bean.getData().getNews().get(0).getTitle());

        isShowListView = true;
       //设置适配器
        news = bean.getData().getNews();
        adpter = new PhotosMenuDetailPagerAdapter();
        listView.setAdapter(adpter);
    }

    /**
     * true,显示ListView，隐藏GrideView
     * false,显示GrideView，隐藏ListView
     */
    private boolean isShowListView = true;
    public void swichListAndGrid(ImageButton ib_swich_list_grid) {
        if(isShowListView){
            isShowListView = false;
            //显示GrideView，隐藏ListView
            gridView.setVisibility(View.VISIBLE);
            adpter = new PhotosMenuDetailPagerAdapter();
            gridView.setAdapter(adpter);
            listView.setVisibility(View.GONE );
            //按钮显示--ListView
            ib_swich_list_grid.setImageResource(R.drawable.icon_pic_list_type);
        }else{
            isShowListView = true;
            //显示ListView，隐藏GrideView
            listView.setVisibility(View.VISIBLE);
            adpter = new PhotosMenuDetailPagerAdapter();
            listView.setAdapter(adpter);
            gridView.setVisibility(View.GONE );
            //按钮显示--GrideView
            ib_swich_list_grid.setImageResource(R.drawable.icon_pic_grid_type);
        }
    }

    class PhotosMenuDetailPagerAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                convertView = View.inflate(context,R.layout.item_photos_menudetail_pager,null);
                viewHolder = new ViewHolder();
                viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //根据位置得到对应的数据
           PhotosMenuDetailPagerBean.DataDTO.NewsDTO newsEntity = news.get(position);
            viewHolder.tv_title.setText(newsEntity.getTitle());
             path = Contents.BASE_URL+ newsEntity.getSmallimage();
            //使用volley请求图片-设置图片

            return convertView;
        }
    }

    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_title;
    }

//    /**
//     *
//     * @param viewHolder
//     * @param imageurl
//     */
//    private void loaderImager(final ViewHolder viewHolder,String imageurl){
//
//        viewHolder.iv_icon.setTag(imageurl);
//        //直接在这里请求会乱位置
//        ImageLoader.ImageListener listener = new ImageLoader.ImageListener() {
//            @Override
//            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
//                if(imageContainer != null){
//
//                    if(viewHolder.iv_icon != null){
//                        if(imageContainer.getBitmap() != null){
//                            //设置图片
//                            viewHolder.iv_icon.setImageBitmap(imageContainer.getBitmap());
//                        } else {
//                            //设置默认图片
//                            viewHolder.iv_icon.setImageResource(R.drawable.home_scroll_default);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                //如果出错，则说明都不显示（简单处理），最好准备一张出错图片
//                viewHolder.iv_icon.setImageResource(R.drawable.home_scroll_default);
//            }
//        };
//
//           ImageLoader.get(imageurl, listener);
//          VolleyManager.getImageLoader.get(imageurl,listener);
//    }



    private PhotosMenuDetailPagerBean parsedJson(String json) {
        return new Gson().fromJson(json,PhotosMenuDetailPagerBean.class);
    }

}


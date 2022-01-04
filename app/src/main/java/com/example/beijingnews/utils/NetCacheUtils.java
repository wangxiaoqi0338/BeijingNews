package com.example.beijingnews.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  网络缓存工具
 */
public class NetCacheUtils {

    /**
     * 请求图片成功
     */
    public static final int SUCESS = 1;
    private final Handler handler;
    /**
     * 本地缓存工具类
     */
    private final LocalCacheUtils localCacheUtils;
    /**
     * 内存缓存工具
     */
    private final MemoryCacheUtils memoryCacheUtils;
    /**
     * 线程池类
     */
    private  ExecutorService service;
    public static final int FAIL = 2;

    public NetCacheUtils(Handler handler, LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        this.handler = handler;
        service = Executors.newFixedThreadPool(10);
        this.localCacheUtils = localCacheUtils;
        this.memoryCacheUtils = memoryCacheUtils;
    }

    //联网请求得到图片
    public void getBitmapFormNet(String imageUrl, int position) {
        //new Thread(new MyRunnable(imageUrl,position)).start();

        service.execute(new MyRunnable(imageUrl,position));
    }

    class MyRunnable implements Runnable{

        private final String imageUrl;
        private final int position;

        public MyRunnable(String imageUrl, int position) {
            this.imageUrl = imageUrl;
            this.position = position;
        }

        @Override
        public void run() {
            //子线程
            //请求网络图片
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(imageUrl).openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(4000);
                connection.setReadTimeout(4000);
                connection.connect();//可写可不写
                int code = connection.getResponseCode();
                if(code == 200){
                    InputStream is = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);

                    //显示到控件上,发消息把Bitmap发出去和position
                    Message msg = Message.obtain();
                    msg.what = SUCESS;
                    msg.arg1 = position;
                    msg.obj = bitmap;
                    handler.sendMessage(msg);

                    //在内存中缓存一份
                    memoryCacheUtils.putBitmap(imageUrl,bitmap);
                    //在本地中缓存一份
                    localCacheUtils.putBitmap(imageUrl,bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Message msg = Message.obtain();
                msg.what = FAIL;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }
        }
    }
}

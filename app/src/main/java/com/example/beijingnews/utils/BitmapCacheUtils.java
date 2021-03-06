package com.example.beijingnews.utils;

import android.graphics.Bitmap;
import android.os.Handler;

/**
 * 图片三级缓存的工具类
 */
public class BitmapCacheUtils {

    /**
     * 网络缓存工具类
     */
    private NetCacheUtils netCacheUtils;

    /**
     * 本地缓存工具类
     * @param handler
     */
    private LocalCacheUtils localCacheUtils;

    /**
     * 内存工具缓存类
     */
    private MemoryCacheUtils memoryCacheUtils;

    public BitmapCacheUtils(Handler handler){
        memoryCacheUtils = new MemoryCacheUtils();
        localCacheUtils = new LocalCacheUtils(memoryCacheUtils);
        netCacheUtils = new NetCacheUtils(handler,localCacheUtils,memoryCacheUtils);
    }
    /**
     * 三级缓存设计步骤：
     *      从内存中取图片
     *      从本地文件中取图片
     *          向内存中保存一份
     *      请求网络图片，获取图片，显示到控件上,Handler,position
     *          向内存存一份
     *          向本地文件存一份
     * @param imageUrl
     * @param position
     * @return
     */
    public Bitmap getBitmap(String imageUrl, int position) {
        //1.从内存中获取
        if(memoryCacheUtils != null){
            Bitmap bitmap = memoryCacheUtils.getBitmapFromUrl(imageUrl);
            if (bitmap != null){
                LogUtil.e("本存加载图片成功=="+position);
                return bitmap;
            }
        }

        //2.从本地中获取图片
        if(localCacheUtils != null){
            Bitmap bitmap = localCacheUtils.getBitmapFromUrl(imageUrl);
            if (bitmap != null){
                LogUtil.e("本地加载图片成功=="+position);
                return bitmap;
            }
        }
        //3.从网络中获取图片
        netCacheUtils.getBitmapFormNet(imageUrl,position);
        return null;
    }
}

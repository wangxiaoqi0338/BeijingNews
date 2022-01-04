package com.example.beijingnews.utils;

/**
 * 常量类，配置联网请求地址
 */
public class Contents {

    /**
     * 联网请求的ip和端口
     */
    public static final String BASE_URL = "http://192.168.11.201:8080/web_home";

    /**
     * 本地的模拟器，访问本机tomcat服务器
     */
    //public static final String BASE_URL = "http://10.0.2.2:8080/web_home";

    /**
     * 新闻中心的网络地址
     */
    public static final String NEWSCANTER_PAGER_URL = BASE_URL+"/static/api/news/categories.json";

}

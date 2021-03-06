package com.example.beijingnews.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beijingnews.R;

public class NewsDetailActivity extends Activity implements View.OnClickListener {

    private TextView tvTitle;
    private ImageButton ibMenu;
    private ImageButton ibBack;
    private ImageButton ibTextsize;
    private ImageButton ibShare;
    private WebView webview;
    private ProgressBar pbLoading;

    private String url;
    private WebSettings webSettings;


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2021-12-16 11:18:12 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        tvTitle = (TextView)findViewById( R.id.tv_title );
        ibMenu = (ImageButton)findViewById( R.id.ib_menu );
        ibBack = (ImageButton)findViewById( R.id.ib_back );
        ibTextsize = (ImageButton)findViewById( R.id.ib_textsize );
        ibShare = (ImageButton)findViewById( R.id.ib_share );
        webview = (WebView)findViewById( R.id.webview );
        pbLoading = (ProgressBar)findViewById( R.id.pb_loading );

        tvTitle.setVisibility(View.GONE);
        ibMenu.setVisibility(View.GONE);
        ibBack.setVisibility(View.VISIBLE);
        ibTextsize.setVisibility(View.VISIBLE);
        ibShare.setVisibility(View.VISIBLE);

        ibBack.setOnClickListener( this );
        ibTextsize.setOnClickListener( this );
        ibShare.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2021-12-16 11:18:12 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
       if ( v == ibBack ) {
            // Handle clicks for ibBack
           finish();
        } else if ( v == ibTextsize ) {
            // Handle clicks for ibTextsize
           showChangeTextSizeDialog();
            //Toast.makeText(NewsDetailActivity.this,"??????????????????",Toast.LENGTH_SHORT).show();
        } else if ( v == ibShare ) {
            // Handle clicks for ibShare
            //Toast.makeText(NewsDetailActivity.this,"??????",Toast.LENGTH_SHORT).show();
        }
    }

    private int tempSize = 2;
    private int realSize = tempSize;

    private void showChangeTextSizeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("??????????????????");
        String[] items = {"????????????","?????????","????????????","?????????","????????????"};
        builder.setSingleChoiceItems(items, 2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    tempSize = which;
            }
        });
        builder.setNegativeButton("??????",null);
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    realSize = tempSize;
                    changeTextSize(realSize);
            }
        });
        builder.show();
    }

    private void changeTextSize(int realSize) {
        switch (realSize){
            case 0://????????????
                webSettings.setTextZoom(200);
                break;
            case 1://?????????
                webSettings.setTextZoom(150);
                break;
            case 2://????????????
                webSettings.setTextZoom(100);
                break;
            case 3://?????????
                webSettings.setTextZoom(75);
                break;
            case 4://????????????
                webSettings.setTextZoom(50);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        findViews();
        getData();
    }

    private void getData() {

        url = getIntent().getStringExtra("url");

        webSettings = webview.getSettings();
        //????????????javascript
        webSettings.setJavaScriptEnabled(true);
        //????????????????????????
        webSettings.setUseWideViewPort(true);
        //??????????????????
        webSettings.setBuiltInZoomControls(true);
        //??????????????????
        webSettings.setTextZoom(100);
        //???????????????????????????????????????????????????
        webview.setWebViewClient(new WebViewClient(){
           //????????????????????????????????????
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbLoading.setVisibility(View.GONE);
            }
        });
        webview.loadUrl(url);
    }
}
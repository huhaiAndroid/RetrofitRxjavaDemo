package com.example.huhai.retrofitrxjavademo.login.ui;

import android.os.CountDownTimer;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.huhai.retrofitrxjavademo.R;
import com.example.huhai.retrofitrxjavademo.base.BaseActivity;
import com.example.huhai.retrofitrxjavademo.bean.blogUrl;
import com.example.huhai.retrofitrxjavademo.manager.Dbhelper;
import com.example.huhai.retrofitrxjavademo.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 *  @项目名：  RetrofitRxjavaDemo
 *  @包名：    com.example.huhai.retrofitrxjavademo.login.ui
 *  @文件名:   H5activity
 *  @创建者:   huhai
 *  @创建时间:  2018/11/13 18:52
 *  @描述：
 */
public class H5activity extends BaseActivity {
    private static final String TAG = "H5activity";
    private List<String> url = new ArrayList<>();
    private Random random=new Random();
    private List<blogUrl> data;
   private CountDownTimer timer;
    @Override
    protected void init() {
        searchData();
        inittimer();
        initwebview();

    }

    private void initwebview() {
        WebView webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        webView.loadUrl(data.get(random.nextInt(data.size())).getName()+"");

        webView.setWebViewClient(new WebViewClient());
    }

    private void inittimer() {
        int time=  SpUtils.getInt(H5activity.this, "scanTime", 5);
        timer = new CountDownTimer(time*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int remainTime = (int) (millisUntilFinished / 1000L);
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }

    private void searchData() {
        data = Dbhelper.getDaoSession().getBlogUrlDao().loadAll();
        Log.d(TAG, data.toString());
    }


    @Override
    protected int bindLayoutId() {
        return R.layout.activity_h5;
    }

    @Override
    protected Object createPresenter() {
        return null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
    }
}

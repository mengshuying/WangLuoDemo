package com.example.administrator.wangluodemo;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMWeb;

import static com.example.administrator.wangluodemo.MainActivity.flag;
public class DiShiErActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView mNr_wv;
    private ImageView mNr_fenx;
    private ImageView mNr_fanhui;
    private String mChuan;
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
         super.onCreate(savedInstanceState);
        setContentView(R.layout.fenxiang_layout);
         initView();
         chuan();
         mywebview();
    }
    private void chuan(){
        Intent intent = getIntent();
        mChuan = intent.getStringExtra("chuan");
    }
    private void mywebview(){
        WebSettings settings = mNr_wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        mNr_wv.setWebViewClient(new WebViewClient());
        mNr_wv.loadUrl(mChuan);
    }
    private void initView(){
        mNr_wv = (WebView) findViewById(R.id.nr_wv);
        mNr_fenx = (ImageView) findViewById(R.id.nr_fenx);
        mNr_fanhui = (ImageView) findViewById(R.id.nr_fanhui);
        mNr_fenx.setOnClickListener(this);
        mNr_fanhui.setOnClickListener(this);
     }
    @Override
    public void onClick(View v){
     switch(v.getId()){
         case R.id.nr_fenx:
             myqq();
             break;
         case R.id.nr_fanhui:
             finish();
             break;
      }
    }
    private void myqq(){
        UMWeb web = new UMWeb(mChuan);
        web.setTitle("This is music title");//标题
        web.setDescription("my description");//描述
        new ShareAction(this).withText("hello").withMedia(web).setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.SINA)
                .setCallback(umShareListener).open();
     }
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }
        @Override
        public void onResult(SHARE_MEDIA platform) {

            Toast.makeText(DiShiErActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(DiShiErActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();

        }
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(DiShiErActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}


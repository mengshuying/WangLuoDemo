package com.example.administrator.wangluodemo;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
public class DiLiuActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mXianyi_imageview2;
    private ImageView mXianyi_imageview1;
    private WebView mXianyi_webview;
    private PopupWindow popupwindow;
    private Button mPop_textview1;
    private Button mPop_textview2;
    private Button mPop_textview3;
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.xianyi_layout);
        initView();
        mywebview();
     }
    private void mywebview(){
        WebSettings settings = mXianyi_webview.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);
        mXianyi_webview.setWebViewClient(new WebViewClient());
        mXianyi_webview.loadUrl("Http://www.baidu.com");
    }
    private void initView(){
        mXianyi_imageview1 = (ImageView) findViewById(R.id.xianyi_imageview1);
        mXianyi_imageview2 = (ImageView) findViewById(R.id.xianyi_imageview2);
        mXianyi_webview = (WebView) findViewById(R.id.xianyi_webview);
        mXianyi_imageview1.setOnClickListener(this);
        mXianyi_imageview2.setOnClickListener(this);
        mXianyi_webview.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.xianyi_imageview1:
                Intent intent=new Intent(DiLiuActivity.this,FourActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.xianyi_imageview2:
                initpop();

                break;
            case R.id.pop_textview1:

                popupwindow.dismiss();
                break;
            case R.id.pop_textview2:

                popupwindow.dismiss();
                break;
            case R.id.pop_textview3:

                popupwindow.dismiss();
                break;
        }
    }
    private void initpop(){
        View view = View.inflate(DiLiuActivity.this, R.layout.pop_layout,null);
        mPop_textview1 = (Button) view.findViewById(R.id.pop_textview1);
        mPop_textview2 = (Button) view.findViewById(R.id.pop_textview2);
        mPop_textview3 = (Button) view.findViewById(R.id.pop_textview3);
        mPop_textview1.setOnClickListener(this);
        mPop_textview2.setOnClickListener(this);
        mPop_textview3.setOnClickListener(this);
        popupwindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupwindow.setTouchable(true);
        popupwindow.setFocusable(true);
        popupwindow.setOutsideTouchable(true);
        popupwindow.setBackgroundDrawable(new BitmapDrawable());
        popupwindow.showAsDropDown(mXianyi_imageview2);
     }
}

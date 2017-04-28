package com.example.administrator.wangluodemo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import bean.FenBean;
import utils.MyHttpUtils;
import utils.MyUrl;
import xlistview.bawei.com.xlistviewlibrary.XListView;
public class DiShiLiuActivity extends AppCompatActivity implements XListView.IXListViewListener {
    private XListView mListt_view;
    private MyHttpUtils mHttpUtils;
    private int channelId=0;
    private int startNum=0;
    public static List<Integer> list_num=new ArrayList<Integer>();
    public static List<FenBean.DataBean> list_bu=new ArrayList<FenBean.DataBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_di_shi_liu);
        init();
        intidata();
    }
     private void intidata(){
        mHttpUtils = new MyHttpUtils(this, mListt_view, MyUrl.url2);
        mHttpUtils.getURLL(channelId,startNum);
     }
    private void init(){
        mListt_view = (XListView) findViewById(R.id.listt_view);
        mListt_view.setXListViewListener(this);
        mListt_view.setPullLoadEnable(true);
        mListt_view.setPullRefreshEnable(true);
      }
       @Override
      public void onRefresh(){
           list_bu.clear();
           list_num.clear();
           startNum=0;
           intidata();
           onLoad();
      }
       @Override
      public void onLoadMore(){
           startNum++;
           intidata();
           onLoad();
      }
      private void onLoad(){
        mListt_view.stopRefresh();
        mListt_view.stopLoadMore();
        // 设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 获取当前系统时间
        String nowTime = df.format(new Date(System.currentTimeMillis()));
        // 释放时提示正在刷新时的当前时间
        mListt_view.setRefreshTime(nowTime);
      }
}

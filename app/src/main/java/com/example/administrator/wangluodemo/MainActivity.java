package com.example.administrator.wangluodemo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

import adapter.MyPageAdapter;
import bean.JsonBean1;
import fragment.MenuLeftFragment;
import utils.ChildInfo;
import utils.MyHttpUtils;
import utils.NetworkUtils;
import utils.Utils;
import utils.UtilsDb;
public class MainActivity extends SlidingFragmentActivity{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView mImagebutton;
    public static boolean flag=false;
    private int theme = 0;
    @Override
    public void onCreate(Bundle savedInstanceState){
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
          Utils.init(this);
//       startActivity(new Intent(this, NetworkActivity.class));
         initView();
        setconnect();
        tabLayout.setupWithViewPager(viewPager);//将tablayout与viewPager关联起来
         // 初始化SlideMenu
         initRightMenu();

//         xutilsdb();
    }
    private void setconnect(){
        boolean connected = NetworkUtils.isConnected();
        if(connected){
            adpater();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("请选择网络");
            String[] arr=new String[]{"wifi","4g"};
            builder.setItems(arr, new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){
                    switch(which){
                        case 0:
                            NetworkUtils.setWifiEnabled(!NetworkUtils.getWifiEnabled());
                            adpater();
                            break;
                        case 1:
                            NetworkUtils.setDataEnabled(!NetworkUtils.getDataEnabled());
                            adpater();
                            break;
                    }
                     dialog.dismiss();
                }
            });
            builder.show();
        }
    }
    private void initView(){
        tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        viewPager= (ViewPager) findViewById(R.id.viewPager);
        mImagebutton = (ImageView) findViewById(R.id.imagebutton);
     }
//    public void xutilsdb(){
//        MyHttpUtils httpUtils=new MyHttpUtils(MainActivity.this);
//        httpUtils.getURL1();
//     }
    public void adpater(){
        MyHttpUtils httpUtils=new MyHttpUtils(MainActivity.this,viewPager);
          httpUtils.getURL2();
      }
    private void initRightMenu()
    {
        Fragment leftMenuFragment = new MenuLeftFragment();
        setBehindContentView(R.layout.left_menu_frame);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_left_menu_frame, leftMenuFragment).commit();
        SlidingMenu menu = getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//		menu.setBehindWidth()
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        // menu.setBehindScrollScale(1.0f);
        menu.setSecondaryShadowDrawable(R.drawable.shadow);
    }

    public void showLeftMenu(View view)
    {
        getSlidingMenu().showMenu();
    }
     public void ib(View view){
         Intent intent=new Intent(MainActivity.this,SecondActivity.class);
         startActivity(intent);
     }
    @Override
    protected void onResume(){
        if(flag){
            cha();
        }
        super.onResume();
    }
    public void cha(){
        List<JsonBean1.ResultBean.DateBean> list=new ArrayList<JsonBean1.ResultBean.DateBean>();
        try {
            DbManager mDb = UtilsDb.get();
            List<ChildInfo> all = mDb.findAll(ChildInfo.class);
            for(ChildInfo xx:all){
                if(xx.zhangtai.equals("1")){
                    list.add(new JsonBean1.ResultBean.DateBean(xx.uri,xx.title));
                }
            }
            viewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager(),list));
        }catch (DbException e){
            e.printStackTrace();
        }
    }

    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {

        }
        @Override
        public void onError(UiError uiError){

        }
        @Override
        public void onCancel(){

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,new BaseUiListener());
        }
        super.onActivityResult(requestCode, resultCode, data);
      }
    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);//进入动画
        finish();
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        startActivity(intent);
    }

}

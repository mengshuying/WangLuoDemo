package com.example.administrator.wangluodemo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
public class ThreeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mButton_phone_land;
    private Button mButton_phone_zhu;
    private ImageButton mLandmore_qq;
    private static final String TAG = "MainActivity";
    private static final String APP_ID = "1106105900";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    private ImageView mButton_landmore_xiao;
    private int theme = 0;
    private ImageButton mLandmore_wei;

    @Override
    public void onCreate(Bundle savedInstanceState){
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_three);
        initView();
        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID,ThreeActivity.this.getApplicationContext());
    }
    private void initView(){
        mButton_phone_land = (Button) findViewById(R.id.button_phone_land);
        mButton_phone_zhu = (Button) findViewById(R.id.button_phone_zhu);
        mLandmore_qq = (ImageButton) findViewById(R.id.landmore_qq);
        mLandmore_wei = (ImageButton) findViewById(R.id.landmore_wei);
        mButton_landmore_xiao = (ImageView) findViewById(R.id.button_landmore_xiao);
        mButton_phone_land.setOnClickListener(this);
        mButton_phone_zhu.setOnClickListener(this);
        mLandmore_qq.setOnClickListener(this);
        mButton_landmore_xiao.setOnClickListener(this);
        mLandmore_wei.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.button_phone_land:
                Intent intent1=new Intent(ThreeActivity.this,FourActivity1.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.button_phone_zhu:
                Intent intent2=new Intent(ThreeActivity.this,FourActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.landmore_wei:
                Intent intent4=new Intent(ThreeActivity.this,DiShiSanActivity.class);
                startActivity(intent4);
                finish();
                break;
            case R.id.landmore_qq:
     /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
     官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
     第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
                mIUiListener = new BaseUiListener();
                //all表示获取所有权限
                mTencent.login(ThreeActivity.this, "all", mIUiListener);
                break;
            case R.id.button_landmore_xiao:
                Intent intent=new Intent(ThreeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener{
        @Override
        public void onComplete(Object response){
            Toast.makeText(ThreeActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener(){
                    @Override
                    public void onComplete(Object response){
                        Log.e(TAG,"登录成功"+response.toString());
                        JSONObject res= (JSONObject) response;
                        String nickName=res.optString("nickname");//获取昵称
                        String figureurl_qq_1=res.optString("figureurl_qq_2");//获取图片
//                        Intent intent=new Intent(ThreeActivity.this,MainActivity.class);
//                        startActivity(intent);
                        SharedPreferences lala = getSharedPreferences("lala", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = lala.edit();
                        edit.putString("name",nickName);
                        edit.putString("imageview",figureurl_qq_1);
                        edit.putBoolean("flag",true);
                        edit.commit();
                        finish();
                    }
                    @Override
                    public void onError(UiError uiError){
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }
                    @Override
                    public void onCancel(){
                        Log.e(TAG,"登录取消");
                    }
                });
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        @Override
        public void onError(UiError uiError){
            Toast.makeText(ThreeActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCancel(){
            Toast.makeText(ThreeActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

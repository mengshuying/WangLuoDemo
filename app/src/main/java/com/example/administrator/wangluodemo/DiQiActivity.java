package com.example.administrator.wangluodemo;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static android.R.attr.country;
import static android.R.attr.data;
import static cn.smssdk.SMSSDK.submitVerificationCode;
import static com.example.administrator.wangluodemo.R.id.chongxinfasong;
public class DiQiActivity extends AppCompatActivity implements View.OnClickListener {
    private LinEditetext mAccount_et;
    private LinEditetext mPassword_et;
    private Button mButton;
    private TextView mPhone_text1;
    private static String APPKEY = "1cfc89609e4aa";
    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "50a9c24abd006b8166c828157f003a8e";
    private String mChuan;
    private Button mChongxinfasong;
    public int T = 40; //倒计时时长
    private Handler mHandler = new Handler();
    private String mTrim;
    private ImageView mZhuce_fanhui;
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_di_qi);
        initView();
        SMSSDK.initSDK(this, APPKEY, APPSECRET);
        jishi();
    }
    private void initView() {
        mAccount_et = (LinEditetext) findViewById(R.id.account_et);
        mPassword_et = (LinEditetext) findViewById(R.id.password_et);
        mButton = (Button) findViewById(R.id.denglu);
        mPhone_text1 = (TextView) findViewById(R.id.phone_text1);
        mChongxinfasong = (Button) findViewById(chongxinfasong);
        mZhuce_fanhui = (ImageView) findViewById(R.id.zhuce_fanhui);
        mAccount_et.setOnClickListener(this);
        mPassword_et.setOnClickListener(this);
        mButton.setOnClickListener(this);
        mChongxinfasong.setOnClickListener(this);
        mZhuce_fanhui.setOnClickListener(this);
        Intent intent = getIntent();
        mChuan = intent.getStringExtra("chuan");
        mPhone_text1.setText(mChuan);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.zhuce_fanhui:
                Intent intent1 = new Intent(DiQiActivity.this, FourActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.denglu:
                 mTrim= mAccount_et.getText().toString().trim();
                String s = mPassword_et.getText().toString();
                if (TextUtils.isEmpty(mTrim)){
                    Toast.makeText(DiQiActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                }
                 else if (TextUtils.isEmpty(s)){
                    Toast.makeText(DiQiActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else{
                    submitVerificationCode("+86", mChuan, mTrim);
                    Intent intent2 = new Intent(DiQiActivity.this, DiBaActivity.class);
                    startActivity(intent2);
                    finish();
                }
                break;
            case chongxinfasong:
                jishi();
                break;
        }
    }
    private void jishi(){
        SMSSDK.registerEventHandler(eh); //注册短信回调
        SMSSDK.getVerificationCode("86", mChuan);//请求获取短信验证码
        submitVerificationCode("+86", mChuan, mTrim);
        new Thread(new MyCountDownTimer()).start();//开始执行
      }
    /**
     * 自定义倒计时类，实现Runnable接口
     */
    class MyCountDownTimer implements Runnable{
        @Override
        public void run(){
            //倒计时开始，循环
            while (T > 0) {
                mHandler.post(new Runnable(){
                    @Override
                    public void run(){
                        mChongxinfasong.setClickable(false);
                        mChongxinfasong.setText(T + "秒后重新开始");
                    }
                });
                try {
                    Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                T--;
            }
            //倒计时结束，也就是循环结束
            mHandler.post(new Runnable(){
                @Override
                public void run(){
                    mChongxinfasong.setClickable(true);
                    mChongxinfasong.setText("重新开始");
                }
            });
            T = 40; //最后再恢复倒计时时长
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (msg.what == 1)
                Toast.makeText(DiQiActivity.this, "回调完成", Toast.LENGTH_SHORT).show();
            else if (msg.what == 2){
//                Intent intent = new Intent(DiQiActivity.this, DiBaActivity.class);
//                startActivity(intent);
                Toast.makeText(DiQiActivity.this, "提交验证码成功", Toast.LENGTH_SHORT).show();
            }
            else if (msg.what == 3){
                Toast.makeText(DiQiActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
            }
            else if (msg.what == 4)
                Toast.makeText(DiQiActivity.this, "返回支持发送国家验证码", Toast.LENGTH_SHORT).show();
        }
    };
    //回调函数
    EventHandler eh= new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data){
            if (result == SMSSDK.RESULT_COMPLETE){
                handler.sendEmptyMessage(1);
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                    //提交验证码成功
                    handler.sendEmptyMessage(2);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                    handler.sendEmptyMessage(3);
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                    handler.sendEmptyMessage(4);
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };
}



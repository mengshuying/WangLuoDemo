package com.example.administrator.wangluodemo;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static cn.smssdk.SMSSDK.submitVerificationCode;
import static com.example.administrator.wangluodemo.R.id.code_edit;
public class DiShiActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mCode_edit;
    private EditText mNewpaw_edit;
    private Button mDishi_button;
    private ImageView mBack_pawTwo;
    private TextView mPhone_num;
    private String mChuan;
    private static String APPKEY = "1cfc89609e4aa";
    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "50a9c24abd006b8166c828157f003a8e";
    private TextView mDishi_textview;
    public int T = 40; //倒计时时长
    private String mTrim;
    private Handler mHandler = new Handler();
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_di_shi);
        SMSSDK.initSDK(this, APPKEY, APPSECRET);
        initView();
        jishi();
    }
    private void initView(){
        mCode_edit = (EditText) findViewById(code_edit);
        mNewpaw_edit = (EditText) findViewById(R.id.newpaw_edit);
        mDishi_button = (Button) findViewById(R.id.dishi_button);
        mBack_pawTwo = (ImageView) findViewById(R.id.back_pawTwo);
        mPhone_num = (TextView) findViewById(R.id.phone_num);
        mDishi_textview = (TextView) findViewById(R.id.dishi_textview);
        mDishi_button.setOnClickListener(this);
        mBack_pawTwo.setOnClickListener(this);
        mDishi_textview.setOnClickListener(this);
        Intent intent = getIntent();
        mChuan = intent.getStringExtra("chuan");
        mPhone_num.setText(mChuan);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.dishi_textview:
                jishi();
                break;
            case R.id.dishi_button:
                mTrim = mCode_edit.getText().toString().trim();
                String trim1 = mNewpaw_edit.getText().toString().trim();
                if (TextUtils.isEmpty(mTrim)){
                    Toast.makeText(DiShiActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(trim1)){
                    Toast.makeText(DiShiActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else{
                    submitVerificationCode("+86", mChuan, mTrim);
                }
                Intent intent1=new Intent(DiShiActivity.this,MainActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.back_pawTwo:
                Intent intent2=new Intent(DiShiActivity.this,DiJiuActivity.class);
                startActivity(intent2);
                finish();
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
                        mDishi_textview.setClickable(false);
                        mDishi_textview.setText(T + "秒后重新开始");
                    }
                });
                try {
                    Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                T--;
            }
            //倒计时结束，也就是循环结束
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mDishi_textview.setClickable(true);
                    mDishi_textview.setText("重新开始");
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
                Toast.makeText(DiShiActivity.this, "回调完成", Toast.LENGTH_SHORT).show();
            else if (msg.what == 2){
//                Intent intent = new Intent(DiShiActivity.this, MainActivity.class);
//                startActivity(intent);
                Toast.makeText(DiShiActivity.this, "提交验证码成功", Toast.LENGTH_SHORT).show();
            }
            else if (msg.what == 3){
                Toast.makeText(DiShiActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
            }
            else if (msg.what == 4)
                Toast.makeText(DiShiActivity.this, "返回支持发送国家验证码", Toast.LENGTH_SHORT).show();
        }
    };
    //回调函数
    EventHandler eh = new EventHandler(){
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

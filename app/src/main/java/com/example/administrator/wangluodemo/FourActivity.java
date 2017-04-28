package com.example.administrator.wangluodemo;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import cn.smssdk.SMSSDK;
public class FourActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mZhuce_imageview;
    private TextView mZhuce_textview2;
    private EditText mEdittext2;
    private RadioButton mRadiobutton;
    private TextView mZhuce_textview3;
    private Button mButton;
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);


        setContentView(R.layout.zhuce_layout);
        initView();
    }
    private void initView(){
        mZhuce_imageview = (ImageView) findViewById(R.id.zhuce_imageview);
        mZhuce_textview2 = (TextView) findViewById(R.id.zhuce_textview2);
        mEdittext2 = (EditText) findViewById(R.id.edittext2);
        mRadiobutton = (RadioButton) findViewById(R.id.radiobutton);
        mZhuce_textview3 = (TextView) findViewById(R.id.zhuce_textview3);
        mButton = (Button) findViewById(R.id.zhuce_button);
        mZhuce_imageview.setOnClickListener(this);
        mZhuce_textview2.setOnClickListener(this);
        mEdittext2.setOnClickListener(this);
        mRadiobutton.setOnClickListener(this);
        mZhuce_textview3.setOnClickListener(this);
        mButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.zhuce_imageview:
                Intent intent1=new Intent(FourActivity.this,ThreeActivity.class);
                startActivity(intent1);
                finish();
                break;
          case  R.id.zhuce_textview2:
              Intent intent2=new Intent(FourActivity.this,FourActivity1.class);
              startActivity(intent2);
              finish();
               break;
            case R.id.zhuce_textview3:
                Intent intent3=new Intent(FourActivity.this,DiLiuActivity.class);
                startActivity(intent3);
                finish();
                break;
            case  R.id.zhuce_button:
                String trim = mEdittext2.getText().toString().trim();
                if(TextUtils.isEmpty(trim)){
                    Toast.makeText(FourActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
                }else if(trim.length()<11){
                    Toast.makeText(FourActivity.this,"手机号应为11位数字",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(FourActivity.this,DiQiActivity.class);
                    intent.putExtra("chuan",trim);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}

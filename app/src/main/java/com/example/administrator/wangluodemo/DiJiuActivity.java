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
import android.widget.Toast;

public class DiJiuActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText mDijiu;
    private Button mDijiu_button;
    private ImageView mDijiu_imageview;
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_di_jiu);
        initView();
    }
    private void initView(){
        mDijiu = (EditText) findViewById(R.id.dijiu_edit);
        mDijiu_button = (Button) findViewById(R.id.dijiu_button);
        mDijiu_imageview = (ImageView) findViewById(R.id.dijiu_imageview);
        mDijiu_button.setOnClickListener(this);
        mDijiu_imageview.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.dijiu_button:
                String trim = mDijiu.getText().toString().trim();
                if(TextUtils.isEmpty(trim)){
                    Toast.makeText(DiJiuActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
                }else if(trim.length()<11){
                    Toast.makeText(DiJiuActivity.this,"手机号应为11位数字",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent1=new Intent(DiJiuActivity.this,DiShiActivity.class);
                    intent1.putExtra("chuan",trim);
                    startActivity(intent1);
                    finish();
                }
                break;
            case R.id.dijiu_imageview:
                Intent intent2=new Intent(DiJiuActivity.this,FourActivity1.class);
                startActivity(intent2);
                finish();
                break;
        }
    }
}

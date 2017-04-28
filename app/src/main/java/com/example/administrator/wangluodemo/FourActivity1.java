package com.example.administrator.wangluodemo;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
public class FourActivity1 extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditText3;
    private EditText mEditText4;
    private TextView mTextView;
    private TextView mTextView2;
    private Button mButton;
    private ImageView mButton_landmore_xiao;
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four1);
        initView();
    }
    private void initView(){
        mEditText3 = (EditText) findViewById(R.id.editText3);
        mEditText4 = (EditText) findViewById(R.id.editText4);
        mTextView = (TextView) findViewById(R.id.textView);
        mTextView2 = (TextView) findViewById(R.id.textView2);
        mButton = (Button) findViewById(R.id.button);
        mButton_landmore_xiao = (ImageView) findViewById(R.id.button_landmore_xiao);
        mTextView.setOnClickListener(this);
        mTextView2.setOnClickListener(this);
        mButton.setOnClickListener(this);
        mButton_landmore_xiao.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.textView:
                Intent intent1=new Intent(FourActivity1.this,FourActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.textView2:
                 Intent intent2=new Intent(FourActivity1.this,DiJiuActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.button:
                Intent intent3=new Intent(FourActivity1.this,MainActivity.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.button_landmore_xiao:
                Intent intent4=new Intent(FourActivity1.this,ThreeActivity.class);
                startActivity(intent4);
                finish();
                break;
        }
    }
}

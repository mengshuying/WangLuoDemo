package com.example.administrator.wangluodemo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
public class DiShiYiActivity extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences.Editor mEdit;
    private SharedPreferences mLala;
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_di_shi_yi);
        Button button= (Button) findViewById(R.id.tuichu);
        button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("flag",false);
        startActivity(intent);
        finish();
    }
}

package com.example.administrator.wangluodemo;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DiShiSiActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mRight_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shezhi);
        initView();
    }

    private void initView() {
        mRight_image = (ImageView) findViewById(R.id.right_image);
        mRight_image.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       finish();
    }
}

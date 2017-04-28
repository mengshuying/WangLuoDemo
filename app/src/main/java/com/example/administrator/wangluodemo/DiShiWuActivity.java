package com.example.administrator.wangluodemo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DiShiWuActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mLxxz_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lixianxz);
        initView();
    }

    private void initView() {
        mLxxz_back = (ImageView) findViewById(R.id.lxxz_back);
        mLxxz_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}

package com.example.administrator.wangluodemo;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import utils.NetworkUtils;

/**
 * date:${DATA}
 * author:孟淑英
 * function:
 */
public class NetworkActivity extends Activity
        implements View.OnClickListener {
    private TextView tvAboutNetwork;
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_network);
        tvAboutNetwork = (TextView) findViewById(R.id.tv_about_network);
        findViewById(R.id.btn_open_wireless_settings).setOnClickListener(this);
        findViewById(R.id.btn_set_wifi_enabled).setOnClickListener(this);
            setAboutNetwork();
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_open_wireless_settings:
                NetworkUtils.openWirelessSettings();
                break;
            case R.id.btn_set_data_enabled:
                NetworkUtils.setDataEnabled(!NetworkUtils.getDataEnabled());
                break;
            case R.id.btn_set_wifi_enabled:
                NetworkUtils.setWifiEnabled(!NetworkUtils.getWifiEnabled());
                break;
         }
          setAboutNetwork();
    }
    private void setAboutNetwork() {
        tvAboutNetwork.setText("isConnected: " + NetworkUtils.isConnected()
                + "\nisAvailableByPing: " + NetworkUtils.isAvailableByPing()
                + "\ngetDataEnabled: " + NetworkUtils.getDataEnabled()
                + "\nis4G: " + NetworkUtils.is4G()
                + "\ngetWifiEnabled: " + NetworkUtils.getWifiEnabled()
                + "\nisWifiConnected: " + NetworkUtils.isWifiConnected()
                + "\nisWifiAvailable: " + NetworkUtils.isWifiAvailable()
                + "\nisAvailableByPing: " + NetworkUtils.isAvailableByPing()
                + "\ngetNetworkOperatorName: " + NetworkUtils.getNetworkOperatorName()
                + "\ngetNetworkTypeName: " + NetworkUtils.getNetworkType()
                + "\ngetIPAddress: " + NetworkUtils.getIPAddress(true)
                + "\ngetDomainAddress: " + NetworkUtils.getDomainAddress("baidu.com")
        );
    }

}

package com.example.administrator.wangluodemo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import bean.Bean;
import bean.Bean_Su;
import utils.MyUrl;
import utils.Utils;

import static com.example.administrator.wangluodemo.R.id.denglu_editText2;
import static com.umeng.socialize.utils.DeviceConfig.context;
import static utils.MyUrl.url;
public class DiShiSanActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mDenglu_editText1;
    private EditText mDenglu_editText2;
    private EditText mDenglu_editText3;
    private EditText mDenglu_editText4;
    private Button mDenglu_button1;
    private Button mDenglu_button2;
    private SharedPreferences mXixi;
    private SharedPreferences.Editor mEditor;
    private int theme = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        Night_styleutils.changeStyle(this, theme, savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.denglu_yuans);
        mDenglu_editText1 = (EditText) findViewById(R.id.denglu_editText1);
        mDenglu_editText2 = (EditText) findViewById(denglu_editText2);
        mDenglu_editText3 = (EditText) findViewById(R.id.denglu_editText3);
        mDenglu_editText4 = (EditText) findViewById(R.id.denglu_editText4);
        mDenglu_button1 = (Button) findViewById(R.id.denglu_button1);
        mDenglu_button2 = (Button) findViewById(R.id.denglu_button2);
        mDenglu_button1.setOnClickListener(this);
        mDenglu_button2.setOnClickListener(this);
        mXixi = getSharedPreferences("xixi", Context.MODE_PRIVATE);
        mEditor = mXixi.edit();
    }
    @Override
    public void onClick(View v){
       switch(v.getId()){
           case R.id.denglu_button1:
               if (TextUtils.isEmpty(mDenglu_editText1.getText().toString())){
                   Toast.makeText(DiShiSanActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
               }else if(TextUtils.isEmpty( mDenglu_editText2.getText().toString())||TextUtils.isEmpty(mDenglu_editText3.getText().toString())){
                   Toast.makeText(DiShiSanActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
               }else if(!mDenglu_editText2.getText().toString().equals(mDenglu_editText3.getText().toString())){
                   Toast.makeText(DiShiSanActivity.this, "两次密码不相同，请重新输入！", Toast.LENGTH_SHORT).show();
               }else if(TextUtils.isEmpty( mDenglu_editText4.getText().toString())){
                   Toast.makeText(DiShiSanActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
               }else{
                   if (Utils.isEmailAddress(mDenglu_editText4.getText().toString())){
                       regis();
                   }else{
                       Toast.makeText(DiShiSanActivity.this, "邮箱格式错误", Toast.LENGTH_SHORT).show();
                   }
               }
               break;
           case R.id.denglu_button2:
               mDenglu_editText4.setVisibility(View.GONE);
               if (TextUtils.isEmpty(mDenglu_editText1.getText().toString())){
                   Toast.makeText(DiShiSanActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
               }else if(TextUtils.isEmpty(mDenglu_editText2.getText().toString())||TextUtils.isEmpty(mDenglu_editText3.getText().toString())){
                   Toast.makeText(DiShiSanActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
               }else if(!mDenglu_editText2.getText().toString().equals(mDenglu_editText3.getText().toString())){
                   Toast.makeText(DiShiSanActivity.this, "两次密码不相同，请重新输入！", Toast.LENGTH_SHORT).show();
               }else{
                     logIn();
                   Intent intent=new Intent(DiShiSanActivity.this,MainActivity.class);
                   startActivity(intent);
                   finish();
               }
               break;
       }
     }
      private void regis(){
          RequestQueue mMQueue = Volley.newRequestQueue(DiShiSanActivity.this);
          StringRequest stringRequest = new StringRequest(Request.Method.POST, MyUrl.LINK_MOBILE_REG, new Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                  Log.i("fff",response);
                  Gson gson=new Gson();
                  Bean bean = gson.fromJson(response, Bean.class);
                  if(bean.getCode()==400){
                      Toast.makeText(DiShiSanActivity.this,bean.getDatas().getError(),Toast.LENGTH_SHORT).show();
                  }else if(bean.getCode()==200){
                      Bean_Su bean_su = gson.fromJson(response, Bean_Su.class);
                      Toast.makeText(DiShiSanActivity.this,bean_su.getDatas().getUsername(),Toast.LENGTH_SHORT).show();
                  }

              }
          }, new Response.ErrorListener() {

              @Override
              public void onErrorResponse(VolleyError error) {
                  Toast.makeText(DiShiSanActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();

              }
          }){ @Override
          protected Map<String, String> getParams(){
              //用户名
              EditText denglu_editText1 = (EditText)findViewById(R.id.denglu_editText1);
              EditText mDenglu_editText2 = (EditText)findViewById(denglu_editText2);
              EditText mDenglu_editText3 = (EditText)findViewById(R.id.denglu_editText3);
              EditText mDenglu_editText4 = (EditText)findViewById(R.id.denglu_editText4);
              //在这里设置需要post的参数
              Map<String, String> params = new HashMap<String, String>();
              params.put("username", denglu_editText1.getText().toString().trim());
              params.put("password", mDenglu_editText2.getText().toString().trim());
              params.put("password_confirm", mDenglu_editText3.getText().toString().trim());
              params.put("email", mDenglu_editText4.getText().toString().trim());
              params.put("client",MyUrl.SYSTEM_TYPE);
              Log.i("fff",params.toString());
              return params;
               }
          };

          mMQueue.add(stringRequest);
      };

      private void logIn(){
          RequestQueue   mMQueue = Volley.newRequestQueue(DiShiSanActivity.this);
          StringRequest stringRequest = new StringRequest(Request.Method.POST, MyUrl.LINK_MOBILE_LOGIN, new Response.Listener<String>() {
              @Override
              public void onResponse(String response){
                  Log.i("fff",response);
                  Gson gson=new Gson();
                  Bean bean = gson.fromJson(response, Bean.class);
                  if(bean.getCode()==400){
                    Toast.makeText(DiShiSanActivity.this,bean.getDatas().getError(),Toast.LENGTH_SHORT).show();
                  }else if(bean.getCode()==200){
                      Bean_Su bean_su = gson.fromJson(response, Bean_Su.class);
                      Toast.makeText(DiShiSanActivity.this,bean_su.getDatas().getUsername(),Toast.LENGTH_SHORT).show();
                      String key = bean_su.getDatas().getKey();
                      mEditor.putString("cun",key);
                      mEditor.commit();
                  }
              }
          }, new Response.ErrorListener(){
              @Override
              public void onErrorResponse(VolleyError error){
                  Toast.makeText(DiShiSanActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
              }
          }){ @Override
          protected Map<String, String> getParams(){
              //用户名
              EditText denglu_editText1 = (EditText)findViewById(R.id.denglu_editText1);
              EditText mDenglu_editText2 = (EditText)findViewById(denglu_editText2);
              //在这里设置需要post的参数
              Map<String, String> params = new HashMap<String, String>();
              params.put("username", denglu_editText1.getText().toString().trim());
              params.put("password", mDenglu_editText2.getText().toString().trim());
              params.put("client",MyUrl.SYSTEM_TYPE);
              Log.i("fff",params.toString());
              return params;
          }
          };

          mMQueue.add(stringRequest);
      };
      }


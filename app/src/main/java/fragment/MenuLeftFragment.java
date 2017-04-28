package fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.wangluodemo.DiShiLiuActivity;
import com.example.administrator.wangluodemo.DiShiSiActivity;
import com.example.administrator.wangluodemo.DiShiWuActivity;
import com.example.administrator.wangluodemo.DiShiYiActivity;
import com.example.administrator.wangluodemo.MainActivity;
import com.example.administrator.wangluodemo.Night_styleutils;
import com.example.administrator.wangluodemo.R;
import com.example.administrator.wangluodemo.ThreeActivity;
import com.example.administrator.wangluodemo.UiUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import adapter.MyAdapter;
import adapter.ViewHolder;
import bean.UserBean;

import static android.R.attr.name;
import static com.example.administrator.wangluodemo.R.id.le_textview3;

import static com.tencent.open.utils.Global.getSharedPreferences;
/**
 * date:${DATA}
 * author:孟淑英
 * function:
 */
public class MenuLeftFragment extends Fragment implements View.OnClickListener {
    private View mView;
    private ListView mCategories;
    private Button btn_more_login;
    private List<UserBean> mMDatas;
    private String[] arr=new String[]{"好友动态","我的话题","收藏","活动","商城","反馈"};
    private int[] arr1=new int[]{R.mipmap.a,R.mipmap.b,R.mipmap.c,R.mipmap.e,R.mipmap.d,R.mipmap.f};
    private ImageView mLeftt_imageview;
    private TextView mTextview;
    private RelativeLayout mLeft_rea;
    private RelativeLayout mLiearlayout;
    private static final String TAG = "MainActivity";
    private static final String APP_ID = "1105602574";//官方获取的APPID
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    private RadioButton mLe_textview3;
    private RadioButton mLe_textview2;
    private RadioButton mLe_textview1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        mTencent = Tencent.createInstance(APP_ID,getActivity().getApplicationContext());
        if (mView == null)
        {
            initView(inflater, container);
        }
        return mView;
    }
    private void initView(LayoutInflater inflater, ViewGroup container)
    {
        mView = inflater.inflate(R.layout.left_menu2, container, false);
          initView();
          add();
          mCategories = (ListView) mView
                .findViewById(R.id.main_listview);
        mCategories.setAdapter(new MyAdapter<UserBean>(getActivity(),R.layout.left_layout,mMDatas) {
            @Override
            public void convert(ViewHolder holder, UserBean item){
                holder.setText(R.id.left_textview,item.getName());
                holder.setImageResource(R.id.left_imageview,item.getImageview());
            }
        });
        mCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent4=new Intent(getActivity(),DiShiLiuActivity.class);
                startActivity(intent4);
            }
        });
    }
    private void initView() {
        mLe_textview1 = (RadioButton) mView.findViewById(R.id.le_textview1);
        mLe_textview2 = (RadioButton) mView.findViewById(R.id.le_textview2);
        mLe_textview3 = (RadioButton) mView.findViewById(le_textview3);
        ImageView image_qq= (ImageView) mView.findViewById(R.id.image_qq);
        ImageView image_phone= (ImageView) mView.findViewById(R.id.image_phone);
        ImageView image_circle= (ImageView) mView.findViewById(R.id.image_circle);
        mLeft_rea = (RelativeLayout) mView.findViewById(R.id.left_rea);
        mLiearlayout = (RelativeLayout) mView.findViewById(R.id.liearlayout);
        btn_more_login = (Button) mView.findViewById(R.id.btn_more_login);
        mLeftt_imageview = (ImageView) mView.findViewById(R.id.leftt_imageview);
        mTextview = (TextView) mView.findViewById(R.id.textview);
        //相机传过来的图片
        Intent intent = getActivity().getIntent();
        Bitmap bitmap = intent.getParcelableExtra("bitmap");
        String name=intent.getStringExtra("name");
        boolean flag = intent.getBooleanExtra("flag",false);
        if(flag){
            mLeft_rea.setVisibility(View.GONE);
            mLiearlayout.setVisibility(View.VISIBLE);
            mLeftt_imageview.setImageBitmap(bitmap);
            mTextview.setText(name);
        }else{
            mLeft_rea.setVisibility(View.VISIBLE);
            mLiearlayout.setVisibility(View.GONE);
         }
        quzhi();
        mLe_textview1.setOnClickListener(this);
        mLe_textview2.setOnClickListener(this);
        mLe_textview3.setOnClickListener(this);
        btn_more_login.setOnClickListener(this);
        mLeftt_imageview.setOnClickListener(this);
        image_qq.setOnClickListener(this);
        image_phone.setOnClickListener(this);
        image_circle.setOnClickListener(this);
    }
    //第三方的图片
    private void quzhi(){
        SharedPreferences lala = getSharedPreferences("lala", Context.MODE_PRIVATE);
        String name1 = lala.getString("name", "");
        String imageview = lala.getString("imageview", "");
        boolean flag1 = lala.getBoolean("flag", false);
        if(flag1){
            mLeft_rea.setVisibility(View.GONE);
            mLiearlayout.setVisibility(View.VISIBLE);
        ImageLoader.getInstance().displayImage(imageview,mLeftt_imageview);
            mTextview.setText(name1);
         }
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.le_textview1:
              Intent intent3=new Intent(getActivity(),DiShiWuActivity.class);
                startActivity(intent3);
                break;
            case R.id.le_textview2:
               Intent intent2=new Intent(getActivity(),DiShiSiActivity.class);
                startActivity(intent2);
                break;
            case le_textview3:
                if("夜间模式".equals(mLe_textview3.getText().toString().trim())){
                    UiUtils.switchAppTheme(getActivity());
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.reload();
                    mLe_textview3.setText("日间模式");
                }
                break;
            case R.id.btn_more_login:
                Intent intent=new Intent(getActivity(), ThreeActivity.class);
                startActivity(intent);
                break;
            case R.id.leftt_imageview:
                Intent intent1=new Intent(getActivity(),DiShiYiActivity.class);
                startActivity(intent1);
                break;
            case R.id.image_qq:
                mIUiListener = new BaseUiListener();
                //all表示获取所有权限
                mTencent.login(getActivity(),"all", mIUiListener);
                break;
            case R.id.image_phone:
                break;
            case R.id.image_circle:
                break;


        }
    }
    public void add(){
        mMDatas = new ArrayList<UserBean>();
       for(int i=0;i<arr.length;i++){
           UserBean bean=new UserBean();
           bean.setName(arr[i]);
           bean.setImageview(arr1[i]);
           mMDatas.add(bean);
       }
    }
    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener{
        @Override
        public void onComplete(Object response) {
            Toast.makeText(getActivity(), "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getActivity().getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response){
                        Log.e(TAG,"登录成功"+response.toString());
                        JSONObject res= (JSONObject) response;
                        String nickName=res.optString("nickname");//获取昵称
                        String figureurl_qq_2=res.optString("figureurl_qq_2");//获取图片
                       Log.i("zzz",figureurl_qq_2);
                        mLeft_rea.setVisibility(View.GONE);
                        mLiearlayout.setVisibility(View.VISIBLE);
                        ImageLoader.getInstance().displayImage(figureurl_qq_2,mLeftt_imageview);
                        mTextview.setText(nickName);
                    }
                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }
                    @Override
                    public void onCancel(){
                        Log.e(TAG,"登录取消");
                    }
                });
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        @Override
        public void onError(UiError uiError){
            Toast.makeText(getActivity(), "授权失败", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCancel(){
            Toast.makeText(getActivity(), "授权取消", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
          }
    @Override
    public void onResume(){
          quzhi();
        super.onResume();
    }
}
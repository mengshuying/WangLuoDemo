package fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.administrator.wangluodemo.R;

import utils.MyHttpUtils;
import xlistview.bawei.com.xlistviewlibrary.XListView;
/**
 * date:${DATA}
 * author:孟淑英
 * function:
 */
public class MyFragment extends Fragment{
    private View mView;
    private XListView mXlistview;
    public static MyFragment getFragment(String uri){
        MyFragment myFragment=new MyFragment();
        Bundle bundle=new Bundle();
        bundle.putString("uri",uri);
        myFragment.setArguments(bundle);
        return myFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.xiao_layout,null);
        return mView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }
    public void initData(){
        Bundle bundle = getArguments();
        String uri = bundle.getString("uri");
        MyHttpUtils myHttpUtils=new MyHttpUtils(getActivity(),mXlistview,uri);
        myHttpUtils.getURL();
      }
    public void initView(){
        mXlistview = (XListView) mView.findViewById(R.id.xlistview);
    }
}

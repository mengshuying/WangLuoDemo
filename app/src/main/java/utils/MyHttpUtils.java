package utils;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import com.example.administrator.wangluodemo.DiShiErActivity;
import com.example.administrator.wangluodemo.R;
import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;
import adapter.MyAdapter;
import adapter.MyListViewAdapter;
import adapter.MyPageAdapter;
import adapter.ViewHolder;
import bean.FenBean;
import bean.JsonBean;
import bean.JsonBean1;
import xlistview.bawei.com.xlistviewlibrary.XListView;
import static com.example.administrator.wangluodemo.DiShiLiuActivity.list_bu;
import static com.example.administrator.wangluodemo.DiShiLiuActivity.list_num;
/**
 * date:${DATA}
 * author:孟淑英
 * function:
 */
public class MyHttpUtils{
    private  Context mContext;
    private XListView mXListView;
    private  String mString;
    private FragmentActivity mFragmentActivity;
    private ViewPager mViewPager;
    private boolean flag=true;
    private int mFirstVisiblePosition;
    public MyHttpUtils(FragmentActivity cc,ViewPager viewpager){
        mFragmentActivity =cc;
        mViewPager =viewpager;
    }
    public MyHttpUtils(Context context,XListView xlistview,String stt){
        mContext =context;
        mXListView =xlistview;
        mString =stt;
    }
  public void getURLL(int channelId, final int startNum){

      for(Integer xx:list_num){
          if(xx==startNum){
              flag=false;
              mXListView.setAdapter(new MyAdapter<FenBean.DataBean>(mContext,R.layout.xlistview_layout,list_bu){
                  @Override
                  public void convert(final ViewHolder holder, FenBean.DataBean item){
                      holder.setText(R.id.textview1,item.getTITLE());
                      holder.setText(R.id.textview2,item.getFROMNAME());
                      holder.setText(R.id.textview3,item.getSHOWTIME());
                      x.image().loadDrawable((String) item.getIMAGEURL(), new ImageOptions.Builder().build(), new Callback.CommonCallback<Drawable>(){
                          @Override
                          public void onSuccess(Drawable result){
                              holder.getimageview(R.id.imageview).setImageDrawable(result);
                          }
                          @Override
                          public void onError(Throwable ex, boolean isOnCallback){
                          }
                          @Override
                          public void onCancelled(CancelledException cex){
                          }
                          @Override
                          public void onFinished(){
                          }
                      });
                  }
              });
              //从当前的位置继续滑动,不会重新从第一条开始
              mXListView.setSelection(mFirstVisiblePosition);
          }
      }

      final RequestParams params = new RequestParams(MyUrl.url2);
      params.addQueryStringParameter("channelId",channelId+"");
      params.addQueryStringParameter("startNum",startNum+"");

      x.http().get(params, new Callback.CommonCallback<String>(){
          @Override
          public void onSuccess(String result){
              //解析result
              MyGson mygson=new MyGson();
              List<FenBean.DataBean> getdata = mygson.getdata2(result);
              if(flag){
                  list_num.add(startNum);
                  for(FenBean.DataBean xx:getdata){
                      list_bu.add(xx);
                  }
                }
              mXListView.setAdapter(new MyAdapter<FenBean.DataBean>(mContext,R.layout.xlistview_layout,list_bu){
                  @Override
                  public void convert(final ViewHolder holder, FenBean.DataBean item){
                      holder.setText(R.id.textview1,item.getTITLE());
                      holder.setText(R.id.textview2,item.getFROMNAME());
                      holder.setText(R.id.textview3,item.getSHOWTIME());
                      x.image().loadDrawable((String) item.getIMAGEURL(), new ImageOptions.Builder().build(), new Callback.CommonCallback<Drawable>(){
                          @Override
                          public void onSuccess(Drawable result){
                              holder.getimageview(R.id.imageview).setImageDrawable(result);
                          }
                          @Override
                          public void onError(Throwable ex, boolean isOnCallback){
                          }
                          @Override
                          public void onCancelled(CancelledException cex){
                          }
                          @Override
                          public void onFinished(){
                          }
                      });
                  }
              });
              mXListView.setSelection(mFirstVisiblePosition);


          }
          //请求异常后的回调方法
          @Override
          public void onError(Throwable ex, boolean isOnCallback){
          }
          //主动调用取消请求的回调方法
          @Override
          public void onCancelled(CancelledException cex){
          }
          @Override
          public void onFinished(){
          }
      });

      //滑动监听
      mXListView.setOnScrollListener(new AbsListView.OnScrollListener(){
          @Override
          public void onScrollStateChanged(AbsListView view, int scrollState){
              //得到当前的光标的第一条
              mFirstVisiblePosition = mXListView.getFirstVisiblePosition();

          }
          @Override
          public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

          }
      });

  }
    public void getURL(){
        final RequestParams params = new RequestParams(MyUrl.url);
        params.addQueryStringParameter("uri",mString);
        Log.i("zzz",params.toString());
        x.http().get(params, new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result){
                //解析result
                Log.i("zzz",result.toString());
                MyGson mygson=new MyGson();
                final List<JsonBean.ResultBean.DataBean> getdata = mygson.getdata(result);
                //注释掉的是通过万能适配器适配的数据  数据是通过Xutils3框架写的
//                mXListView.setAdapter(new MyAdapter<JsonBean.ResultBean.DataBean>(mContext, R.layout.listview_layout,getdata) {
//                    @Override
//                    public void convert(final ViewHolder holder, JsonBean.ResultBean.DataBean item) {
//                        holder.setText(R.id.textt1,item.getTitle());
//                        holder.setText(R.id.textt2,item.getAuthor_name());
//                        x.image().loadDrawable(item.getThumbnail_pic_s02(), new ImageOptions.Builder().build(), new CommonCallback<Drawable>(){
//                            @Override
//                            public void onSuccess(Drawable result){
//                                 holder.getimageview(R.id.imageview).setImageDrawable(result);
//                            }
//                            @Override
//                            public void onError(Throwable ex, boolean isOnCallback){
//
//                            }
//                            @Override
//                            public void onCancelled(CancelledException cex){
//
//                            }
//                            @Override
//                            public void onFinished(){
//
//                            }
//                        });
//                    }
//                });
                mXListView.setAdapter(new MyListViewAdapter(mContext,getdata));
                mXListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                        Intent intent=new Intent(mContext,DiShiErActivity.class);
                        intent.putExtra("chuan",getdata.get(position-1).getUrl());
                        mContext.startActivity(intent);
                    }
                });
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback){
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex){
            }
            @Override
            public void onFinished(){
            }
        });
    }
    public void getURL2(){
        RequestParams params = new RequestParams(MyUrl.url1);
        params.addQueryStringParameter("uri","news");
        Log.i("zzz","getURL2"+params.toString());
        x.http().get(params, new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result){
                //解析result
                Log.i("zzz","result  "+result.toString());
                MyGson mygson=new MyGson();
                List<JsonBean1.ResultBean.DateBean> getdata = mygson.getdata1(result);
                /*
                * 查询+存储数据库
                * **/
                cun(getdata);
                cha();
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback){
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex){
            }
            @Override
            public void onFinished(){
            }
        });
    }
    private void cun(List<JsonBean1.ResultBean.DateBean> getdata){
        //用集合向child_info表中插入多条数据
        ArrayList<ChildInfo> childInfos = new ArrayList<ChildInfo>();
        for(int i=0;i<10;i++){
            ChildInfo info= new ChildInfo();
            info.title=getdata.get(i).getTitle();
            info.uri=getdata.get(i).getUri();
            if(getdata.get(i).getTitle().equals("头条")||getdata.get(i).getTitle().equals("社会")){
                info.zhangtai="1";
            }else{
                info.zhangtai="0";
            }
             childInfos.add(info);
          }
        //db.save()方法不仅可以插入单个对象，还能插入集合
        try {
            DbManager mDb = UtilsDb.get();
            mDb.save(childInfos);
        }catch (DbException e){
            e.printStackTrace();
        }
    }
     public void cha(){
         List<JsonBean1.ResultBean.DateBean> list=new ArrayList<JsonBean1.ResultBean.DateBean>();
         try {
             DbManager mDb = UtilsDb.get();
             List<ChildInfo> all = mDb.findAll(ChildInfo.class);
             for(ChildInfo xx:all){
                 if(xx.zhangtai.equals("1")){
                     list.add(new JsonBean1.ResultBean.DateBean(xx.uri,xx.title));
                 }
             }
             mViewPager.setAdapter(new MyPageAdapter(mFragmentActivity.getSupportFragmentManager(),list));
         }catch (DbException e){
             e.printStackTrace();
          }
       }
//    public List<ChildInfo> cha(){
//        try {
//            DbManager mDb = UtilsDb.get();
//            ChildInfo first = mDb.findFirst(ChildInfo.class);
//            //添加查询条件进行查询
//            List<ChildInfo> all = mDb.selector(ChildInfo.class).findAll();
//            Log.i("ggg","长度为："+all.toString());
//            return all;
//         }catch (DbException e){
//            e.printStackTrace();
//         }
//       return null;
//    }
}

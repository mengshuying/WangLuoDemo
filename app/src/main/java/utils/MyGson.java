package utils;
import com.google.gson.Gson;

import java.util.List;

import bean.FenBean;
import bean.JsonBean;
import bean.JsonBean1;
/**
 * date:${DATA}
 * author:孟淑英
 * function:
 */
public class MyGson {
   public List<JsonBean.ResultBean.DataBean> getdata(String stt){
      Gson gson=new Gson();
       JsonBean jsonBean = gson.fromJson(stt, JsonBean.class);
       List<JsonBean.ResultBean.DataBean> data = jsonBean.getResult().getData();
       return data;
   }
    public List<JsonBean1.ResultBean.DateBean> getdata1(String stt){
        Gson gson=new Gson();
        JsonBean1 jsonBean = gson.fromJson(stt, JsonBean1.class);
        List<JsonBean1.ResultBean.DateBean> list = jsonBean.getResult().getDate();
        return list;
    }
    public List<FenBean.DataBean> getdata2(String stt){
        Gson gson=new Gson();
        FenBean jsonBean = gson.fromJson(stt, FenBean.class);
        List<FenBean.DataBean> list1 = jsonBean.getData();
        return list1;
    }

}

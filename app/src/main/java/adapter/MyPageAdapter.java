package adapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;
import bean.JsonBean1;
import fragment.MyFragment;
/**
 * date:${DATA}
 * author:孟淑英
 * function:
 */
public  class MyPageAdapter extends FragmentPagerAdapter{
    private final List<JsonBean1.ResultBean.DateBean> mDateBeen;
    public MyPageAdapter(FragmentManager fm, List<JsonBean1.ResultBean.DateBean> getdata){
        super(fm);
        mDateBeen =getdata;
    }
    @Override
    public Fragment getItem(int position){
        return MyFragment.getFragment(mDateBeen.get(position).getUri());
    }
    @Override
    public int getCount(){
        return mDateBeen.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mDateBeen.get(position).getTitle();
    }
}

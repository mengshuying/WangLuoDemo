package adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
/**
 * date:${DATA}
 * author:孟淑英
 * function:
 */
public abstract class MyAdapter<T> extends BaseAdapter{
    /** 上下文 */
    protected Context mContext;
    /** 数据源 */
    protected List<T> mData;
    private int mLayoutId;

    public MyAdapter(Context mContext, int layoutId, List<T> data) {
        super();
        this.mContext = mContext;
        this.mLayoutId = layoutId;
        this.mData=data;
    }
    @Override
    public int getCount() {
        return mData.size();
    }
    @Override
    public T getItem(int position) {
        return (T) (mData == null ? 0 : mData.get(position));
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView,
                parent, mLayoutId, position);
        convert(holder, getItem(position));
        return holder.getConvertView();

    }
    /*** 实现给View赋数据的方法 */
    public abstract void convert(ViewHolder holder, T item);
}

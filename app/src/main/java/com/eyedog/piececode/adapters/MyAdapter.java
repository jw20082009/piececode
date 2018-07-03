
package com.eyedog.piececode.adapters;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by walljiang on 2015/8/27.
 */
public abstract class MyAdapter<T> extends BaseAdapter {
    private ArrayList<T> mDatas;

    protected Context mContext;

    public void addData(T obj) {
        if (obj != null)
            mDatas.add(obj);
    }

    public void addDatas(ArrayList<T> mDatas) {
        if (mDatas != null)
            mDatas.addAll(mDatas);
    }

    public void removeData(T obj) {
        if (obj != null)
            mDatas.remove(obj);
    }

    public void removeDatas(ArrayList<T> mDatas) {
        if (mDatas != null) {
            mDatas.removeAll(mDatas);
        }
    }

    public MyAdapter(Context context, ArrayList<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

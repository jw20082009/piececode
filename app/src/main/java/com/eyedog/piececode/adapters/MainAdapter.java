
package com.eyedog.piececode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eyedog.piececode.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by walljiang on 2015/8/27.
 */
public class MainAdapter extends MyAdapter {
    public MainAdapter(Context context, ArrayList<?> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder cache;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_list, null);
            cache = new Holder();
            cache.title = (TextView) convertView.findViewById(R.id.tv_list_title);
            convertView.setTag(R.id.tag_item_cache, cache);
        } else {
            cache = (Holder) convertView.getTag(R.id.tag_item_cache);
        }
        HashMap<String,String> pageDatas = (HashMap<String, String>) getItem(position);
        String title = pageDatas.get("title");
        cache.title.setText(title);
        return convertView;
    }

    private class Holder {
        private TextView title;
    }
}

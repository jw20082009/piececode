package com.eyedog.piececode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eyedog.piececode.adapters.MainAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView list;

    private ArrayList<HashMap<String, String>> pageDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initList();
    }

    private void initList() {
        pageDatas = getListDatas();
        list = (ListView) findViewById(R.id.category_list);
        MainAdapter adapter = new MainAdapter(this, pageDatas);
        list.setAdapter(adapter);
        list.setOnItemClickListener(itemListener);
    }

    private ArrayList<HashMap<String, String>> getListDatas() {
        ArrayList<HashMap<String, String>> datas = new ArrayList<>();
        datas.add(getPageMap("TransitionActivity", "控件切换动画"));
        datas.add(getPageMap("TextStickerActivity","文字编辑与粘贴"));
        return datas;
    }

    private HashMap<String, String> getPageMap(String key, String title) {
        HashMap<String, String> page = new HashMap<>();
        page.put("key", key);
        page.put("title", title);
        return page;
    }

    private AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            HashMap<String, String> page = pageDatas.get(position);
            new Thread("") {
                @Override
                public void run() {
                    super.run();

                }
            }.start();
            try {
                Class clazz = Class.forName("com.eyedog.piececode." + page.get("key"));
                Intent intent = new Intent(MainActivity.this, clazz);
                startActivity(intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    };
}

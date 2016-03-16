package com.example.sunrx.listviewdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SwipeListView lv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_list = (SwipeListView) findViewById(R.id.lv_list);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("Hello World " + i);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list);
        lv_list.setAdapter(adapter);
    }

    class SimpleAdapter extends BaseAdapter {
        private Context context;
        private List<String> list;

        public SimpleAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list, null);
            TextView textView = (TextView) convertView.findViewById(R.id.tv_content);
            textView.setText(list.get(position));
            return convertView;
        }
    }
}

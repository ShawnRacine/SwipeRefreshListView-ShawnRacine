package com.example.sunrx.listviewdemo.TouchEventTest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sunrx.listviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunrx on 2016/3/16.
 */
public class MActivity extends Activity {

    private MLayout mlayout;
    private MListView mlistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m);

        mlayout = (MLayout) findViewById(R.id.mlayout);
        mlistview = (MListView) findViewById(R.id.mlistview);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("Hello World " + i);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list);
        mlistview.setAdapter(adapter);
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

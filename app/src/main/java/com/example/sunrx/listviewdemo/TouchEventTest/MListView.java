package com.example.sunrx.listviewdemo.TouchEventTest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by sunrx on 2016/3/16.
 */
public class MListView extends ListView {
    public MListView(Context context) {
        super(context);
    }

    public MListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Snake", "ListView-Patch-Dowm");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("Snake", "ListView-Patch-Move");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("Snake", "ListView-Patch-Up");
                break;
        }
        boolean state = super.dispatchTouchEvent(ev);
        Log.i("Snake", "ListView-Patch:" + state);
        return state;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Snake", "ListView-Inter-Dowm");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("Snake", "ListView-Inter-Move");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("Snake", "ListView-Inter-Up");
                break;
        }
//        return super.onInterceptTouchEvent(ev);
        boolean state = super.onInterceptTouchEvent(ev);
        Log.i("Snake", "ListView-Inter:" + state);
        return state;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Snake", "ListView-Touch-Dowm");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("Snake", "ListView-Touch-Move");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("Snake", "ListView-Touch-Up");
                break;
        }
//        return super.onTouchEvent(ev);
        boolean state = super.onTouchEvent(ev);
        Log.i("Snake", "ListView-Touch:" + state);
        return state;
    }
}

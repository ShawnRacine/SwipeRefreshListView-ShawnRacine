package com.example.sunrx.listviewdemo.TouchEventTest;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by sunrx on 2016/3/16.
 */
public class MLayout extends LinearLayout {
    public MLayout(Context context) {
        super(context);
    }

    public MLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Snake", "Layout-Patch-Dowm");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("Snake", "Layout-Patch-Move");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("Snake", "Layout-Patch-Up");
                break;
        }
//        return super.dispatchTouchEvent(ev);
        boolean state = super.dispatchTouchEvent(ev);
        Log.i("Snake", "Layout-Patch:" + state);
        return state;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Snake", "Layout-Inter-Dowm");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("Snake", "Layout-Inter-Move");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("Snake", "Layout-Inter-Up");
                break;
        }
//        return super.onInterceptTouchEvent(ev);
        boolean state = super.onInterceptTouchEvent(ev);
        Log.i("Snake", "Layout-Inter:" + state);
        return state;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("Snake", "Layout-Touch-Dowm");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("Snake", "Layout-Touch-Move");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("Snake", "Layout-Touch-Up");
                break;
        }
//        return super.onTouchEvent(ev);
        boolean state = super.onTouchEvent(ev);
        Log.i("Snake", "Layout-Touch:" + state);
        return state;
    }
}

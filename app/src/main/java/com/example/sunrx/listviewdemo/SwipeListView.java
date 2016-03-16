package com.example.sunrx.listviewdemo;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by sunrx on 2016/3/14.
 */
public class SwipeListView extends ListView {
    //sliding resistance coefficient.
    private static final float PULL_RESISTANCE = 2.0f;

    private static final int NOP = 0;
    private static final int PULL_TO_REFRESH = 1;
    private static final int RELEASE_TO_REFRESH = 2;
    private static final int REFRESHING = 3;
    private int state = NOP;

    private Context context;

    private LinearLayout headerLayout;
    private final LinearLayout ll_header;
    private ImageView iv_loading;
    private TextView tv_loading;

    private RotateAnimation upwardAnimation;
    private RotateAnimation downwardAnimation;

    private int measureHeaderHeight;

    private float action_down_RawY;
    private float Down_RawY;
    private boolean slideUp;

    public SwipeListView(Context context) {
        this(context, null);
    }

    public SwipeListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //inflate headerLayout
        headerLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.header_layout, null);
        ll_header = (LinearLayout) headerLayout.findViewById(R.id.ll_header);
        iv_loading = (ImageView) headerLayout.findViewById(R.id.iv_loading);
        tv_loading = (TextView) headerLayout.findViewById(R.id.tv_loading);
        //
        ll_header.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (ll_header.getHeight() > 0) {
                    //Only when greater than zero, to assign @measureHeight.
                    measureHeaderHeight = ll_header.getHeight();

                    //hide the headerview of listview.
                    setHeaderPadding(-measureHeaderHeight);

                    //In order to avoid trigger for many times, need to remove listener in time.
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });

        //add header to listview
        addHeaderView(headerLayout);

        //
        upwardAnimation = new RotateAnimation(0f, -180f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        upwardAnimation.setDuration(200);
        upwardAnimation.setFillAfter(true);
        //
        downwardAnimation = new RotateAnimation(-180f, 0f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        downwardAnimation.setDuration(200);
        downwardAnimation.setFillAfter(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                action_down_RawY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float diff_y = ev.getRawY() - action_down_RawY;

                //*Through the resistance to slow the padding>0 process.
                float paddingTop = diff_y;

                //*If lack of judgment(diff_y>0),it will affect the calculation results when diff_y<0.
                //*such as ev.getRawY() - start_y - measureHeight = -100;after diff_y /= 2,will get -50.
                //*expect that:-100,-98,-96...;otherwise,-100,-50,0..
                if (paddingTop > 0) {
                    paddingTop /= PULL_RESISTANCE;
                }

                if (state != REFRESHING) {
                    paddingTop -= measureHeaderHeight;
                }

                //Through the resistance to slow the whole (contains padding>0 and padding<0) process.
//                float paddingTop = diff_y / PULL_RESISTANCE;
//                if (state != REFRESHING) {
//                    paddingTop -= measureHeaderHeight;
//                }

                //
                if (state != REFRESHING && paddingTop > -measureHeaderHeight) {
                    setHeaderPadding((int) paddingTop);
                } else if (state == REFRESHING && paddingTop > 0) {
                    setHeaderPadding((int) paddingTop);
                } else {
                    setState(NOP);
                    setHeaderPadding(-measureHeaderHeight);
//                    bounceBackHeader(-measureHeaderHeight);
                }

                //
                if (paddingTop > -measureHeaderHeight && state == NOP) {
                    setState(PULL_TO_REFRESH);
                } else if (paddingTop > 0 && state == PULL_TO_REFRESH) {
                    setState(RELEASE_TO_REFRESH);
                } else if (paddingTop < 0 && state == RELEASE_TO_REFRESH) {
                    setState(PULL_TO_REFRESH);
                }
                break;
            case MotionEvent.ACTION_UP:
                action_down_RawY = 0;
                //
                if (state == RELEASE_TO_REFRESH || (state == REFRESHING && ll_header.getPaddingTop() > 0)) {
                    setState(REFRESHING);
                    bounceBackHeader(-ll_header.getPaddingTop());
                } else if (state == PULL_TO_REFRESH || (state == REFRESHING && ll_header.getPaddingTop() < 0)) {
                    bounceBackHeader(-ll_header.getPaddingTop() - measureHeaderHeight);
                }
                break;
        }

        //*if return super.onTouchEvent(ev), listview's scroller will coexist with header's padding.
        return super.onTouchEvent(ev);
        //*if return true, listview will cannot scroll.
//        return true;
    }

    private void setHeaderPadding(int paddingTop) {
        ll_header.setPadding(0, paddingTop, 0, 0);
//        Log.i("Snake", "move:" + paddingTop);
    }

    private void bounceBackHeader(int yTranslate) {

        TranslateAnimation bounceAnimation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0,
                TranslateAnimation.ABSOLUTE, 0,
                TranslateAnimation.ABSOLUTE, 0,
                TranslateAnimation.ABSOLUTE, yTranslate);

        bounceAnimation.setDuration(200);
        bounceAnimation.setFillEnabled(true);
        bounceAnimation.setFillAfter(false);
        bounceAnimation.setFillBefore(true);
        bounceAnimation.setInterpolator(new OvershootInterpolator(1.4f));
        bounceAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (state == REFRESHING && ll_header.getPaddingTop() > 0) {
                    setHeaderPadding(0);
                } else {
                    setHeaderPadding(-measureHeaderHeight);
                    setState(NOP);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        startAnimation(bounceAnimation);
    }

    private void setState(int state) {
        if (state == NOP) {
            tv_loading.setText("下拉刷新");
            //
            iv_loading.setImageResource(R.mipmap.pull_refresh_arrow_down);
            iv_loading.setBackgroundResource(0);
            //
            iv_loading.clearAnimation();
            iv_loading.startAnimation(downwardAnimation);
        } else if (state == PULL_TO_REFRESH) {
            tv_loading.setText("下拉刷新");
            //
            if (this.state == RELEASE_TO_REFRESH) {
                iv_loading.clearAnimation();
                iv_loading.startAnimation(downwardAnimation);
            }
        } else if (state == RELEASE_TO_REFRESH) {
            tv_loading.setText("释放更新");
            //
            iv_loading.clearAnimation();
            iv_loading.startAnimation(upwardAnimation);
        } else if (state == REFRESHING) {
            tv_loading.setText("加载中");
            //
            iv_loading.setImageResource(0);
            iv_loading.setBackgroundResource(R.drawable.loading);
            //
            iv_loading.clearAnimation();
            AnimationDrawable animationDrawable = (AnimationDrawable) iv_loading.getBackground();
            animationDrawable.start();
        }
        this.state = state;
    }

}

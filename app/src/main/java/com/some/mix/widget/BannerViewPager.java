package com.some.mix.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.Scroller;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * @author cyl
 * @date 2020/5/27
 */
public class BannerViewPager extends ViewPager {
    private TaskRunnable mTaskRunnable;
    private Handler mHandler;
    private BannerViewPager instance;

    public static boolean mIsRunning = false; // 是否正在执行

    public BannerViewPager(@NonNull Context context) {
        this(context, null);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        instance = this;
        OnPageChangeListener mOnPagerChangeListener = new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state){
                    case ViewPager.SCROLL_STATE_IDLE: // 空闲状态
                        start();
                        break;

                    case ViewPager.SCROLL_STATE_DRAGGING: // 手指触摸滑动
                        stop();
                        break;

                    case ViewPager.SCROLL_STATE_SETTLING: // 手指松开, 惯性滑动
                        break;
                }
            }
        };
        addOnPageChangeListener(mOnPagerChangeListener);
        // 修改ViewPager的滚动速度
        setViewPagerDuration();
    }

    private static class TaskRunnable implements Runnable {

        public WeakReference<BannerViewPager> weakReference;
        TaskRunnable(BannerViewPager bannerViewPager){
            this.weakReference = new WeakReference<>(bannerViewPager);
        }
        @Override
        public void run() {
            // 执行切换任务
            BannerViewPager instance = weakReference.get();
            if (instance == null){
                return;
            }
            instance.setCurrentItem();
        }
    }
    /**
     * 开启任务
     */
    private void startTimingTask(){
        if (mHandler == null && !mIsRunning){
            mHandler = new Handler();
            mTaskRunnable = new TaskRunnable(instance);
            mHandler.postDelayed(mTaskRunnable, 6000);
            mIsRunning = true;
        }
    }

    /**
     * 结束任务
     */
    private void stopTimingTask(){
        if (mHandler != null && mIsRunning){
            mHandler.removeCallbacks(mTaskRunnable);
            mHandler = null;
            mIsRunning = false;
        }
    }

    public void clear(){
        mTaskRunnable.weakReference.clear();
    }

    private void setCurrentItem(){
        setCurrentItem(getCurrentItem() + 1, true);
        mHandler.postDelayed(mTaskRunnable, 6000);
    }

    public void start(){
        startTimingTask();
    }

    public void stop(){
        stopTimingTask();
    }

    private class FixedSpeedScroll extends Scroller {

        private int mDuration = 750; // 毫秒
        public FixedSpeedScroll(Context context) {
            super(context);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 防止ViewPager可见时第一次切换无动画效果
        // 滚动监听
        setFirstLayout(false);
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    private void setFirstLayout(boolean isFirstLayout){
        try {
            Class<?> clazz = Class.forName("androidx.viewpager.widget.ViewPager");
            Field field = clazz.getDeclaredField("mFirstLayout");
            field.setAccessible(true);
            field.setBoolean(this, isFirstLayout);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

    /**
     * 修改ViewPager的滑动速度
     */
    private void setViewPagerDuration(){
        try {
            Class<?> clazz = Class.forName("androidx.viewpager.widget.ViewPager");
            FixedSpeedScroll mScroller = new FixedSpeedScroll(getContext());
            Field field = clazz.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(this, mScroller);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }
}

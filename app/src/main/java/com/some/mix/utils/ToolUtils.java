package com.some.mix.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author cyl
 * @date 2020/5/28
 */
public class ToolUtils {

    public static String parseTime(long mTime){
        long time;
        time = (System.currentTimeMillis() - mTime) / 1000;
        int day, hour, minute, second;
        day = (int) (time / 3600 / 24);
        if (day == 1){
            return "1天前";
        }
        if (day > 1){
            return new SimpleDateFormat("yyyy-MM-dd").format(mTime);
        }
        hour = (int) (time / 3600);
        if (hour > 0){
            return hour + "小时前";
        }
        minute = (int) (time / 60);
        if (minute > 0){
            return minute + "分钟前";
        }
        second = (int) time;
        if (second >= 0) {
            return "刚刚";
        }
        return "";
    }

    public static long dateToStamp(String str) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(str);
        return date.getTime();
    }

    public static void setTagTextColor(TextView tagView){
        int red, green, blue;
        Random random = new Random();
        red = random.nextInt(255);
        green = random.nextInt(255);
        blue = random.nextInt(255);
        int color = Color.rgb(red, green, blue);
        tagView.setTextColor(color);
    }

    @SuppressLint("WrongConstant")
    public static void setLayoutManager(PullToRefreshRecyclerView recyclerView, Context context){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @SuppressLint("WrongConstant")
    public static void setLayoutManager(RecyclerView recyclerView, Context context){
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    public static void openPullRecyclerView(PullToRefreshRecyclerView recyclerView, PullToRefreshListener listener){
        //设置是否开启上拉加载
        recyclerView.setLoadingMoreEnabled(true);
        //设置是否开启下拉刷新
        recyclerView.setPullRefreshEnabled(true);
        //设置是否显示上次刷新的时间
        recyclerView.displayLastRefreshTime(true);
        //设置刷新回调
        recyclerView.setPullToRefreshListener(listener);
    }

    public static void onDestroyPullRecyclerView(PullToRefreshRecyclerView recyclerView){
        recyclerView.setRefreshComplete();
        recyclerView.setLoadMoreComplete();
        recyclerView.loadMoreEnd();
    }
}

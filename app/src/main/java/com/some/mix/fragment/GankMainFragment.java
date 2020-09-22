package com.some.mix.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.some.mix.R;
import com.some.mix.activity.CateActivity;
import com.some.mix.activity.GirlActivity;
import com.some.mix.adapter.GankBannerAdapter;
import com.some.mix.adapter.GankDetailAdapter;
import com.some.mix.base.BaseFragment;
import com.some.mix.bean.gank.Banner;
import com.some.mix.bean.gank.Detail;
import com.some.mix.callback.DataCallBack;
import com.some.mix.gankapi.BannerApi;
import com.some.mix.gankapi.CateDetailApi;
import com.some.mix.utils.ToolUtils;
import com.some.mix.widget.BannerViewPager;
import com.xcj.luck.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author cyl
 * @date 2020/8/20
 */
public class GankMainFragment extends BaseFragment implements PullToRefreshListener, View.OnClickListener {

    private String cateS; // 类型
    private List<Detail.DataBean> dataBeans = new ArrayList<>();
    private GankDetailAdapter gAdapter;
    private GankBannerAdapter bannerAdapter;
    private BannerViewPager viewPager;
    private PullToRefreshRecyclerView ganKRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView(View view) {
        randomCate();
        ganKRecyclerView = view.findViewById(R.id.ganKRecyclerView);
        ToolUtils.setLayoutManager(ganKRecyclerView, getContext());
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_gank_header, ganKRecyclerView, false);
        viewPager = headView.findViewById(R.id.viewPager);
        headView.findViewById(R.id.tvGanHuo).setOnClickListener(this);
        headView.findViewById(R.id.tvArticle).setOnClickListener(this);
        headView.findViewById(R.id.tvGirl).setOnClickListener(this);
        headView.findViewById(R.id.tvMvp).setOnClickListener(this);
        initBanner();
        ganKRecyclerView.addHeaderView(headView);
        gAdapter = new GankDetailAdapter(getActivity(), R.layout.gank_item_detail, dataBeans);
        ganKRecyclerView.setAdapter(gAdapter);
        //设置是否开启上拉加载
        ganKRecyclerView.setLoadingMoreEnabled(false);
        //设置是否开启下拉刷新
        ganKRecyclerView.setPullRefreshEnabled(true);
        //设置是否显示上次刷新的时间
        ganKRecyclerView.displayLastRefreshTime(true);
        //设置刷新回调
        ganKRecyclerView.setPullToRefreshListener(this);

        ganKRecyclerView.onRefresh();
    }

    private void initBanner(){
        BannerApi api = new BannerApi();
        api.setCallBack(new DataCallBack<List<Banner.DataBean>>() {
            @Override
            public void onSuccess(List<Banner.DataBean> response) {
                bannerAdapter = new GankBannerAdapter(getActivity(), response);
                viewPager.setAdapter(bannerAdapter);
                viewPager.setOffscreenPageLimit(response.size());
                viewPager.setCurrentItem(1000 * response.size());
                bannerAdapter.notifyDatas(response);
            }
            @Override
            public void onFail(String errorMsg) {

            }
        });
        api.execute();
    }

    private void initData(){
        CateDetailApi api = new CateDetailApi();
        api.setCateApi("random");
        api.setCate(cateS);
        api.setType("All");
        api.setCount(50);
        api.setCallBack(new DataCallBack<List<Detail.DataBean>>() {
            @Override
            public void onSuccess(List<Detail.DataBean> response) {
                    dataBeans.clear();
                    dataBeans.addAll(response);
                    if (gAdapter != null){
                        gAdapter.notifyDataSetChanged();
                    }
            }

            @Override
            public void onFail(String errorMsg) {

            }
        });
        api.execute();
    }

    private void randomCate(){
        int cate = new Random().nextInt(2);
        switch (cate){
            case 0:
                cateS = "GanHuo";
                break;
            case 1:
                cateS = "Article";
                break;
            default:
                cateS = "GanHuo";
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden){
            viewPager.start();
        } else {
            viewPager.stop();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onRefresh() {
        ganKRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ganKRecyclerView.setRefreshComplete();
                randomCate();
                initData();
            }
        }, 2 * 1000);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToolUtils.onDestroyPullRecyclerView(ganKRecyclerView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvGanHuo:
                toDetailFragment("GanHuo");
                break;
            case R.id.tvArticle:
                toDetailFragment("Article");
                break;

            case R.id.tvGirl:
               startActivity(new Intent(getActivity(), GirlActivity.class));
                break;

            case R.id.tvMvp:
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
            default:
                break;
        }
    }
    private void toDetailFragment(String type){
        CateActivity.startCateDetailActivity(getActivity(), type);
    }
}

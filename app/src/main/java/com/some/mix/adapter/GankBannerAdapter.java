package com.some.mix.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.some.mix.R;
import com.some.mix.bean.gank.Banner;
import com.some.mix.bean.gank.Detail;
import com.some.mix.activity.WebViewActivity;
import com.some.mix.utils.GlideUtil;
import com.some.mix.utils.OnClickUtil;

import java.util.List;

/**
 * @author cyl
 * @date 2020/8/21
 */
public class GankBannerAdapter extends PagerAdapter {

    private Context mContext;
    private SparseArray<View> views;
    private List<Banner.DataBean> mBannerDatas;
    public GankBannerAdapter(Context context, List<Banner.DataBean> dataBeans){
        this.mContext = context;
        this.mBannerDatas = dataBeans;
        views = new SparseArray<>();
    }

    public void notifyDatas(List<Banner.DataBean> dataBeans){
        this.mBannerDatas = dataBeans;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mBannerDatas == null) return 0;
        return mBannerDatas.size() <= 1 ? mBannerDatas.size() : Integer.MAX_VALUE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        View view = views.get(position);
        if (view == null){
            position %= mBannerDatas.size();
            final Banner.DataBean dataBean = mBannerDatas.get(position);
            view = LayoutInflater.from(mContext).inflate(R.layout.item_banner, container, false);
            ImageView imageView = view.findViewById(R.id.img);
            TextView textView = view.findViewById(R.id.tv_title);
            if (dataBean.getImage().startsWith("http://")){
                String url = dataBean.getImage().replace("http://","https://");
                GlideUtil.loadImage(mContext, url,imageView);
            }else {
                GlideUtil.loadImage(mContext, dataBean.getImage(),imageView);
            }
            textView.setText(dataBean.getTitle());
            view.setOnClickListener(new OnClickUtil() {
                @Override
                public void onMultiClick(View v) {
                    Intent intent = new Intent(container.getContext(), WebViewActivity.class);
                    Detail.DataBean bean = new Detail.DataBean();
                    bean.setTitle(dataBean.getTitle());
                    bean.setUrl(dataBean.getUrl());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("url", bean);
                    intent.putExtras(bundle);
                    container.getContext().startActivity(intent);
                }
            });
            views.put(position, view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        views.clear();
        return POSITION_NONE;
    }
}

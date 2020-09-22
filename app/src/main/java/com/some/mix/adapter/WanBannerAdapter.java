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
import com.some.mix.activity.WebViewActivity;
import com.some.mix.bean.wanandroid.Article;
import com.some.mix.bean.wanandroid.Banner;
import com.some.mix.utils.GlideUtil;
import com.some.mix.utils.OnClickUtil;

import java.util.List;

/**
 * @author cyl
 * @date 2020/8/24
 */
public class WanBannerAdapter extends PagerAdapter {

    private Context mContext;
    private SparseArray<View> views;
    private List<Banner> mBannerDatas;

    public WanBannerAdapter(List<Banner> bannerList, Context context){
        this.mBannerDatas = bannerList;
        this.mContext = context;
        views = new SparseArray<>();
    }
    public void notifyDatas(List<Banner> bannerList){
       this.mBannerDatas = bannerList;
       notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mBannerDatas == null)
            return 0;
        return mBannerDatas.size() <= 1 ? mBannerDatas.size() : Integer.MAX_VALUE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        View view = views.get(position);
        if (view == null){
            position %= mBannerDatas.size();
            final Banner bean = mBannerDatas.get(position);
            view = LayoutInflater.from(mContext).inflate(R.layout.item_banner, container, false);
            ImageView imageView = view.findViewById(R.id.img);
            TextView textView = view.findViewById(R.id.tv_title);
            //Glide.with(mContext).load(bean.getImagePath()).into(imageView);
            GlideUtil.loadImage(mContext, bean.getImagePath(), imageView);
            textView.setText(bean.getTitle());
            view.setOnClickListener(new OnClickUtil() {
                @Override
                public void onMultiClick(View v) {
                    Intent intent = new Intent(container.getContext(), WebViewActivity.class);
                    Article article = new Article();
                    article.setTitle(bean.getTitle());
                    article.setLink(bean.getUrl());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("obj", article);
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

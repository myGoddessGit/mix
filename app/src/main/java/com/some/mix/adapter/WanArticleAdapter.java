package com.some.mix.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.some.mix.R;
import com.some.mix.activity.WebViewActivity;
import com.some.mix.bean.wanandroid.Article;
import com.some.mix.constans.Constant;
import com.some.mix.utils.ToolUtils;
import com.some.mix.utils.OnClickUtil;

import java.util.List;

/**
 * @author cyl
 * @date 2020/8/24
 */
public class WanArticleAdapter extends BaseAdapter<Article> {

    private int mType;

    public WanArticleAdapter(Context context, int layoutId, List<Article> datas, int type) {
        super(context, layoutId, datas);
        this.mType = type;
    }

    @Override
    public void convert(ViewHolder holder, Article article) {

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (mType == Constant.LIST_TYPE.HOME || mType == Constant.LIST_TYPE.SEARCH){
            holder.setViewVisible(R.id.tv_tag, View.VISIBLE);
            if (datas.get(position).isTop()){
                holder.setActivated(R.id.tv_tag, true).setText(R.id.tv_tag, "置顶").setTextColor(R.id.tv_tag, Color.RED);
            } else if (datas.get(position).isFresh()){
                holder.setActivated(R.id.tv_tag, true).setText(R.id.tv_tag, "新").setTextColor(R.id.tv_tag, Color.RED);
            } else if (datas.get(position).getTags() != null && datas.get(position).getTags().size() > 0){
                holder.setActivated(R.id.tv_tag, false).setText(R.id.tv_tag, datas.get(position).getTags().get(0).getName()).setTextColor(R.id.tv_tag, ContextCompat.getColor(super.context, R.color._009a61));
            } else {
                holder.setViewVisible(R.id.tv_tag, View.GONE);
            }
            holder.setText(R.id.tv_time, ToolUtils.parseTime(datas.get(position).getPublishTime()))
                    .setText(R.id.tv_title, Html.fromHtml(datas.get(position).getTitle()))
                    .setText(R.id.tv_author, datas.get(position).getAuthor())
                    .setText(R.id.tv_type, String.format("%1$s / %2$s",datas.get(position).getSuperChapterName(), datas.get(position).getChapterName()));
        }
        if (mType == Constant.LIST_TYPE.TREE){
            holder.setViewVisible(R.id.tv_tag, View.GONE)
                    .setText(R.id.tv_time, ToolUtils.parseTime(datas.get(position).getPublishTime()))
                    .setText(R.id.tv_title, Html.fromHtml(datas.get(position).getTitle()))
                    .setText(R.id.tv_author, datas.get(position).getAuthor())
                    .setText(R.id.tv_type, String.format("%1$s / %2$s",datas.get(position).getSuperChapterName(), datas.get(position).getChapterName()));
        }

        holder.itemView.setOnClickListener(new OnClickUtil() {
            @Override
            public void onMultiClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("obj", datas.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
}

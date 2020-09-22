package com.some.mix.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.some.mix.R;
import com.some.mix.activity.WebViewActivity;
import com.some.mix.bean.wanandroid.Article;
import com.some.mix.utils.ToolUtils;
import com.some.mix.utils.GlideUtil;
import com.some.mix.utils.OnClickUtil;

import java.util.List;

/**
 * @author cyl
 * @date 2020/8/25
 */
public class ProjectListAdapter extends BaseAdapter<Article> {

    public ProjectListAdapter(Context context, int layoutId, List<Article> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final Article item) {
        ImageView imageView = holder.getView(R.id.iv_img);
        GlideUtil.loadImage(super.context, item.getEnvelopePic(), imageView);
        holder.setText(R.id.tv_title, item.getTitle());
        holder.setText(R.id.tv_desc, item.getDesc());
        holder.setText(R.id.tv_time, ToolUtils.parseTime(item.getPublishTime()));
        holder.setText(R.id.tv_name, item.getAuthor());
        holder.itemView.setOnClickListener(new OnClickUtil() {
            @Override
            public void onMultiClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("obj", item);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
}

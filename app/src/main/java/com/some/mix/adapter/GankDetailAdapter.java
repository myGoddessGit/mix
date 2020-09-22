package com.some.mix.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.some.mix.R;
import com.some.mix.bean.gank.Detail;
import com.some.mix.activity.WebViewActivity;
import com.some.mix.utils.ToolUtils;
import com.some.mix.utils.GlideUtil;
import com.some.mix.utils.OnClickUtil;

import java.text.ParseException;
import java.util.List;

/**
 * @author cyl
 * @date 2020/8/19
 */
public class GankDetailAdapter extends BaseAdapter<Detail.DataBean> {

    public GankDetailAdapter(Context context, int layoutId, List<Detail.DataBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final Detail.DataBean dataBean){
        ImageView imageView = holder.getView(R.id.iv_img);
        if (dataBean.getImages().size() > 0){
            if (dataBean.getImages().get(0).startsWith("http://")){
                String url = dataBean.getImages().get(0).replace("http://","https://");
                GlideUtil.loadImage(context,url,imageView);
            }else {
                GlideUtil.loadImage(context, dataBean.getImages().get(0),imageView);
            }
        } else{
            imageView.setVisibility(View.GONE);
        }
        holder.setText(R.id.tv_title, "标题:  " + dataBean.getTitle());
        holder.setText(R.id.tv_desc, "描述:  " + dataBean.getDesc());
        holder.setText(R.id.tv_author, "作者:  " + dataBean.getAuthor());
        holder.setText(R.id.tv_cate_type, "类型:  " + dataBean.getCategory() + " | " + dataBean.getType());
        try {
            holder.setText(R.id.tv_time, "时间:  " + ToolUtils.parseTime(ToolUtils.dateToStamp(dataBean.getPublishedAt())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new OnClickUtil() {
            @Override
            public void onMultiClick(View v) {
                Intent intent = new Intent(context, WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("url", dataBean);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
}

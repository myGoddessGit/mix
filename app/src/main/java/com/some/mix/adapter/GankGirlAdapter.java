package com.some.mix.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.some.mix.R;
import com.some.mix.activity.GirlDetailActivity;
import com.some.mix.bean.gank.Detail;
import com.some.mix.utils.OnClickUtil;
import java.util.List;

/**
 * @author cyl
 * @date 2020/8/22
 */
public class GankGirlAdapter extends RecyclerView.Adapter<GankGirlAdapter.ViewHolder>{

    private Context mContext;
    private List<Detail.DataBean> mList;
    private String str = "";

    public GankGirlAdapter(Context context, List<Detail.DataBean> list){
        mContext = context;
        mList = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gank_girl, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String url = mList.get(position).getUrl();
//        if (url.startsWith("http://")){
//            url = url.replace("http://", "https://");
//            Glide.with(mContext).load(url).into(holder.imageView);
//            str = url;
//        }
        Glide.with(mContext).load(url).into(holder.imageView);
        final String desc = mList.get(position).getDesc();
        holder.itemView.setOnClickListener(new OnClickUtil() {
            @Override
            public void onMultiClick(View v) {
                Intent intent = new Intent(mContext, GirlDetailActivity.class);
                intent.putExtra("GUrl",url);
                intent.putExtra("GDesc",desc);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gImageView);
        }
    }
}

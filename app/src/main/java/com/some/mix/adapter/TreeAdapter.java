package com.some.mix.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.some.mix.R;
import com.some.mix.activity.TreeDetailActivity;
import com.some.mix.bean.wanandroid.Tree;
import com.some.mix.utils.OnClickUtil;
import java.util.List;

/**
 * @author cyl
 * @date 2020/6/1
 */
public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.ViewHolder> {

    private Context mContext;

    private List<Tree> mTreeList;

    public TreeAdapter(Context context, List<Tree> treeList){
        this.mContext = context;
        this.mTreeList = treeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tree, null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvTitle.setText(mTreeList.get(position).getName());
        holder.tvContent.setText("");
        for (Tree.ChildrenBean children : mTreeList.get(position).getChildren()){
            holder.tvContent.append(children.getName() + "      ");
        }
        holder.itemView.setOnClickListener(new OnClickUtil() {
            @Override
            public void onMultiClick(View v) {
                Intent intent = new Intent(mContext, TreeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ccid", mTreeList.get(position));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTreeList != null ? mTreeList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}

package com.some.mix.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.some.mix.R;
import com.some.mix.bean.wanandroid.Article;
import com.some.mix.bean.wanandroid.Navi;
import com.some.mix.callback.DataCallBack;
import com.some.mix.utils.ToolUtils;
import com.some.mix.wanandroidapi.NaviApi;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import java.util.List;


/**
 * @author cyl
 * @date 2020/8/27
 */
public class NaviActivity extends FragmentActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private TagFlowLayout titleLayout;
    private NameAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi_layout);
        initView();
        initData();
    }

    @SuppressLint("WrongConstant")
    private void initView(){
        ((TextView)findViewById(R.id.tv_topBarText)).setText("导航");
        findViewById(R.id.iv_topBarSearch).setVisibility(View.GONE);
        findViewById(R.id.iv_topBarBack).setOnClickListener(this);
        recyclerView = findViewById(R.id.lvName);
        ToolUtils.setLayoutManager(recyclerView, this);
        titleLayout = findViewById(R.id.tlTitle);
    }

    private void initData(){
        NaviApi api = new NaviApi();
        api.setCallBack(new DataCallBack<List<Navi>>() {
            @Override
            public void onSuccess(List<Navi> response) {
                initNameAndTitle(response);
            }

            @Override
            public void onFail(String errorMsg) {

            }
        });
        api.execute();
        Log.i("NaviApi", api.buildRealUrl());
    }

    private void initNameAndTitle(final List<Navi> list){
        adapter = new NameAdapter(this, list);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickPosition(new NameAdapter.OnClickPosition() {
            @Override
            public void clickPosition(final int index) {
                titleLayout.setAdapter(new TagAdapter<Article>(list.get(index).getArticles()) {
                    @Override
                    public View getView(FlowLayout parent, int position, Article data) {
                        TextView tagView = (TextView) LayoutInflater.from(NaviActivity.this).inflate(R.layout.item_search_tag, titleLayout, false);
                        tagView.setText(list.get(index).getArticles().get(position).getTitle());
                        ToolUtils.setTagTextColor(tagView);
                        return tagView;
                    }
                });
                titleLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        Article article = new Article();
                        article.setTitle(list.get(index).getArticles().get(position).getTitle());
                        article.setLink(list.get(index).getArticles().get(position).getLink());
                        Intent intent = new Intent(NaviActivity.this, WebViewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("obj",article);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        return false;
                    }
                });
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy != 0){
                    if (adapter != null){
                        adapter.isCheck = false;
                    }
                }
            }
        });

    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_topBarBack){
            finish();
        }
    }

    public static class NameAdapter extends RecyclerView.Adapter<NameAdapter.NameViewHolder>{

        private Context mContext;
        private List<Navi> mList;
        public NameAdapter(Context context, List<Navi> list){
            mContext = context;
            mList = list;
        }
        public boolean isCheck = true;
        @NonNull
        @Override
        public NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_navi_name, parent, false);
            return new NameViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final NameViewHolder holder, final int position) {
            holder.textView.setText(mList.get(position).getName());
            if (isCheck) {
                onClickPosition.clickPosition(0);
            }
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickPosition.clickPosition(position);
                    Toast.makeText(mContext, mList.get(position).getName(),Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList != null ? mList.size() : 0;
        }

        public static class NameViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            public NameViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.tvName);
            }
        }

        private OnClickPosition onClickPosition;

        public void setOnClickPosition(OnClickPosition position){
            this.onClickPosition = position;
        }
        public interface OnClickPosition{

            void clickPosition(int index);
        }
    }
}

package com.xcj.luck.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.xcj.luck.Constants
import com.xcj.luck.R
import com.xcj.luck.durationFormat
import com.xcj.luck.glide.GlideApp
import com.xcj.luck.mvp.model.bean.HomeBean
import com.xcj.luck.ui.activity.VideoDetailActivity
import com.xcj.luck.view.recyclerview.ViewHolder
import com.xcj.luck.view.recyclerview.adapter.CommonAdapter

/**
 * @author cyl
 * @date 2020/8/6
 */
class WatchHistoryAdapter(context: Context, dataList: ArrayList<HomeBean.Issue.Item>, layoutId: Int):
        CommonAdapter<HomeBean.Issue.Item>(context, dataList, layoutId){

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        with(holder){
            setText(R.id.tv_title, data.data?.title!!)
            setText(R.id.tv_tag, "#${data.data.category} / ${durationFormat(data.data.duration)}")
            setImagePath(R.id.iv_video_small_card, object : ViewHolder.HolderImageLoader(data.data.cover.detail){
                override fun loadImage(iv: ImageView, path: String) {
                    GlideApp.with(mContext)
                        .load(path)
                        .placeholder(R.drawable.placeholder_banner)
                        .transition(DrawableTransitionOptions().crossFade())
                        .into(iv)
                }
            })
        }
        holder.getView<TextView>(R.id.tv_title).setTextColor(ContextCompat.getColor(mContext, R.color.color_black))
        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_video_small_card), data)
        })
    }

    /**
     * 跳转到视频详情页面播放
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item){
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.TRANSITION, true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            val pair = Pair<View,String>(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }
}
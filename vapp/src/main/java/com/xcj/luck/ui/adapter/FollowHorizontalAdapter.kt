package com.xcj.luck.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.orhanobut.logger.Logger
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
 * 关注 水平的 RecyclerViewAdapter
 */
class FollowHorizontalAdapter(mContext: Context, categoryList: ArrayList<HomeBean.Issue.Item>, layoutId: Int):
        CommonAdapter<HomeBean.Issue.Item>(mContext, categoryList, layoutId) {

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        val horizontalItemData = data.data
        holder.setImagePath(R.id.iv_cover_feed, object : ViewHolder.HolderImageLoader(data.data?.cover?.feed!!){
            override fun loadImage(iv: ImageView, path: String) {
                GlideApp.with(mContext)
                    .load(path)
                    .placeholder(R.drawable.placeholder_banner)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(iv)
            }
        })

        holder.setText(R.id.tv_title, horizontalItemData?.title?: "")
        // 格式化时间
        val timeFormat = durationFormat(horizontalItemData?.duration)
        with(holder){
            Logger.d("horizontalItemData===title:${horizontalItemData?.title}tag:${horizontalItemData?.tags?.size}")
            if (horizontalItemData?.tags != null && horizontalItemData.tags.size > 0){
                setText(R.id.tv_tag, "#${horizontalItemData.tags[0].name} / $timeFormat")
            } else {
                setText(R.id.tv_tag, "#$timeFormat")
            }

            setOnItemClickListener(listener = View.OnClickListener {
                goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_cover_feed), data)
            })
        }
    }
    // 跳转播放
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item){
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.TRANSITION, true)
        if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP){
            val pair = Pair<View, String>(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }

}
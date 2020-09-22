package com.xcj.luck.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.xcj.luck.Constants
import com.xcj.luck.R
import com.xcj.luck.durationFormat
import com.xcj.luck.glide.GlideApp
import com.xcj.luck.mvp.model.bean.HomeBean
import com.xcj.luck.ui.activity.VideoDetailActivity
import com.xcj.luck.view.recyclerview.ViewHolder
import com.xcj.luck.view.recyclerview.adapter.CommonAdapter
import io.reactivex.Observable

/**
 * @author cyl
 * @date 2020/8/6
 * 首页精选的adapter
 */
class HomeAdapter (context: Context, data: ArrayList<HomeBean.Issue.Item>)
    : CommonAdapter<HomeBean.Issue.Item>(context, data, -1) {

    var bannerItemSize = 0

    companion object {
        private const val ITEM_TYPE_BANNER = 1  //Banner 类型
        private const val ITEM_TYPE_TEXT_HEADER = 2  // textHeader
        private const val ITEM_TYPE_CONTENT = 3  // item
    }

    /**
     * 设置Banner 大小
     */
    fun setBannerSize(count: Int){
        bannerItemSize = count
    }

    /**
     * 添加更多数据
     */
    fun addItemData(itemList: ArrayList<HomeBean.Issue.Item>){
        this.mData.addAll(itemList)
        notifyDataSetChanged()
    }

    // 获取Item的类型
    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> ITEM_TYPE_BANNER
            mData[position + bannerItemSize - 1].type == "textHeader" -> ITEM_TYPE_TEXT_HEADER
            else -> ITEM_TYPE_CONTENT
        }
    }

    override fun getItemCount(): Int {
        return when {
            mData.size > bannerItemSize -> mData.size - bannerItemSize + 1
            mData.isEmpty() -> 0
            else -> 1
        }
    }

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when (getItemViewType(position)){
            // banner
            ITEM_TYPE_BANNER -> {
                val bannerItemData: ArrayList<HomeBean.Issue.Item> = mData.take(bannerItemSize).toCollection(ArrayList())
                val bannerFeedList = ArrayList<String>()
                val bannerTitleList = ArrayList<String>()
                // 取出banner 显示的image 和 title
                Observable.fromIterable(bannerItemData)
                    .subscribe {
                        list ->
                        bannerFeedList.add(list.data?.cover?.feed?:"")
                        bannerTitleList.add(list.data?.title?:"")
                    }
                with(holder){
                    getView<BGABanner>(R.id.banner).run {
                        setAutoPlayAble(bannerFeedList.size > 1)
                        setData(bannerFeedList, bannerTitleList)
                        setAdapter {banner, _, feedImageUrl, position ->
                            GlideApp.with(mContext)
                                .load(feedImageUrl)
                                .transition(DrawableTransitionOptions().crossFade())
                                .placeholder(R.drawable.placeholder_banner)
                                .into(banner.getItemImageView(position))
                        }
                    }
                }
                holder.getView<BGABanner>(R.id.banner).setDelegate {_, imageView, _, i ->
                    goToVideoPlayer(mContext as Activity, imageView, bannerItemData[i])
                }
            }
            ITEM_TYPE_TEXT_HEADER -> {
                holder.setText(R.id.tvHeader, mData[position + bannerItemSize - 1].data?.text?: "")
            }
            ITEM_TYPE_CONTENT -> {
                setVideoItem(holder, mData[position + bannerItemSize - 1])
            }
        }
    }

    // 创建布局
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType){
            ITEM_TYPE_BANNER ->
                ViewHolder(inflaterView(R.layout.item_home_banner, parent))
            ITEM_TYPE_TEXT_HEADER ->
                ViewHolder(inflaterView(R.layout.item_home_header, parent))
            else ->
                ViewHolder(inflaterView(R.layout.item_home_content, parent))
        }
    }

    /**
     * 加载布局
     */
    private fun inflaterView(mLayoutId: Int, parent: ViewGroup): View {
        val view = mInflater?.inflate(mLayoutId, parent, false)
        return view ?: View(parent.context)
    }

    private fun setVideoItem(holder: ViewHolder, item: HomeBean.Issue.Item){
        val itemData = item.data
        val defAvatar = R.mipmap.default_avatar
        val cover = itemData?.cover?.feed
        var avatar = itemData?.author?.icon
        var tagText: String?= "#"

        // 作者的出处为空, 就展示提供者的信息
        if (avatar.isNullOrEmpty()){
            avatar = itemData?.provider?.icon
        }
        GlideApp.with(mContext)
            .load(cover)
            .placeholder(R.drawable.placeholder_banner)
            .transition(DrawableTransitionOptions().crossFade())
            .into(holder.getView(R.id.iv_cover_feed))

        GlideApp.with(mContext)
                    .load(if (avatar.isNullOrEmpty()) defAvatar else avatar)
                    .placeholder(R.mipmap.default_avatar).circleCrop()
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(holder.getView(R.id.iv_avatar))

        holder.setText(R.id.tv_title, itemData?.title?: "")
        // 遍历标签 取前四个
        itemData?.tags?.take(4)?.forEach{
            tagText += (it.name + "/")
        }
        // 格式化时间
        val timeFormat = durationFormat(itemData?.duration)
        tagText += timeFormat
        holder.setText(R.id.tv_tag, tagText!!)
        holder.setText(R.id.tv_category, "#" + itemData?.category)
        holder.setOnItemClickListener(listener = View.OnClickListener {
            goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_cover_feed), item)
        })
    }

    // 跳转播放
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item){
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.TRANSITION, true)
        if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP){
            val pair = Pair(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }

}
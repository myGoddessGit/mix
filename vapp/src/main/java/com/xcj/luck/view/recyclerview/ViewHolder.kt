package com.xcj.luck.view.recyclerview


import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @author cyl
 * @date 2020/8/3
 *
 */
class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    // 用于缓存已经找的界面
    private var mView: SparseArray<View>? = null
    init {
        mView = SparseArray()
    }
    fun <T : View> getView(viewId: Int): T {
        //对已有的view做缓存
        var view: View? = mView?.get(viewId)
        //使用缓存的方式减少findViewById的次数
        if (view == null) {
            view = itemView.findViewById(viewId)
            mView?.put(viewId, view)
        }
        return view as T
    }

    fun <T : ViewGroup> getViewGroup(viewId: Int): T {
        // 对已有的view左缓存
        var view: View? = mView?.get(viewId)
        // 使用缓存的方式减少findViewById的次数
        if (view == null){
            view = itemView.findViewById(viewId)
            mView?.put(viewId, view)
        }
        return view as T
    }

    fun setText(viewId: Int, text: CharSequence): ViewHolder {
        val view = getView<TextView>(viewId)
        view.text = "" + text
        return this
    }

    fun setHintText(viewId: Int, text: CharSequence): ViewHolder {
        val view = getView<TextView>(viewId)
        view.hint = "" + text
        return this
    }

    /**
     * 设置图片
     * @param viewId
     * @param resId
     */
    fun setImageResource(viewId: Int, resId: Int): ViewHolder {
        val iv = getView<ImageView>(viewId)
        iv.setImageResource(resId)
        return this
    }

    /**
     * 加载图片资源路径
     */
    fun setImagePath(viewId: Int, imageLoader: HolderImageLoader): ViewHolder{
        val iv = getView<ImageView>(viewId)
        imageLoader.loadImage(iv, imageLoader.path)
        return this
    }

    abstract class HolderImageLoader(val path: String){

        /**
         * 需要重写这个方法加载图片
         */
        abstract fun loadImage(iv: ImageView, path: String)
    }

    /**
     * 设置View的Visibility
     */
    fun setViewVisibility(viewId: Int, visibility: Int): ViewHolder {
        getView<View>(viewId).visibility = visibility
        return this
    }

    /**
     * 设置条目的点击事件
     */
    fun setOnItemClickListener(listener: View.OnClickListener){
        itemView.setOnClickListener(listener)
    }

    /**
     * 设置条目的长按事件-
     */
    fun setOnItemLongClickListener(listener: View.OnLongClickListener){
        itemView.setOnLongClickListener(listener)
    }
}
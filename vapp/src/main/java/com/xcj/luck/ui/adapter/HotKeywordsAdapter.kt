package com.xcj.luck.ui.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayoutManager
import com.xcj.luck.R
import com.xcj.luck.view.recyclerview.ViewHolder
import com.xcj.luck.view.recyclerview.adapter.CommonAdapter

/**
 * @author cyl
 * @date 2020/8/6
 * Tag 标签布局的adapter
 */
class HotKeywordsAdapter (mContext: Context, mList: ArrayList<String>, layoutId: Int):
        CommonAdapter<String>(mContext, mList, layoutId){

    /**
     * kotlin 的函数可以作为参数
     */
    private var mOnTagItemClick: ((tag: String) -> Unit)? = null

    fun setOnTagItemClickListener(onTagItemClickListener: (tag: String) -> Unit) {
        this.mOnTagItemClick = onTagItemClickListener
    }

    override fun bindData(holder: ViewHolder, data: String, position: Int) {
        holder.setText(R.id.tv_title, data)

        val params = holder.getView<TextView>(R.id.tv_title).layoutParams
        if (params is FlexboxLayoutManager.LayoutParams){
            params.flexGrow = 1.0f
        }
        holder.setOnItemClickListener ( View.OnClickListener {
            mOnTagItemClick?.invoke(data)
        })
    }

}

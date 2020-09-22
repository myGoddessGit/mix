package com.xcj.luck.view.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xcj.luck.view.recyclerview.MultipleType
import com.xcj.luck.view.recyclerview.ViewHolder

/**
 * @author cyl
 * @date 2020/8/3
 */
abstract class CommonAdapter<T>(var mContext: Context, var mData: ArrayList<T>, private var mLayoutId: Int): RecyclerView.Adapter<ViewHolder>() {

    protected var mInflater: LayoutInflater? = null
    private var mTypeSupport: MultipleType<T>? = null

    private var mItemClickListener: OnItemClickListener? = null
    private var mItemLongClickListener: OnItemLongClickListener? = null
    init {
        mInflater = LayoutInflater.from(mContext)
    }
    constructor(context: Context, data: ArrayList<T>, typeSupport: MultipleType<T>): this(context, data, -1){
        this.mTypeSupport = typeSupport
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (mTypeSupport != null){
            mLayoutId = viewType
        }
        // 创建view
        val view = mInflater?.inflate(mLayoutId, parent,false)
        return ViewHolder(view!!)
    }

    override fun getItemViewType(position: Int): Int {
        // 多布局问题
        return mTypeSupport?.getLayoutId(mData[position], position)?: super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 绑定数据
        bindData(holder, mData[position], position)
        // 点击事件
        mItemClickListener?.let {
            holder.itemView.setOnClickListener{mItemClickListener!!.onItemClick(mData[position], position)}
        }
        // 长按点击事件
        mItemLongClickListener?.let {
            holder.itemView.setOnLongClickListener{mItemLongClickListener!!.onItemLongClick(mData[position], position)}
        }
    }

    /**
     * 传递必要的参数
     */
    protected abstract fun bindData(holder: ViewHolder, data: T, position: Int)

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener){
        this.mItemClickListener = itemClickListener
    }

    fun setOnItemLongClickListener(itemLongClickListener: OnItemLongClickListener){
        this.mItemLongClickListener = itemLongClickListener
    }
}
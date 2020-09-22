package com.xcj.luck.ui.activity

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.xcj.luck.Constants
import com.xcj.luck.MyApplication
import com.xcj.luck.R
import com.xcj.luck.base.BaseActivity
import com.xcj.luck.mvp.model.bean.HomeBean
import com.xcj.luck.ui.adapter.WatchHistoryAdapter
import com.xcj.luck.utils.StatusBarUtil
import com.xcj.luck.utils.WatchHistoryUtils
import kotlinx.android.synthetic.main.activity_video_detail.*
import kotlinx.android.synthetic.main.fragment_hot.*
import kotlinx.android.synthetic.main.fragment_hot.toolbar
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author cyl
 * @date 2020/8/7
 * 观看的历史记录
 */
class WatchHistoryActivity : BaseActivity(){

    private var itemListData = ArrayList<HomeBean.Issue.Item>()

    companion object {
        private const val HISTORY_MAX = 20
    }
    /**
     * 布局
     */
    override fun layoutId(): Int {
        return R.layout.layout_watch_history
    }

    /**
     * 初始化数据
     */
    override fun initData() {
        multipleStatusView.showLoading()
        itemListData = queryWatchHistory()
    }

    /**
     * 初始化数据
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initView() {
        toolbar.setNavigationOnClickListener { finish() }
        val mAdapter = WatchHistoryAdapter(this, itemListData, R.layout.item_video_small_card)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter

        if (itemListData.size > 0){
            multipleStatusView.showContent()
        } else {
            multipleStatusView.showEmpty()
        }
         // 状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
        StatusBarUtil.setPaddingSmart(this, mRecyclerView)
    }

    override fun start() {

    }

    private fun queryWatchHistory(): ArrayList<HomeBean.Issue.Item>{
        val watchList = ArrayList<HomeBean.Issue.Item>()
        val historyAll = WatchHistoryUtils.getAll(Constants.FILE_WATCH_HISTORY_NAME, MyApplication.context) as Map<*,*>
        // 将key升序排列
        val keys = historyAll.keys.toTypedArray()
        Arrays.sort(keys)
        val keyLength = keys.size
        val hisLength = if (keyLength > HISTORY_MAX) HISTORY_MAX else keyLength
        // 反序列化和遍历 添加观看的历史记录
        (1..hisLength).mapTo(watchList){
            WatchHistoryUtils.getObject(Constants.FILE_WATCH_HISTORY_NAME, MyApplication.context, keys[keyLength -it] as String) as HomeBean.Issue.Item
        }
        return watchList
    }

}
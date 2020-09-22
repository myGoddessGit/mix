package com.xcj.luck.ui.activity

import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.xcj.luck.R
import com.xcj.luck.base.BaseActivity
import com.xcj.luck.utils.CleanLeakUtil
import com.xcj.luck.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_profile_homepage.*

/**
 * @author cyl
 * @date 2020/8/7
 */
class ProfileHomePageActivity : BaseActivity(){

    private var mOffset = 0
    private var mScrollY = 0

    /**
     * 布局
     */
    override fun layoutId(): Int {
        return R.layout.activity_profile_homepage
    }

    override fun initData() {

    }

    override fun initView() {
        // 状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)

        refreshLayout.setOnMultiPurposeListener(object : SimpleMultiPurposeListener(){
            override fun onHeaderPulling(header: RefreshHeader?, percent: Float, offset: Int, headerHeight: Int, extendHeight: Int) {
                mOffset = offset / 2
                parallax.translationY = (mOffset - mScrollY).toFloat()
                toolbar.alpha = 1 - Math.min(percent, 1f)
            }

            override fun onHeaderReleasing(header: RefreshHeader?, percent: Float, offset: Int, footerHeight: Int, extendHeight: Int) {
                mOffset = offset / 2
                parallax.translationY = (mOffset - mScrollY).toFloat()
                toolbar.alpha = 1 - Math.min(percent, 1f)
            }
        })
        scrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{
            private var lastScrollY = 0
            private val h = DensityUtil.dp2px(170f)
            private val color = ContextCompat.getColor(applicationContext, R.color.colorPrimary) and 0x00ffffff
            override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                var tScrollY = scrollY
                if (lastScrollY < h){
                    tScrollY = Math.min(h, tScrollY)
                    mScrollY = if (tScrollY > h) h else tScrollY
                    buttonBarLayout.alpha = 1f * mScrollY / h
                    toolbar.setBackgroundColor(255 * mScrollY / h shl 24 or color)
                    parallax.translationY = (mOffset - mScrollY).toFloat()
                }
                lastScrollY = tScrollY
            }
        })
        buttonBarLayout.alpha = 0f
        toolbar.setBackgroundColor(0)
        //back 返回
        toolbar.setNavigationOnClickListener { finish() }

    }

    override fun start() {

    }

    override fun onDestroy() {
        CleanLeakUtil.fixInputMethodManagerLeak(this)
        super.onDestroy()
    }

}
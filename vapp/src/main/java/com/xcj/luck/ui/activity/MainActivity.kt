package com.xcj.luck.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.Window
import androidx.fragment.app.FragmentTransaction
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.xcj.luck.R
import com.xcj.luck.base.BaseActivity
import com.xcj.luck.mvp.model.bean.TabEntity
import com.xcj.luck.showToast
import com.xcj.luck.ui.fragment.DiscoveryFragment
import com.xcj.luck.ui.fragment.HomeFragment
import com.xcj.luck.ui.fragment.HotFragment
import com.xcj.luck.ui.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main_mvp.*

/**
 * @author cyl
 * @date 2020/8/7
 */
class MainActivity : BaseActivity(){

    private val mTitles = arrayOf("每日精选", "发现", "热门")

    // 未被选中的图标
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_home_normal, R.mipmap.ic_discovery_normal, R.mipmap.ic_hot_normal)
    // 被选中的图标
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_home_selected, R.mipmap.ic_discovery_selected, R.mipmap.ic_hot_selected)
    private val mTabEntities = ArrayList<CustomTabEntity>()

    private var mHomeFragment: HomeFragment? = null
    private var mDiscoveryFragment: DiscoveryFragment? = null
    private var mHotFragment: HotFragment? = null
    //private var mMineFragment: MineFragment? = null

    // 默认为0
    private var mIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (savedInstanceState != null){
            mIndex = savedInstanceState.getInt("currTabIndex")
        }
        super.onCreate(savedInstanceState)
        initTab()
        tab_layout.currentTab = mIndex
        switchFragment(mIndex)
    }
    /**
     * 布局
     */
    override fun layoutId(): Int {
        return R.layout.activity_main_mvp
    }

    /**
     * 初始化底部菜单
     */
    private fun initTab(){
        (0 until mTitles.size)
            .mapTo(mTabEntities){TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it])}
        // 为Tab赋值
        tab_layout.setTabData(mTabEntities)
        tab_layout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                switchFragment(position)
            }

            override fun onTabReselect(position: Int) {

            }
        })
    }

    private fun switchFragment(position: Int){
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0 -> // 首页
                mHomeFragment?.let {
                    transaction.show(it)
                } ?: HomeFragment.getInstance(mTitles[position]).let {
                    mHomeFragment = it
                    transaction.add(R.id.fl_container, it, "home")
                }
            1 -> { // 发现
                mDiscoveryFragment?.let {
                    transaction.show(it)
                }?: DiscoveryFragment.getInstance(mTitles[position]).let {
                    mDiscoveryFragment = it
                    transaction.add(R.id.fl_container, it, "discovery")
                }
            }
            2 -> { // 热门
                mHotFragment?.let {
                    transaction.show(it)
                }?: HotFragment.getInstance(mTitles[position]).let {
                    mHotFragment = it
                    transaction.add(R.id.fl_container, it, "hot")
                }
            }
        }
        mIndex = position
        tab_layout.currentTab = mIndex
        transaction.commitAllowingStateLoss()
    }

    /**
     * 隐藏所有的Fragment
     */
    private fun hideFragments(transaction: FragmentTransaction){
        mHomeFragment?.let { transaction.hide(it) }
        mDiscoveryFragment?.let { transaction.hide(it) }
        mHotFragment?.let { transaction.hide(it) }
    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        // 记录fragment的位置, 防止崩溃, fragment错乱
        if (tab_layout != null){
            outState.putInt("currTabIndex", mIndex)
        }
    }

    /**
     * 初始化数据
     */
    override fun initData() {

    }

    /**
     * 初始化View
     */
    override fun initView() {

    }

    override fun start() {

    }
//    private var mExitTime: Long = 0
//
//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK){
//            if (System.currentTimeMillis().minus(mExitTime) <=  1500){
//                finish()
//            } else {
//                mExitTime = System.currentTimeMillis()
//                showToast("再按一次退出程序")
//            }
//            return true
//        }
//        return super.onKeyDown(keyCode, event)
//    }

}
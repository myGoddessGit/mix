package com.xcj.luck.ui.activity

import android.Manifest
import android.content.Intent
import android.graphics.Typeface
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.xcj.luck.MyApplication
import com.xcj.luck.R
import com.xcj.luck.base.BaseActivity
import com.xcj.luck.utils.AppUtils
import kotlinx.android.synthetic.main.activity_splash.*
import pub.devrel.easypermissions.EasyPermissions


/**
 * @author cyl
 * @date 2020/8/7
 */
class SplashActivity : BaseActivity(){

    private var textTypeface: Typeface? = null

    private var descTypeface: Typeface? = null

    private var alphaAnimation: AlphaAnimation? = null

    init {
        textTypeface = Typeface.createFromAsset(MyApplication.context.assets, "fonts/Lobster-1.4.otf")
        descTypeface = Typeface.createFromAsset(MyApplication.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }
    /**
     * 布局
     */
    override fun layoutId(): Int {
        return R.layout.activity_splash
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
        tv_app_name.typeface = textTypeface // 设置字体
        tv_splash_desc.typeface = descTypeface  // 设置字体
        tv_version_name.text = "v${AppUtils.getVerName(MyApplication.context)}"
        // 渐变展示启动屏
        alphaAnimation = AlphaAnimation(0.3f, 1.0f)
        alphaAnimation?.duration = 2000  // 动画过渡时间
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                redirectTo()
            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })
        checkPermission()
    }

    override fun start() {

    }

    fun redirectTo(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun checkPermission(){
        val perms = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        EasyPermissions.requestPermissions(this, "该应用需要以下权限, 请允许", 0, *perms)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        if (requestCode == 0){
            if (perms.isNotEmpty()){
                if (perms.contains(Manifest.permission.READ_PHONE_STATE) && perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    if (alphaAnimation != null){
                        iv_web_icon.startAnimation(alphaAnimation)
                    }
                }
            }
        }
    }

}
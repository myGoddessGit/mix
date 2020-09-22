package com.xcj.luck.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.xcj.luck.R
import java.lang.IllegalArgumentException

/**
 * @author cyl
 * @date 2020/8/1
 */
class ExpandableTextView : LinearLayout, View.OnClickListener {

    private var mTextView: TextView? = null
    private var mButton: ImageView? = null

    private var mRelayout: Boolean = false

    private var mCollapsed = true // 标记处于折叠or展开状态, 默认为折叠状态

    private var mExpandDrawable: Drawable? = null // 折叠图标

    private var mCollapseDrawable: Drawable? = null // 展开图标

    private var mMaxCollapsedLines: Int = 0 // 设置最多的折叠行数
    private var mTextHeightWithMaxLines: Int = 0 // TextView的最大高度

    private var mMarginBetweenTxtAndBottom: Int = 0
    private var mCollapsedHeight: Int = 0
    private var mAnimating: Boolean = false
    private var mAnimAlphaStart: Float = 0.toFloat()
    private var mAnimationDuration: Int = 0
    private val mListener: OnExpandStateChangeListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet): super(context, attrs){
        initView(attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet){
        var ta = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView)
        mMaxCollapsedLines = ta.getInt(R.styleable.ExpandableTextView_maxCollapsedLines, MAX_COLLAPSED_LINES)
        mAnimationDuration = ta.getInt(R.styleable.ExpandableTextView_animDuration, DEFAULT_ANIM_DURATION)
        mAnimAlphaStart = ta.getFloat(R.styleable.ExpandableTextView_animAlphaStart, DEFAULT_ANIM_ALPHA_START)
        mExpandDrawable = ta.getDrawable(R.styleable.ExpandableTextView_collapseDrawable)
        mCollapseDrawable = ta.getDrawable(R.styleable.ExpandableTextView_collapseDrawable)
        if (mExpandDrawable == null) {
            mExpandDrawable = getDrawable(context, R.mipmap.ic_action_down_white)
        }
        if (mCollapseDrawable == null){
            mCollapseDrawable = getDrawable(context, R.mipmap.ic_action_up_white)
        }
        ta.recycle()
        orientation = VERTICAL
        visibility = View.GONE
    }

    override fun setOrientation(orientation: Int) {
        if (HORIZONTAL == orientation){
            throw  IllegalArgumentException("ExpandableTextView only supports Vertical Orientation")
        }
        super.setOrientation(orientation)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (!mRelayout || visibility == View.GONE){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }
        mRelayout = false
        mButton!!.visibility = View.GONE
        mTextView!!.maxLines = Integer.MAX_VALUE
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (mTextView!!.lineCount <= mMaxCollapsedLines){
            return
        }
        mTextHeightWithMaxLines = getRealTextViewHeight(mTextView!!)
        if (mCollapsed) {
            mTextView!!.maxLines = mMaxCollapsedLines
        }
        mButton!!.visibility = View.VISIBLE
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (mCollapsed){
            mTextView!!.post { mMarginBetweenTxtAndBottom = height - mTextView!!.height }
            mCollapsedHeight = measuredHeight
        }
    }

    override fun onFinishInflate() {
        findViews()
        super.onFinishInflate()
    }

    private fun findViews(){
        mTextView = findViewById<View>(R.id.expandable_text) as TextView
        mButton = findViewById<View>(R.id.expand_collapse) as ImageView
        mTextView!!.setOnClickListener(this)
        mButton!!.setOnClickListener(this)
        mButton!!.setImageDrawable(if (mCollapsed) mExpandDrawable else mCollapseDrawable)
    }

    override fun onClick(v: View?) {
        if (mButton!!.visibility != View.VISIBLE){
            return
        }
        mCollapsed = !mCollapsed
        mButton!!.setImageDrawable(if (mCollapsed) mExpandDrawable else mCollapseDrawable)

        mAnimating = true
        val animation: Animation = if (mCollapsed) {
            ExpandCollapseAnimation(this, height, mCollapsedHeight)
        } else {
            ExpandCollapseAnimation(this, height, height + mTextHeightWithMaxLines - mTextView!!.height)
        }
        animation.fillAfter = true
        animation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation) {
                applyAlphaAnimation(mTextView, mAnimAlphaStart)
            }

            override fun onAnimationEnd(animation: Animation?) {
                clearAnimation()
                mAnimating = false
                mListener?.onExpandStateChanged(mTextView, !mCollapsed)
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        clearAnimation()
        startAnimation(animation)
    }
    internal inner class ExpandCollapseAnimation(private val mTargetView: View, private val mStartHeight: Int, private val mEndHeight: Int): Animation(){

        init {
            duration = mAnimationDuration.toLong()
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            val newHeight = ((mEndHeight - mStartHeight) * interpolatedTime + mStartHeight).toInt()
            mTextView!!.maxHeight = newHeight - mMarginBetweenTxtAndBottom
            if (java.lang.Float.compare(mAnimAlphaStart, 1.0f) != 0){
                applyAlphaAnimation(mTextView, mAnimAlphaStart + interpolatedTime * (1.0f - mAnimAlphaStart))
            }
            mTargetView.layoutParams.height = newHeight
            mTargetView.requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    interface OnExpandStateChangeListener {

        fun onExpandStateChanged(textView: TextView?, isExpanded: Boolean)
    }

    companion object {

        private val MAX_COLLAPSED_LINES = 8

        private val DEFAULT_ANIM_DURATION = 300

        private val DEFAULT_ANIM_ALPHA_START = 0.7f

        /**
         * 获取TextView真正的高度
         */
        private fun getRealTextViewHeight(textView: TextView): Int {
            val textHeight = textView.layout.getLineTop(textView.lineCount)
            val padding = textView.compoundPaddingTop + textView.compoundPaddingBottom
            return textHeight + padding
        }

        private fun getDrawable(context: Context, resId: Int): Drawable? {
            val res = context.resources
            return if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.LOLLIPOP){
                res.getDrawable(resId, context.theme)
            } else {
                ContextCompat.getDrawable(context, resId)
            }
        }
    }
    private fun applyAlphaAnimation(view: View?, alpha: Float){
        if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.KITKAT) {
            view!!.alpha = alpha
        } else {
            val alphaAnimation = AlphaAnimation(alpha, alpha)
            alphaAnimation.duration = 0
            alphaAnimation.fillAfter = true
            view!!.startAnimation(alphaAnimation)
        }
    }

}
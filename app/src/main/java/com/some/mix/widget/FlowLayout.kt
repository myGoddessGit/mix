package com.some.mix.widget

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup

/**
 * @author cyl
 * @date 2020/9/11
 */
class FlowLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs){

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        var measuredWidth = 0
        var measuredHeight = 0
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val compute = compute(widthSize - paddingRight)
        if (widthMode == MeasureSpec.EXACTLY){
            measuredWidth = widthSize
        } else if(widthMode == MeasureSpec.AT_MOST){
            measuredWidth = compute["allChildWidth"] ?: 0
        }
        if (heightMode == MeasureSpec.EXACTLY){
            measuredHeight = heightSize
        } else if (heightMode == MeasureSpec.AT_MOST){
            measuredHeight = compute["allChildHeight"] ?: 0
        }
        setMeasuredDimension(measuredWidth, measuredHeight)
    }
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0..childCount){
            val child = getChildAt(i)
            val rect = getChildAt(i).tag as Rect
            child.layout(rect.left, rect.top, rect.right, rect.bottom)
        }
    }

    private fun compute(flowWidth: Int): Map<String, Int>{
        // 是否为单行
        var aRow = true
        var marginParams : MarginLayoutParams? = null // 子元素margin
        var rowsWidth = paddingLeft // 当前行已占宽度 + paddingLeft
        var columnHeight = paddingTop // 当前行已占高度 + paddingTop
        var rowsMaxHeight = 0
        for (i in 0..childCount){
            val child = getChildAt(i)
            val measuredWidth = child.measuredWidth
            val measuredHeight = child.measuredHeight
            marginParams = child.layoutParams as MarginLayoutParams
            val childWidth = marginParams.leftMargin + marginParams.rightMargin + measuredWidth
            val childHeight = marginParams.topMargin + marginParams.bottomMargin + measuredHeight
            // 判断是否换行 该行已占大小 + 该元素的大小 > 父容器宽度 则换行
            rowsMaxHeight = Math.max(rowsMaxHeight, childHeight)
            if (rowsWidth + childWidth > flowWidth){
                // 重置行宽度
                rowsWidth = paddingLeft + paddingRight
                // 累加上该行子元素的最大高度
                columnHeight += rowsMaxHeight
                // 重置该行最大高度
                rowsMaxHeight = childHeight
                aRow = false
            }
            // 累加上该行子元素宽度
            rowsWidth += childWidth
            //判断时占的宽段时加上margin计算，设置顶点位置时不包括margin位置，不然margin会不起作用，这是给View设置tag,在onLayout给子元素设置位置再遍历取出
            child.tag = Rect(rowsWidth - childWidth + marginParams.leftMargin, columnHeight + marginParams.topMargin,rowsWidth - marginParams.rightMargin, columnHeight + childHeight - marginParams.bottomMargin)
        }
        val flowMap = HashMap<String, Int>()
        if (aRow){
            flowMap["allChildWidth"] = rowsWidth
        } else {
            flowMap["allChildWidth"] = flowWidth
        }
        flowMap["allChildHeight"] = columnHeight + rowsMaxHeight + paddingBottom
        return flowMap
    }
}
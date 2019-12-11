package com.kxl.bezierdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent

/**
 *Create by atu on 2019/12/11
 */
class MoreHeartPathView: AdvancePathView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas) {

    }

    override fun initPoint(){

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        pointStart = PointF()
        pointFirst = PointF()
        pointSecond = PointF()
        pointEnd = PointF()

        pointStart!!.x = (measuredWidth / 2 - bitmap!!.width / 2).toFloat()
        pointStart!!.y = (measuredHeight + bitmap!!.height).toFloat()

        pointEnd!!.x = random!!.nextFloat() * measuredWidth
        pointEnd!!.y = 0f

        pointFirst!!.x = random!!.nextFloat() * measuredWidth
        pointFirst!!.y = random!!.nextFloat() * measuredHeight / 2

        pointSecond!!.x = measuredWidth - pointFirst!!.x
        pointSecond!!.y = random!!.nextFloat() * measuredHeight / 2 + measuredHeight / 2
        addHeart()
        return true
    }
}
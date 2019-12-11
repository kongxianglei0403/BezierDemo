package com.kxl.bezierdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Button
import android.widget.LinearLayout

/**
 *Create by atu on 2019/12/11
 */
class BasicPathView: LinearLayout {

    private var paint: Paint? = null
    private var path: Path? = null
    private var startX = 0F
    private var startY = 0F
    private var endX = 0F
    private var endY = 0F
    private var touchX = 0F
    private var touchY = 0F
    private var isFill = false

    constructor(context: Context): super(context){
        initView()
    }

    constructor(context: Context,attributeSet: AttributeSet): super(context,attributeSet){
        initView()
    }

    constructor(context: Context,attributeSet: AttributeSet,defStyleAttr: Int):super(context,attributeSet,defStyleAttr){
        initView()
    }

    private fun initView() {
        paint = Paint()
        paint!!.isAntiAlias = true
        path = Path()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        startX = (measuredWidth / 5).toFloat()
        startY = (measuredHeight / 2).toFloat()
        endX = (measuredWidth * 4 / 5).toFloat()
        endY = (measuredHeight / 2).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint?.let {
            it.strokeWidth = 10f
            it.color = Color.RED
            it.style = Paint.Style.FILL
            canvas.drawCircle(startX,startY,10f,it)
            canvas.drawCircle(endX,endY,10f,it)
            canvas.drawCircle(touchX,touchY,10f,it)
            it.textSize = 30f
            canvas.drawText("这是一阶贝塞尔曲线",startX,startY / 4,it)
            it.color = Color.BLUE
            if (isFill){
                it.style = Paint.Style.FILL
            }
            else{
                it.style = Paint.Style.STROKE
            }
            //绘制一阶
            path?.let { inIt ->
                inIt.moveTo(startX,startY / 3)
                inIt.lineTo(endX,endY / 3)
                canvas.drawPath(inIt,paint!!)
                inIt.reset()

                inIt.moveTo(startX,startY)
                inIt.quadTo(touchX,touchY,endX,endY)
                canvas.drawPath(inIt,paint!!)
                inIt.reset()
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        touchX = event.x
        touchY = event.y
        postInvalidate()
        return true
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val button = Button(context)
        button.text = "填充"
        button.setOnClickListener {
            if (isFill){
                button.text = "填充"
            }
            else{
                button.text = "不填充"
            }
            isFill = !isFill
        }
        addView(button)
    }
}
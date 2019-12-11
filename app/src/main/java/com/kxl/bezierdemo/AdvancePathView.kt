package com.kxl.bezierdemo

import android.animation.*
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import java.util.*


/**
 *Create by atu on 2019/12/11
 */
open class AdvancePathView : RelativeLayout {

    private var paint: Paint? = null
    private var path: Path? = null
    protected var pointStart: PointF? = null
    protected var pointEnd: PointF? = null
    protected var pointFirst: PointF? = null
    protected var pointSecond: PointF? = null
    private var colors = arrayOf(Color.WHITE,Color.CYAN, Color.YELLOW, Color.BLACK, Color.LTGRAY, Color.GREEN, Color.RED)
    protected var random: Random? = null
    protected var bitmap: Bitmap? = null

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        initView()
    }

    //初始化
    private fun initView() {
        setBackgroundColor(Color.WHITE)

        paint = Paint()
        paint!!.isAntiAlias = true
        paint!!.strokeWidth = 10f

        path = Path()

        pointStart = PointF()
        pointEnd = PointF()
        pointFirst = PointF()
        pointSecond = PointF()

        random = Random()
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.heart)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        initPoint()
        paint!!.style = Paint.Style.FILL
        paint!!.color = Color.BLACK
        canvas.drawCircle(pointStart!!.x, pointStart!!.y, 10f, paint!!)
        canvas.drawCircle(pointEnd!!.x, pointEnd!!.y, 10f, paint!!)
        canvas.drawCircle(pointFirst!!.x, pointFirst!!.y, 10f, paint!!)
        canvas.drawCircle(pointSecond!!.x, pointSecond!!.y, 10f, paint!!)

        paint!!.style = Paint.Style.STROKE
        paint!!.color = Color.GRAY
        path!!.moveTo(pointStart!!.x, pointStart!!.y)
        path!!.cubicTo(pointFirst!!.x, pointFirst!!.y, pointSecond!!.x, pointSecond!!.y, pointEnd!!.x, pointEnd!!.y)
        canvas.drawPath(path!!, paint!!)
        path!!.reset()
    }

    open fun initPoint() {
        pointStart!!.x = (measuredWidth / 2).toFloat()
        pointStart!!.y = (measuredHeight - 10).toFloat()

        pointEnd!!.x = (measuredWidth / 2).toFloat()
        pointEnd!!.y = 10f

        pointFirst!!.x = 10f
        pointFirst!!.y = (measuredHeight * 3 / 4).toFloat()

        pointSecond!!.x = (measuredWidth - 10).toFloat()
        pointSecond!!.y = (measuredHeight / 4).toFloat()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val button = Button(context)
        button.text = "添加一个心"
        button.setOnClickListener {
            addHeart()
        }
        addView(button)
    }

    protected fun addHeart() {
        val imageView = ImageView(context)
        val params = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.addRule(CENTER_HORIZONTAL)
        params.addRule(ALIGN_PARENT_BOTTOM)
        imageView.setImageBitmap(drawHeart(colors[random!!.nextInt(colors.size)]))
        addView(imageView,params)
        moveHeart(imageView)
    }

    private fun moveHeart(imageView: ImageView) {
        val start = this.pointStart
        val first = this.pointFirst
        val second = this.pointSecond
        val end = this.pointEnd

        val animator = ValueAnimator.ofObject(TypeE(first!!, second!!), start, end)
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as PointF
            imageView.x = value.x
            imageView.y = value.y
        }
        animator.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                this@AdvancePathView.removeView(imageView)
            }
        })

        val objectAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f)
        objectAnimator.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                this@AdvancePathView.removeView(imageView)
            }
        })

        val set = AnimatorSet()
        set.duration = 3000
        set.play(animator).with(objectAnimator)
        set.start()
    }

    private fun drawHeart(color: Int): Bitmap {
        val newBitmap = Bitmap.createBitmap(bitmap!!.width, bitmap!!.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBitmap)
        canvas.drawBitmap(bitmap!!, 0f, 0f, paint!!)
        canvas.drawColor(color, PorterDuff.Mode.SRC_ATOP)
        canvas.setBitmap(null)
        return newBitmap
    }

    inner class TypeE(start: PointF, end: PointF) : TypeEvaluator<PointF> {
        private var start: PointF? = start
        private var end: PointF? = end
        override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
            val result = PointF()
            val left = (1 - fraction).toDouble()
            result.x = (startValue.x * Math.pow(left, 3.0) + 3 * start!!.x * Math.pow(
                left,
                2.0
            ) * fraction + 3 * end!!.x * Math.pow(
                fraction.toDouble(),
                2.0
            ) * left + endValue.x * Math.pow(fraction.toDouble(), 3.0)).toFloat()

            result.y = (startValue.y * Math.pow(left, 3.0) + 3 * start!!.y * Math.pow(
                left,
                2.0
            ) * fraction + 3 * end!!.y * Math.pow(
                fraction.toDouble(),
                2.0
            ) * left + endValue.y * Math.pow(fraction.toDouble(), 3.0)).toFloat()

            return result
        }
    }
}
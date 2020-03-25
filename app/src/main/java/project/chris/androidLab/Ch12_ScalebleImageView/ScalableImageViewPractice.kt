package project.chris.androidLab.Ch12_ScalebleImageView

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.view.GestureDetectorCompat
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import project.chris.androidLab.Utils

class ScalableImageViewPractice(context: Context?, attrs: AttributeSet?) : View(context, attrs),
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener, Runnable {

    private var imageWidth = Utils.dp2px(200F);
    private var bitmap: Bitmap
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG);
    private var imageOffsetX: Float = 0f
    private var imageOffsetY: Float = 0f

    //放大縮小倍率
    private var smallScale: Float = 0.0f
    private var bigScale: Float = 0.0f
    private var largeScale = 1.5f

    //滑動圖片
    private var imageScrollOffsetX = 0f
    private var imageScrollOffsetY = 0f

    //當前縮放模式
    private var isBig = false

    //縮放動畫過程的參數
    private var scaleFraction = 0.0f

    private var objectAnimator: ObjectAnimator? = null
    private var gestureDetector: GestureDetectorCompat
    private var overScroller: OverScroller

    init {
        bitmap = Utils.getAvatar(resources, imageWidth.toInt())
        gestureDetector = GestureDetectorCompat(context, this)
        gestureDetector.setOnDoubleTapListener(this)
        overScroller = OverScroller(context)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        imageOffsetX = (width - bitmap.width) / 2f;
        imageOffsetY = (height - bitmap.height) / 2f

        //看是比較寬的圖還是比較長的圖
        if (bitmap.width / bitmap.height > width / height) {
            smallScale = (width / bitmap.width).toFloat()
            bigScale = (height / bitmap.height).toFloat() * largeScale
        } else {
            smallScale = (height / bitmap.height).toFloat()
            bigScale = (width / bitmap.width).toFloat()
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(imageScrollOffsetX, imageScrollOffsetY)
        val scale = (smallScale + (bigScale - smallScale) * scaleFraction)
        canvas.scale(scale, scale, width / 2f, height / 2f);
        canvas.drawBitmap(bitmap, imageOffsetX, imageOffsetY, paint)
    }

    fun getScaleFraction(): Float {
        return scaleFraction
    }

    fun setScaleFraction(scaleFraction: Float) {
        this.scaleFraction = scaleFraction
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        if (isBig) {
            overScroller.fling(imageOffsetX.toInt(), imageOffsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                    (-(bitmap.width * bigScale - width) / 2).toInt(),
                    ((bitmap.width * bigScale - width) / 2).toInt(),
                    (-(bitmap.height * bigScale - height) / 2).toInt(),
                    ((bitmap.height * bigScale - height) / 2).toInt())
            postOnAnimation(this)
        }
        return false
    }

    override fun run() {
        if (overScroller.computeScrollOffset()) {
            overScroller.computeScrollOffset()
            imageScrollOffsetX = overScroller.currX.toFloat()
            imageScrollOffsetY = overScroller.currY.toFloat()
            invalidate()
            postOnAnimation(this)
        }
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        if (isBig) {
            imageScrollOffsetX -= distanceX
            //手指往右滑
            imageScrollOffsetX = Math.min(imageScrollOffsetX, (bitmap.width * bigScale - width) / 2f)
            //手指往左滑
            imageScrollOffsetX = Math.max(imageScrollOffsetX, -(bitmap.width * bigScale - width) / 2f)

            imageScrollOffsetY -= distanceY
            //往上
            imageScrollOffsetY = Math.min(imageScrollOffsetY, (bitmap.height * bigScale - height) / 2)
            //往下
            imageScrollOffsetY = Math.max(imageScrollOffsetY, -(bitmap.height * bigScale - height) / 2)
            invalidate()
        }
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    fun getObjectAnimator(): ObjectAnimator {
        if (objectAnimator == null)
            objectAnimator = ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f)
        return objectAnimator!!
    }

    override fun onDoubleTap(e: MotionEvent?): Boolean {
        isBig = !isBig
        if (isBig)
            getObjectAnimator().start()
        else
            getObjectAnimator().reverse()

        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
        return false
    }

    override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
        return false
    }

    /*
    internal var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    internal var bitmap: Bitmap
    val Image_Width = Utils.dp2px(200f)
    val OVER_SCALE_FACTOR = 1.5f//放大後還要再加大的放大係數
    private var originalOffsetX: Float = 0.toFloat()
    private var originalOffsetY: Float = 0.toFloat()
    private var offsetX: Float = 0.toFloat()
    private var offsetY: Float = 0.toFloat()
    private var smallScale: Float = 0.toFloat()
    private var bigScale: Float = 0.toFloat()
    private var isBig: Boolean = false
    private var scaleFraction: Float = 0.toFloat() //0~1f
    private var scaleAnimator: ObjectAnimator? = null

    internal var gestureDetectorCompat: GestureDetectorCompat
    internal var overScroller: OverScroller//可以在fling()最後兩個overX, overY參數設置,滾動到邊界時看起來會像iOS的滾動效果
    internal var scroller: Scroller? = null//可以把overScroller改成這個試試看, 滑起來會很慢＝＝

    init {
        bitmap = Utils.getAvatar(resources, Image_Width.toInt());
        //最正規的寫法是去繼承兩個listener
        gestureDetectorCompat = GestureDetectorCompat(context, this);
        gestureDetectorCompat.setOnDoubleTapListener(this);
        //不過也有簡便的寫法
//        gestureDetectorCompat = new GestureDetectorCompat((getContext()),
//                new GestureDetector.SimpleOnGestureListener() {
//                    //override你需要的事件...
//                    @Override
//                    public boolean onDoubleTapEvent(MotionEvent e) {
//                        return super.onDoubleTapEvent(e);
//                    }
//                });
        overScroller = OverScroller(context)

    }

    /*
    onSizeChanged方法一般是視圖大小發生變化的時候回調了，
    那麼具體看源碼是在layout的過程中出發的
    在layout方法中會調用setFrame方法，
    在setFrame方法中又調用了sizeChange，在該方法裡面回調了onSizeChanged ，
    然後才去回調onLayout過程
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = (width.toFloat() - bitmap.width) / 2
        originalOffsetY = (height.toFloat() - bitmap.height) / 2

        //比較寬的圖
        if (bitmap.width / bitmap.height > width / height) {
            smallScale = width.toFloat() / bitmap.width
            bigScale = height.toFloat() / bitmap.height * OVER_SCALE_FACTOR
        } else {
            //比較高的圖
            smallScale = height.toFloat() / bitmap.height
            bigScale = width.toFloat() / bitmap.width * OVER_SCALE_FACTOR

        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetectorCompat.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(offsetX, offsetY)//拖移

        val scale = smallScale + (bigScale - smallScale) * scaleFraction
        canvas.scale(scale, scale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }


    fun getScaleFraction(): Float {
        return scaleFraction
    }

    fun setScaleFraction(scaleFraction: Float) {
        this.scaleFraction = scaleFraction
        invalidate()
    }

    private fun getScaleAnimator(): ObjectAnimator? {
        if (scaleAnimator == null) {
            scaleAnimator = ObjectAnimator.ofFloat(this,
                    "scaleFraction", 0f, 1f)
        }
        return scaleAnimator
    }

    /*
    OnGestureListener
     */

    //必須return true, 若是false的話後續事件就不消費了
    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent) {

    }

    //return直不影響後續事件
    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }

    //distanceX:舊的Ｘ與新的Ｘ的差
    override fun onScroll(downEvent: MotionEvent, currentEvent: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        if (isBig) {
            //            if (offsetX > (bitmap.getWidth() * bigScale - getWidth()) / 2)
            //                offsetX = (bitmap.getWidth() * bigScale - getWidth()) / 2;
            //            else
            //                offsetX -= distanceX;
            //
            //也可以這樣寫比較聰明
            offsetX -= distanceX
            //往左
            offsetX = Math.min(offsetX, (bitmap.width * bigScale - width) / 2)
            //往右
            offsetX = Math.max(offsetX, -(bitmap.width * bigScale - width) / 2)

            offsetY -= distanceY
            //往上
            offsetY = Math.min(offsetY, (bitmap.height * bigScale - height) / 2)
            //往下
            offsetY = Math.max(offsetY, -(bitmap.height * bigScale - height) / 2)

            invalidate()
        }

        return false
    }

    override fun onLongPress(e: MotionEvent) {

    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        if (isBig) {
            overScroller.fling(offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                    (-(bitmap.width * bigScale - width)).toInt() / 2,
                    (bitmap.width * bigScale - width).toInt() / 2,
                    (-(bitmap.height * bigScale - height)).toInt() / 2,
                    (bitmap.height * bigScale - height).toInt() / 2,
                    100, 100)
            //這是土炮的手嚕
            //            for (int i = 10; i < 100; i += 10) {
            //                postDelayed(new Runnable() {
            //                    @Override
            //                    public void run() {
            //                        refresh();
            //                    }
            //                }, i);
            //            }
            //用這個, 刷新下一貞的畫面
            postOnAnimation(this)

        }

        return false
    }

    internal fun refresh() {
        overScroller.computeScrollOffset()
        offsetX = overScroller.currX.toFloat()
        offsetY = overScroller.currY.toFloat()
        invalidate()
    }

    /*
    OnDoubleTapListener
     */

    //此方法若與OnGestureListener的onSingleTapUp並存
    //則單擊事件邏輯需寫在這
    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        return false
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        isBig = !isBig
        if (isBig) {
            //小變大
            getScaleAnimator()!!.start()
        } else {
            //大變小
            getScaleAnimator()!!.reverse()
        }
        //        invalidate();
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        return false
    }

    override fun run() {
        if (overScroller.computeScrollOffset()) {//避免L.243 deadlock
            overScroller.computeScrollOffset()
            offsetX = overScroller.currX.toFloat()
            offsetY = overScroller.currY.toFloat()
            invalidate()
            //原本只刷新一貞是不夠的, 所以這邊再刷新
            postOnAnimation(this)
        }
    }

     */
}
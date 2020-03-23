package project.chris.androidLab.Ch12_ScalebleImageView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;
import android.widget.Scroller;

import project.chris.androidLab.Utils;

public class ScalableImageView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    public static final float Image_Width = Utils.dp2px(200);
    public static final float OVER_SCALE_FACTOR = 1.5f;//放大後還要再加大的放大係數
    private float originalOffsetX;
    private float originalOffsetY;
    private float offsetX;
    private float offsetY;
    private float smallScale;
    private float bigScale;
    private boolean isBig;
    private float currentScale; //0~1f
    private ObjectAnimator scaleAnimator;

    GestureDetectorCompat gestureDetectorCompat;
    OverScroller overScroller;//可以在fling()最後兩個overX, overY參數設置,滾動到邊界時看起來會像iOS的滾動效果
    Scroller scroller;//可以把overScroller改成這個試試看, 滑起來會很慢＝＝

    HandGestureListener handGestureListener = new HandGestureListener();
    HandFlingRunner handFlingRunner = new HandFlingRunner();
    ScaleGestureDetector scaleGestureDetector;
    HandScaleListener handScaleListener = new HandScaleListener();

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        bitmap = Utils.getAvatar(getResources(), (int) Image_Width);
        //最正規的寫法是去繼承兩個listener
        gestureDetectorCompat = new GestureDetectorCompat(getContext(), handGestureListener);
        //不過也有簡便的寫法
//        gestureDetectorCompat = new GestureDetectorCompat((getContext()),
//                new GestureDetector.SimpleOnGestureListener() {
//                    //override你需要的事件...
//                    @Override
//                    public boolean onDoubleTapEvent(MotionEvent e) {
//                        return super.onDoubleTapEvent(e);
//                    }
//                });
        overScroller = new OverScroller(getContext());
        scaleGestureDetector = new ScaleGestureDetector(getContext(), handScaleListener);

    }

    /*
    onSizeChanged方法一般是視圖大小發生變化的時候回調了，
    那麼具體看源碼是在layout的過程中出發的
    在layout方法中會調用setFrame方法，
    在setFrame方法中又調用了sizeChange，在該方法裡面回調了onSizeChanged ，
    然後才去回調onLayout過程
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        originalOffsetX = ((float) getWidth() - bitmap.getWidth()) / 2;
        originalOffsetY = ((float) getHeight() - bitmap.getHeight()) / 2;

        //比較寬的圖
        if (bitmap.getWidth() / bitmap.getHeight() > getWidth() / getHeight()) {
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FACTOR;
        } else {
            //比較高的圖
            smallScale = (float) getHeight() / bitmap.getHeight();
            bigScale = (float) getWidth() / bitmap.getWidth() * OVER_SCALE_FACTOR;

        }
        currentScale = smallScale;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = scaleGestureDetector.onTouchEvent(event);
        if (!scaleGestureDetector.isInProgress())
            result = gestureDetectorCompat.onTouchEvent(event);
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float scaleFraction = (currentScale - smallScale) / (bigScale - smallScale);
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction);//拖移

//        float scale = smallScale + (bigScale - smallScale) * scaleFraction;
        canvas.scale(currentScale, currentScale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint);
    }


    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }

    private ObjectAnimator getScaleAnimator() {
        if (scaleAnimator == null) {
            scaleAnimator = ObjectAnimator.ofFloat(this,
                    "currentScale", 0, 1);
        }
        scaleAnimator.setFloatValues(smallScale, bigScale);
        return scaleAnimator;
    }

    class HandGestureListener extends GestureDetector.SimpleOnGestureListener {
         /*
    OnGestureListener
     */

        //必須return true, 若是false的話後續事件就不消費了
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        //return直不影響後續事件
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        //distanceX:舊的Ｘ與新的Ｘ的差
        @Override
        public boolean onScroll(MotionEvent downEvent, MotionEvent currentEvent, float distanceX, float distanceY) {
            if (isBig) {
                offsetX -= distanceX;
                offsetY -= distanceY;
                fixOffsets();
                invalidate();
            }

            return false;
        }

        private void fixOffsets() {
            //往左
            offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
            //往右
            offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2);
            //往上
            offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
            //往下
            offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) / 2);
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (isBig) {
                overScroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                        (int) -(bitmap.getWidth() * bigScale - getWidth()) / 2,
                        (int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                        (int) -(bitmap.getHeight() * bigScale - getHeight()) / 2,
                        (int) (bitmap.getHeight() * bigScale - getHeight()) / 2,
                        100, 100);
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
                postOnAnimation(handFlingRunner);

            }

            return false;
        }

       /*
    OnDoubleTapListener
     */

        //此方法若與OnGestureListener的onSingleTapUp並存
        //則單擊事件邏輯需寫在這
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            isBig = !isBig;
            if (isBig) {
                offsetX = (e.getX() - getWidth() / 2f) - (e.getX() - getWidth() / 2) * bigScale / smallScale;
                offsetY = (e.getY() - getHeight() / 2f) - (e.getY() - getHeight() / 2) * bigScale / smallScale;
                fixOffsets();
                getScaleAnimator().start();
            } else {
                getScaleAnimator().reverse();
            }
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }
    }


    void refresh() {
        overScroller.computeScrollOffset();
        offsetX = overScroller.getCurrX();
        offsetY = overScroller.getCurrY();
        invalidate();
    }


    class HandFlingRunner implements Runnable {
        @Override
        public void run() {
            if (overScroller.computeScrollOffset()) {//避免L.243 deadlock
                overScroller.computeScrollOffset();
                offsetX = overScroller.getCurrX();
                offsetY = overScroller.getCurrY();
                invalidate();
                //原本只刷新一貞是不夠的, 所以這邊再刷新
                postOnAnimation(this);
            }
        }
    }

    class HandScaleListener implements ScaleGestureDetector.OnScaleGestureListener {
        float initialScale;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            currentScale = initialScale * detector.getScaleFactor();
            invalidate();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            initialScale = currentScale;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }
}

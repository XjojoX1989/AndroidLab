package project.chris.androidLab.Ch13_MultipleTouchView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import project.chris.androidLab.Utils;

public class MultiTouchView extends View {
    public static final float IMAGE_WIDTH = Utils.dp2px(200);
    Bitmap bitmap;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    float offsetX;
    float offsetY;
    float downX;
    float downY;
    float originalOffsetX;//避免下一次手指落下圖片跳動,用來記錄前一次的差
    float originalOffsetY;
    int trackingPointerId;//設置這個來追蹤目前活動的手指( 因為多了一隻手指, 所以getX(預設0)必須改成getX(特定的id)了

    public MultiTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                trackingPointerId = event.getPointerId(0);//0代表是拿唯一的那根手指
                downX = event.getX();//手指落下時的位置
                downY = event.getY();
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
            case MotionEvent.ACTION_MOVE:
                //從當前活動的手指id找對應的pointer的index->findPointerIndex
                int index = event.findPointerIndex(trackingPointerId);
                // event.getX (移動後的"位置") - downX (手指落下時的"位置") 就是這次移動的"距離"
                //再加上originalOffsetX(前一次滑動結束時的"位置")
                offsetX = event.getX(index) - downX + originalOffsetX;
                offsetY = event.getY(index) - downY + originalOffsetY;
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //因為多了一隻手指, 所以getX(預設0)必須改成getX(特定的id)了
                //getActionIndex()拿到剛才按下去手指(第二根)那個的id
                int actionIndex = event.getActionIndex();
                trackingPointerId = event.getPointerId(actionIndex);
                //初始值也需要紀錄, 沒記錄的話圖片又會跳
                downX = event.getX(actionIndex);//手指落下時的位置
                downY = event.getY(actionIndex);
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                //食指先按下, 中指在按下移動圖片是可以的, 但是此時把中止放開用食指移動會crash, 所以需要ACTION_POINTER_UP裡面去紀錄
                //1.我抬起的是不是正在做事(tracking)的手指
                actionIndex = event.getActionIndex();
                int pointerId = event.getPointerId(actionIndex);
                //2.如果是, 我把它移除找一個新的手指替代
                if (pointerId == trackingPointerId) {
                    int newIndex;//手指放開後新的接管者
                    if (actionIndex==event.getPointerCount()-1)
                        newIndex = event.getPointerCount()-2;
                    else
                        newIndex = event.getPointerCount()-1;
                    downX = event.getX(actionIndex);//手指落下時的位置
                    trackingPointerId = event.getPointerId(newIndex);
                    downY = event.getY(actionIndex);
                    originalOffsetX = offsetX;
                    originalOffsetY = offsetY;
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }
}

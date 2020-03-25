package project.chris.androidLab.Ch13_MultipleTouchView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import project.chris.androidLab.Utils;

public class MultiTouchView2 extends View {
    public static final float IMAGE_WIDTH = Utils.dp2px(200);
    Bitmap bitmap;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MultiTouchView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);
    }

    float downX, downY;
    float originalX, originalY;
    float offsetX, offsetY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float sumX = 0;
        float sumY = 0;
        for (int i = 0; i < event.getPointerCount(); i++) {
            sumX += event.getX(i);
            sumY += event.getY(i);
        }
        float centerX = sumX / event.getPointerCount();
        float centerY = sumY / event.getPointerCount();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                downX = centerX;
                downY = centerY;
                originalX = offsetX;
                originalY = offsetY;
                break;
            case MotionEvent.ACTION_MOVE:
                offsetX = event.getX() - downX + originalX;
                offsetY = event.getY() - downY + originalY;
                invalidate();
                break;
//                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }
}

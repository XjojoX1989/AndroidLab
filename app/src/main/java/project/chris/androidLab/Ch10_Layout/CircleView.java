package project.chris.androidLab.Ch10_Layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CircleView extends View {
    private static final int radius = 240;
    private static final int padding = 90;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //widthMeasureSpec:父view對子view的寬度要求，包含了Mode(ex:At_Most...) & Size(寬的具體值，也就是XML設定的)
    //heightMeasureSpec:父view對子view的高度要求，包含了Mode(ex:At_Most...) & Size(高的具體值，也就是XML設定的)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = (radius + padding) * 2;
        int height = (radius + padding) * 2;
//        對寬度做修正->其實不用手寫，系統已經提供方法resolveSize(int size, int measureSpec)
        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (specWidthMode) {
            case MeasureSpec.EXACTLY://XML直接指定size or Match_Parent
                width = specWidthSize;
                break;
            case MeasureSpec.AT_MOST:
                if (width > specWidthSize) {//Wrap_Content
                    width = specWidthMode;
                }
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            default:
        }

        int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specHeightMode) {
            case MeasureSpec.EXACTLY:
                height = specHeightSize;
                break;
            case MeasureSpec.AT_MOST:
                if (height > specHeightSize) {
                    height = specHeightSize;
                }
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            default:
                break;

        }
        width = resolveSize(width, widthMeasureSpec);
        height = resolveSize(height, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
        canvas.drawCircle(padding + radius, padding + radius, radius, paint);
    }

    //目標：方形裡面畫一個圓形
    //先畫出方形
    //圓形半徑就是方形的一半
    //圓心座標
//    private int RADIUS;
//    private int CX;
//    private int CY;
//
//    private Paint paint = new Paint();
//
//    public CircleView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width = getMeasuredWidth();
//        int height = getMeasuredHeight();
//        RADIUS = Math.min(width, height) / 2;
//        CX = width / 2;
//        CY = width / 2;
//        setMeasuredDimension(RADIUS * 2, RADIUS * 2);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        paint.setColor(Color.BLUE);
//        canvas.drawCircle(CX, CY, RADIUS, paint);
//    }
}
